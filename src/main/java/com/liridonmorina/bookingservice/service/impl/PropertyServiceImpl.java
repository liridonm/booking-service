package com.liridonmorina.bookingservice.service.impl;

import com.liridonmorina.bookingservice.domain.dto.PropertyDTO;
import com.liridonmorina.bookingservice.exceptions.NotFoundException;
import com.liridonmorina.bookingservice.mapper.PropertyMapper;
import com.liridonmorina.bookingservice.repository.PropertyRepository;
import com.liridonmorina.bookingservice.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository repository;
    private final PropertyMapper mapper;
    @Override
    public List<PropertyDTO> findAll() {
        return repository
                .findAll()
                .parallelStream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public PropertyDTO findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Property not found"));
    }
}
