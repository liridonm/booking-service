package com.liridonmorina.bookingservice.domain.entity;

import com.liridonmorina.bookingservice.domain.enums.BookingStatus;
import com.liridonmorina.bookingservice.domain.enums.BookingType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "bookings")
public class Booking extends BaseEntity<Integer> {

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Enumerated(EnumType.STRING)
    private BookingType type;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;
}
