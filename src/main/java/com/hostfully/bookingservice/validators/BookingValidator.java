package com.hostfully.bookingservice.validators;

import com.hostfully.bookingservice.entity.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

import static com.hostfully.bookingservice.enums.BookingStatus.CANCELED;

@Slf4j
public class BookingValidator {

    public BookingValidator(LocalDate arrivalDate, LocalDate departureDate, List<Booking> bookings){
        if(this.isOverlap(arrivalDate, departureDate, bookings)){
            log.info("BookingValidator - booking dates ({}), ({}) overlapped", arrivalDate, departureDate);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "property was booked in the same period. Please choose another timeslot.");
        }
    }

    private boolean isOverlap(LocalDate arrivalDate, LocalDate departureDate, List<Booking> bookings) {
        return bookings.stream()
                .filter(sortedBook -> !CANCELED.equals(sortedBook.getStatus()))
                .anyMatch(sortedBooking -> arrivalDate.compareTo(sortedBooking.getDepartureDate()) < 0
                        && departureDate.compareTo(sortedBooking.getArrivalDate()) > 0);

    }
}



