package com.liridonmorina.bookingservice.controller;

import com.liridonmorina.bookingservice.domain.dto.BookingDTO;
import com.liridonmorina.bookingservice.domain.dto.ResponseWrapper;
import com.liridonmorina.bookingservice.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/booking")
@Tag(name = "Booking Controller", description = "Booking API")
public class BookingController {
    private final BookingService service;

    @GetMapping
    @Operation(summary = "Read all bookings")
    public ResponseEntity<ResponseWrapper> getAll() {
        List<BookingDTO> bookings = service.findAll();
        return ResponseEntity.ok(new ResponseWrapper("Bookings have been retrieved!", bookings));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Read booking by id")
    public ResponseEntity<ResponseWrapper> getById(@PathVariable Integer id) {
        BookingDTO booking = service.findById(id);
        return ResponseEntity.ok(new ResponseWrapper("Booking has been retrieved!", booking));
    }

    @PostMapping
    @Operation(summary = "Create a book", description = "FormatDate:`yyyy-MM-dd HH:mm`," +
            " Example:" +
            "`{\n" +
            "    \"startDate\": \"2024-03-15 14:00\",\n" +
            "    \"endDate\": \"2024-03-16 10:00\",\n" +
            "    \"status\": \"OPEN\",\n" +
            "    \"type\": \"DEFAULT\",\n" +
            "    \"property\": {\n" +
            "      \"id\": 1,\n" +
            "    }\n" +
            "  }`," )
    public ResponseEntity<ResponseWrapper> create(@RequestBody BookingDTO bookingDto) {
        BookingDTO bookingDTO = service.create(bookingDto);
        return ResponseEntity.ok(new ResponseWrapper("Booking has been created!", bookingDTO));

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book", description = "FormatDate:`yyyy-MM-dd HH:mm`," +
            " Example:" +
            "`{\n" +
            "    \"id\": 1,\n" +
            "    \"startDate\": \"2024-03-15 14:00\",\n" +
            "    \"endDate\": \"2024-03-16 10:00\",\n" +
            "    \"status\": \"OPEN\",\n" +
            "    \"type\": \"DEFAULT\",\n" +
            "    \"property\": {\n" +
            "      \"id\": 1,\n" +
            "    }\n" +
            "  }`," )
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer id, @RequestBody BookingDTO bookingDto) {
        bookingDto.setId(id);
        BookingDTO bookingDTO = service.update(bookingDto);
        return ResponseEntity.ok(new ResponseWrapper("Booking has been updated!", bookingDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.ok(new ResponseWrapper("Booking has been deleted!"));
    }
}
