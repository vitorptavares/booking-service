package com.hostfully.bookingservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigInteger;

public record UpdateGuestDto(@NotNull(message = "guest_id must not be null") BigInteger id,
                             @NotEmpty(message = "guest_name must not be empty") String name,
                             @NotEmpty(message = "phone must not be empty") String phone,
                             @NotEmpty(message = "address must not be empty") String address,
                             @NotEmpty(message = "city must not be empty") String city,
                             @NotEmpty(message = "state must not be empty") String state,
                             @NotEmpty(message = "zip_code must not be empty")  @JsonProperty("zip_code") String zipCode,
                             @NotEmpty(message = "country must not be empty")  String country,
                             @NotEmpty(message = "email must not be empty")  String email) {
}
