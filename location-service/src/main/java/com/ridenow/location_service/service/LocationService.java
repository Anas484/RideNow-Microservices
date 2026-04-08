package com.ridenow.location_service.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final StringRedisTemplate stringRedisTemplate;
    private final String KEY = "driver:location";

    public void updateDriverLocation(Long driverId, Double latitude, Double longitude) {
        stringRedisTemplate.opsForGeo().add(
                KEY,
                new Point(longitude,latitude),
                driverId.toString()
        );
        stringRedisTemplate.opsForValue().set(
                "driver:ttl:" + driverId.toString(),
                "active",
                Duration.ofSeconds(60)
        );
        log.info("Updated location for driver {}: {}", driverId);
    }

    //GetNearbyDrivcers
    public List<Long> getNearbyDriversIds(Double latitude, Double longitude) {
        GeoResults<RedisGeoCommands.GeoLocation<String>> Ids = stringRedisTemplate.opsForGeo().radius(
                KEY,
                new Circle(new Point(longitude, latitude),
                        new Distance(5, Metrics.KILOMETERS))
        );
        if (Ids == null || Ids.getContent().isEmpty()) {
            log.info("No nearby drivers found for location ({}, {})", latitude, longitude);
            return List.of();
        }
        return Ids.getContent().stream()
                .map(result -> Long.parseLong(result.getContent().getName()))
                .filter(driverId -> stringRedisTemplate.hasKey("driver:ttl:" + driverId.toString()))
                .toList();
    }
}
