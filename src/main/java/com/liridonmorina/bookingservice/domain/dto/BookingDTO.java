package com.liridonmorina.bookingservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.liridonmorina.bookingservice.config.LocalDateTimeDeserializer;
import com.liridonmorina.bookingservice.domain.enums.BookingStatus;
import com.liridonmorina.bookingservice.domain.enums.BookingType;
import com.liridonmorina.bookingservice.util.AppConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {

    private Integer id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = AppConstants.DATE_FORMAT)
    private LocalDateTime startDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = AppConstants.DATE_FORMAT)
    private LocalDateTime endDate;

    private BookingStatus status;

    private BookingType type;

    private PropertyDTO property;
}
