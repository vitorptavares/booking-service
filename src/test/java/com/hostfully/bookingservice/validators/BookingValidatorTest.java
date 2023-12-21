package com.hostfully.bookingservice.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hostfully.bookingservice.entity.Booking;
import com.hostfully.bookingservice.enums.BookingStatus;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class BookingValidatorTest {


    @Test
    void testNotOverlappedBooking() {
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);
        ArrayList<Booking> bookingList = new ArrayList<>();
        new BookingValidator(ofEpochDayResult, ofEpochDayResult1, bookingList);

        assertEquals("1970-01-02", ofEpochDayResult.toString());
        assertEquals("1970-01-02", ofEpochDayResult1.toString());
        assertTrue(bookingList.isEmpty());
    }

    @Test
    void testOverlappedBooking() {
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);



        Booking booking = new Booking();
        booking.setArrivalDate(LocalDate.ofEpochDay(9L));
        booking.setDepartureDate(LocalDate.ofEpochDay(9L));
        booking.setId(BigInteger.valueOf(42L));
        booking.setStatus(BookingStatus.CONFIRMED);


        Booking booking1 = new Booking();
        booking1.setArrivalDate(LocalDate.ofEpochDay(9L));
        booking1.setDepartureDate(LocalDate.ofEpochDay(9L));
        booking1.setId(BigInteger.valueOf(42L));
        booking1.setStatus(BookingStatus.CONFIRMED);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking1);
        new BookingValidator(ofEpochDayResult, ofEpochDayResult1, bookingList);

        assertEquals("1970-01-02", ofEpochDayResult.toString());
        assertEquals("1970-01-02", ofEpochDayResult1.toString());
        assertEquals(1, bookingList.size());
    }

    @Test
    void testBookingOverlapped() {
        LocalDate arrivalDate = LocalDate.ofEpochDay(1L);
        LocalDate departureDate = LocalDate.ofEpochDay(5L);


        Booking booking = new Booking();
        booking.setArrivalDate(LocalDate.ofEpochDay(4L));
        booking.setDepartureDate(LocalDate.ofEpochDay(6L));
        booking.setId(BigInteger.valueOf(42L));
        booking.setStatus(BookingStatus.CONFIRMED);

        ArrayList<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        assertThrows(ResponseStatusException.class, () -> new BookingValidator(arrivalDate, departureDate, bookingList));
    }
}

