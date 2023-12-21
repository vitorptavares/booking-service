package com.hostfully.bookingservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.time.LocalDate;


public record BookRequestDto(
        @NotNull(message = "propertyId cannot be null") @JsonProperty("property_id") BigInteger propertyId,
        @Future(message = "cannot select current or past days as arrivalDate") @JsonProperty("arrival_date") LocalDate arrivalDate,
        @Future @JsonProperty("departure_date") LocalDate departureDate,
        @NotNull(message = "guestId cannot be null") @JsonProperty("guest_id") BigInteger guestId) {

}
