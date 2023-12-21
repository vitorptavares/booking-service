package com.hostfully.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hostfully.bookingservice.enums.Role;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.time.LocalDate;

public record UpdateBlockRequestDto(@NotNull(message = "booking_id cannot be null")
                              @JsonProperty("booking_id") BigInteger bookingId,
                                    @NotNull(message = "arrival_date cannot be null") @JsonProperty("arrival_date")LocalDate arrivalDate,
                                    @NotNull(message = "departure_date cannot be null") @JsonProperty("departure_date") LocalDate departureDate) {

}


