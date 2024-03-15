package com.liridonmorina.bookingservice.mapper;

import com.liridonmorina.bookingservice.domain.entity.Property;
import com.liridonmorina.bookingservice.domain.dto.PropertyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper {

    PropertyDTO toDto(Property property);
    Property toEntity(PropertyDTO propertyDTO);

}
