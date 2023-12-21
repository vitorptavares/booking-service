package com.hostfully.bookingservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.hostfully.bookingservice.enums.BookingStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDate;


public record UpdateBookRequestDto(
        @NotNull(message = "booking_id cannot be null") @JsonProperty("booking_id") BigInteger bookingId,
        @Future(message = "cannot select current or past days as arrival_date") @NotNull(message = "arrival_date must not be null") @JsonProperty("arrival_date") LocalDate arrivalDate,
        @Future @JsonProperty("departure_date") @NotNull(message = "departure_date must not be null") LocalDate departureDate) {

}
