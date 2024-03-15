package com.liridonmorina.bookingservice.mapper;

import com.liridonmorina.bookingservice.domain.entity.Booking;
import com.liridonmorina.bookingservice.domain.dto.BookingDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    BookingDTO toDto(Booking booking);
    Booking toEntity(BookingDTO bookingDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBooking(BookingDTO bookingDTO, @MappingTarget Booking booking);


}
