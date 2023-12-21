package com.hostfully.bookingservice.validators;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

public class ArrivalDepartureValidator  {

    public ArrivalDepartureValidator(LocalDate arrivalDate, LocalDate departureDate){
        validate(arrivalDate, departureDate);
    }

    private void validate(LocalDate arrivalDate, LocalDate departureDate){
        if(!departureDate.isAfter(arrivalDate)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "departure_date must be later then arrival date");
        }
    }
}
