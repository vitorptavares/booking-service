package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.dto.BookRequestDto;
import com.hostfully.bookingservice.dto.BookResponseDto;
import com.hostfully.bookingservice.dto.UpdateBookRequestDto;
import com.hostfully.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    /**
     * create a new booking for a specific property, guest and arrival/departure dates
     * @param bookRequestDto
     * @return {@code BookResponseDto}
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> book(@Valid @RequestBody BookRequestDto bookRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookProperty(bookRequestDto));
    }

    /**
     * update arrival/departure dates from specific booking
     * @param bookRequestDto
     * @return {@code BookResponseDto}
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> updateBooking(@Valid @RequestBody UpdateBookRequestDto bookRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.updateBookProperty(bookRequestDto));
    }

    /**
     * cancel a booking by bookingId
     * @param bookingId
     * @return void
     */
    @PatchMapping(value = "cancel/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> cancelBooking(@Valid @NotNull @PathVariable BigInteger bookingId){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.cancelBookProperty(bookingId));
    }

    /**
     * delete booking by bookingId.
     * This operation cannot be undone
     * @param bookingId
     * @return void
     */
    @DeleteMapping(value = "/{bookingId}")
    public ResponseEntity deleteBooking(@Valid @NotNull @PathVariable BigInteger bookingId){
        bookingService.deleteBookProperty(bookingId);
        return ResponseEntity.noContent().build();
    }

    /**
     * get a booking by bookingId
     * @param bookingId
     * @return {BookRequestDto}
     */
    @GetMapping(value = "/{bookingId}")
    public ResponseEntity<BookResponseDto> getBooking(@Valid @NotNull @PathVariable BigInteger bookingId){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.getBookProperty(bookingId));
    }

    /**
     * rebook a canceled booking
     * @param bookingId
     * @return {@code BookResponseDto}
     */
    @PatchMapping(value = "rebook/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponseDto> rebook(@Valid @NotNull @PathVariable BigInteger bookingId){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.rebookProperty(bookingId));
    }
}
