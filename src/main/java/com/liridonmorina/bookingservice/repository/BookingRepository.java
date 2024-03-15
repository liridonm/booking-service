package com.liridonmorina.bookingservice.repository;

import com.liridonmorina.bookingservice.domain.entity.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT b FROM bookings b WHERE b.property.id = :propertyId AND b.status <> 'CANCELED' AND b.id <> :excludeId AND ((b.startDate <= :startDate AND b.endDate >= :startDate) OR (b.startDate <= :endDate AND b.endDate >= :endDate))")
    List<Booking> findDetectingOverlap(@Param("propertyId") Integer propertyId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("excludeId") Integer excludeId);

}
