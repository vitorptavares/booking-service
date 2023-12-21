package com.hostfully.bookingservice.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public record GuestDto(String name, String phone, String address, String city, String state, String zipcode,
                       String country, String email) {
}
