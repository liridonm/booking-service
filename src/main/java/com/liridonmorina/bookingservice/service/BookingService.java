package com.liridonmorina.bookingservice.service;

import com.liridonmorina.bookingservice.domain.dto.BookingDTO;

import java.util.List;

public interface BookingService {

    BookingDTO create(BookingDTO bookingDTO);
    BookingDTO update(BookingDTO bookingDTO);

    BookingDTO findById(Integer id);

    List<BookingDTO> findAll();

    void deleteById(Integer id);

}
