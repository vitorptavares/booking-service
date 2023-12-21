package com.hostfully.bookingservice.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class NameValidatorTest {

    @Test
    void testValidName() {

        assertDoesNotThrow(() -> new NameValidator("Jhon Doe"));
    }


    @Test
    void testNonValidName() {
        assertThrows(ResponseStatusException.class, () -> new NameValidator("933oo"));
    }
}

