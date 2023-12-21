package com.hostfully.bookingservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@AllArgsConstructor
@Jacksonized
public class Error {

    private final String message;
    private final List<String> messages;
}
