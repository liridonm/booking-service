package com.liridonmorina.bookingservice.service.impl;

import com.liridonmorina.bookingservice.domain.dto.BookingDTO;
import com.liridonmorina.bookingservice.domain.dto.PropertyDTO;
import com.liridonmorina.bookingservice.domain.entity.Booking;
import com.liridonmorina.bookingservice.domain.enums.BookingStatus;
import com.liridonmorina.bookingservice.domain.enums.BookingType;
import com.liridonmorina.bookingservice.exceptions.NotFoundException;
import com.liridonmorina.bookingservice.exceptions.ValidationException;
import com.liridonmorina.bookingservice.mapper.BookingMapper;
import com.liridonmorina.bookingservice.repository.BookingRepository;
import com.liridonmorina.bookingservice.service.BookingService;
import com.liridonmorina.bookingservice.service.LockService;
import com.liridonmorina.bookingservice.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private static final String LOCK_NAME = "BOOKING_LOCK__";

    private final BookingRepository repository;
    private final PropertyService propertyService;
    private final LockService lockService;
    private final BookingMapper mapper;

    @Override
    public BookingDTO create(BookingDTO bookingDTO) {
        validate(bookingDTO);
        PropertyDTO currentProperty = propertyService.findById(bookingDTO.getProperty().getId());
        bookingDTO.setProperty(currentProperty);
        if (ObjectUtils.isEmpty(bookingDTO.getStatus())) {
            bookingDTO.setStatus(BookingStatus.OPEN);
        }
        Booking booking = mapper.toEntity(bookingDTO);
        booking = saveBookingWithLock(booking);
        return mapper.toDto(booking);
    }

    @Override
    public BookingDTO update(BookingDTO bookingDTO) {
        if (bookingDTO.getId() == null) {
            throw new ValidationException("Booking id is missing");
        }
        validate(bookingDTO);
        Booking booking = repository.findById(bookingDTO.getId()).orElseThrow(() -> new NotFoundException("Booking not found"));
        mapper.updateBooking(bookingDTO, booking);
        if (BookingType.DEFAULT.equals(bookingDTO.getType())) {
            return updateReservation(booking);
        }
        booking = repository.save(booking);
        return mapper.toDto(booking);
    }

    @Override
    public BookingDTO findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

    }

    @Override
    public List<BookingDTO> findAll() {

        return repository.findAll()
                .parallelStream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private void validate(BookingDTO bookingDto) {
        if (bookingDto.getStartDate() == null) {
            throw new ValidationException("Please provide correct value for start date");
        }

        if (bookingDto.getEndDate() == null) {
            throw new ValidationException("Please provide correct value for end date");
        }

        if (bookingDto.getStartDate().isAfter(bookingDto.getEndDate())) {
            throw new ValidationException("Start date cannot be higher than end date");
        }

        if (bookingDto.getType() == null) {
            throw new ValidationException("Please provide correct value for type");
        }
    }


    private BookingDTO updateReservation(Booking booking) {
        booking = saveBookingWithLock(booking);
        return mapper.toDto(booking);
    }

    private Booking saveBookingWithLock(Booking booking) {
        final String lockName = LOCK_NAME + booking.getProperty().getId();

        return lockService.runLocked(() -> checkOverlappingAndUpdate(booking), lockName);
    }

    private Booking checkOverlappingAndUpdate(Booking booking) {
        if (BookingType.DEFAULT.equals(booking.getType())) {
            checkForOverlappingBookings(booking);
        }
        return repository.save(booking);
    }

    private void checkForOverlappingBookings(Booking booking) {
        List<Booking> detectingOverlap = repository.findDetectingOverlap(booking.getProperty().getId(), booking.getStartDate(), booking.getEndDate(), booking.getId() != null ? booking.getId() : -1);
        if (!CollectionUtils.isEmpty(detectingOverlap)) {
            throw new ValidationException("The dates you've selected are unavailable for booking at the moment.");
        }
    }
}
