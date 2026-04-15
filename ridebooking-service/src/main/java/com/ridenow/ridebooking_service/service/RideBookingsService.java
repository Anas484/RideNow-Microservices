package com.ridenow.ridebooking_service.service;


import com.ridenow.ridebooking_service.Feign.DriverFeign;
import com.ridenow.ridebooking_service.Feign.LocationFeign;
import com.ridenow.ridebooking_service.Feign.PriceFeign;
import com.ridenow.ridebooking_service.dto.RideBookingRequestDTO;
import com.ridenow.ridebooking_service.exception.NoDriverAcceptedRequest;
import com.ridenow.ridebooking_service.exception.NoDriverFoundNearbyException;
import com.ridenow.ridebooking_service.model.RideBookingStatus;
import com.ridenow.ridebooking_service.model.RideBookings;
import com.ridenow.ridebooking_service.repository.RideBookingsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.NoSuchObjectException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RideBookingsService {

    private final RideBookingsRepo  rideBookingsRepo;
    private final DriverFeign driverFeign;
    private final PriceFeign priceFeign;
    private final LocationFeign locationFeign;
    private final StringRedisTemplate stringRedisTemplate;


    @RabbitListener(queues = "ride_booking_requests",concurrency = "5-10")
    public void createRideBooking(RideBookingRequestDTO data) {
        Map<String, Double> pickCords = locationFeign.getGeoCode(data.pickUpLocation());
        Map<String, Double> dropCords = locationFeign.getGeoCode(data.dropOffLocation());
        List<Long> driverIDs = locationFeign.getNearbyDriversIds(pickCords.get("lat"), pickCords.get("lon"));

        if (driverIDs.isEmpty()) {
            throw new NoDriverFoundNearbyException("No drivers found nearby");
        }
        Map<Long,String> driverStatusMap = driverFeign.checkAllDriverStatus(driverIDs);
        for (Long driverID : driverIDs) {
            if (!driverStatusMap.get(driverID).equals("ONLINE")) continue;

            String lockKey = "lock:driver:" + driverID;
            Boolean lockAcquired = stringRedisTemplate.opsForValue()
                    .setIfAbsent(lockKey, "locked", Duration.ofSeconds(30));

            if (lockAcquired == null || !lockAcquired) continue;

            try {
                String statusAfterLock = driverFeign.checkDriverStatus(driverID);
                if (!"ONLINE".equals(statusAfterLock)) continue;

//                Boolean accepted = notificationFeign.sendRideRequestToDriver(driverID, data);
                Boolean accepted = true;
                if (!Boolean.TRUE.equals(accepted)) continue;

                BigDecimal price = calculatePrice(pickCords, dropCords);

                RideBookings booking = RideBookings.builder()
                        .driverId(driverID)
                        .passengerId(data.passengerId())
                        .pickUpLocation(data.pickUpLocation())
                        .pickUpLocationLat(pickCords.get("lat"))
                        .pickUpLocationLon(pickCords.get("lon"))
                        .dropOffLocation(data.dropOffLocation())
                        .dropOffLocationLat(dropCords.get("lat"))
                        .dropOffLocationLon(dropCords.get("lon"))
                        .price(price)
                        .status(RideBookingStatus.IN_RIDE)
                        .build();

                rideBookingsRepo.save(booking);
                driverFeign.updateDriverStatus(driverID, "IN_RIDE");
                return;

            } catch (Exception e) {
                driverFeign.updateDriverStatus(driverID, "ONLINE");
                throw e;
            } finally {
                stringRedisTemplate.delete(lockKey);
            }
        }
        throw new NoDriverAcceptedRequest("No driver accepted the request");
    }



    public void updateRideStatusToCancelled(Long driverID, Long rideID) throws NoSuchObjectException {
        RideBookings book = rideBookingsRepo.findById(rideID).orElse(null);
        if (book == null){
            throw new NoSuchObjectException("Ride");
        }
        book.setStatus(RideBookingStatus.CANCELLED);
        driverFeign.updateDriverStatus(driverID, "AVAILABLE");
        rideBookingsRepo.save(book);
    }

    //UpdateRideStausToCompleted
    public void updateRideStatusToCompleted(Long rideId, Long driverID) throws NoSuchObjectException {
        RideBookings book = rideBookingsRepo.findById(rideId).orElse(null);
        if (book == null){
            throw new NoSuchObjectException("Ride");
        }
        book.setStatus(RideBookingStatus.COMPLETED);
        driverFeign.updateDriverStatus(driverID, "AVAILABLE");
        rideBookingsRepo.save(book);
    }


    private BigDecimal calculatePrice(Map<String, Double> pick, Map<String, Double> drop) {
        double lat1 = pick.get("lat"), lon1 = pick.get("lon");
        double lat2 = drop.get("lat"), lon2 = drop.get("lon");
        double distanceKm = haversine(lat1, lon1, lat2, lon2);
        double basefare = 2.50;
        double perKm = 1.20;
        return BigDecimal.valueOf(basefare + perKm * distanceKm).setScale(2, RoundingMode.HALF_UP);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
