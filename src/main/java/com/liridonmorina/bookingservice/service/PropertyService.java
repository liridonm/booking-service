package com.liridonmorina.bookingservice.service;

import com.liridonmorina.bookingservice.domain.dto.PropertyDTO;

import java.util.List;

public interface PropertyService {

    List<PropertyDTO> findAll();
    PropertyDTO findById(Integer id);
}
