package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.dto.BookResponseDto;
import com.hostfully.bookingservice.dto.BookRequestDto;
import com.hostfully.bookingservice.dto.UpdateBookRequestDto;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;

@Service
public interface BookingService {

    /**
     * create a new booking for a specific property, guest and arrival/departure dates
     * @param request
     * @return {@code BookResponseDto}
     */
    BookResponseDto bookProperty(BookRequestDto request);

    /**
     * update arrival/departure dates from specific booking
     * @param bookRequestDto
     * @return {@code BookResponseDto}
     */
    BookResponseDto updateBookProperty(UpdateBookRequestDto bookRequestDto);

    /**
     * cancel a booking by bookingId
     * @param bookingId
     * @return void
     */
    BookResponseDto cancelBookProperty(BigInteger bookingId);

    /**
     * delete booking by bookingId.
     * This operation cannot be undone
     * @param bookingId
     * @return void
     */
    void deleteBookProperty(BigInteger bookingId);

    /**
     * get a booking by bookingId
     * @param bookingId
     * @return {BookRequestDto}
     */
    BookResponseDto getBookProperty(BigInteger bookingId);

    /**
     * rebook a canceled booking
     * @param bookingId
     * @return {@code BookResponseDto}
     */
    BookResponseDto rebookProperty(BigInteger bookingId);

    /**
     * Verify if booking period overlaps any other property booking
     * @param arrivalDate
     * @param departureDate
     * @param propertyId
     */
    void verifyOverlappedBooking(LocalDate arrivalDate, LocalDate departureDate, BigInteger propertyId);
}
