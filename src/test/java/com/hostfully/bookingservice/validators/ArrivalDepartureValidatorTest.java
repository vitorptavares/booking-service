package com.hostfully.bookingservice.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class ArrivalDepartureValidatorTest {

    @Test
    void testValidDepartureAndArrivalDates() {
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(0L);
        LocalDate ofEpochDayResult1 = LocalDate.ofEpochDay(1L);
        new ArrivalDepartureValidator(ofEpochDayResult, ofEpochDayResult1);

        assertEquals("1970-01-01", ofEpochDayResult.toString());
        assertEquals("1970-01-02", ofEpochDayResult1.toString());
    }

    @Test
    void testNonValidDepartureAndArrivalDates() {
        assertThrows(ResponseStatusException.class,
                () -> new ArrivalDepartureValidator(LocalDate.ofEpochDay(1L), LocalDate.ofEpochDay(1L)));
    }

}

