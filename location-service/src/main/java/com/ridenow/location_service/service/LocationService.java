package com.ridenow.location_service.service;


import com.ridenow.location_service.dto.DriverLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final StringRedisTemplate stringRedisTemplate;
    private final String KEY = "driver:location";
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "driver-location-queue", concurrency = "1000-2000")
    public void updateDriverLocation(DriverLocation driverLocation) {
        String driverId = driverLocation.getDriverId().toString();
        stringRedisTemplate.opsForGeo().add(
                KEY,
                new Point((Double) driverLocation.getLon(),(Double) driverLocation.getLat()),
                driverId
        );
        stringRedisTemplate.opsForValue().set(
                "driver:ttl:" + driverId,
                "active",
                Duration.ofSeconds(60)
        );
        log.info("Driver location has been updated with " +  driverLocation.getLat() + " " + driverLocation.getLon() + " " + driverId);
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
