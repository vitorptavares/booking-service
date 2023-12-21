package com.hostfully.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hostfully.bookingservice.enums.Role;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;
import java.time.LocalDate;

public record BlockRequestDto(@NotNull(message = "property_id cannot be null")
                              @JsonProperty("property_id") BigInteger propertyId, 
                              @NotNull(message = "role_block cannot be null") @JsonProperty("role_block") Role blocker,
                              @NotNull(message = "hoster_name cannot be null") @JsonProperty("hoster_name") String hosterName,
                              @NotNull(message = "arrival_date cannot be null") @JsonProperty("arrival_date")LocalDate arrivalDate,
                              @NotNull(message = "departure_date cannot be null") @JsonProperty("departure_date") LocalDate departureDate) {

}


