package com.hostfully.bookingservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hostfully.bookingservice.enums.BlockStatus;
import com.hostfully.bookingservice.enums.Role;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BlockResponseDto {
    @JsonProperty("arrival_date")
    private  LocalDate arrivalDate;

    @JsonProperty("departure_date")
    private  LocalDate departureDate;

    @JsonProperty("guest_name")
    private  String guestName;

    @JsonProperty("address")
    private  String address;

    @JsonProperty("status")
    private BlockStatus status;

    @JsonProperty("role_block")
    private Role role;

}
