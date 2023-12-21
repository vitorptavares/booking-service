package com.hostfully.bookingservice.validators;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

class EmailStrValidatorTest {


    @Test
    void testValidEmail() {

        assertDoesNotThrow(() -> new EmailStrValidator("jane.doe@example.org"));
    }

    @Test
    void testNonValidEmail() {
        assertThrows(ResponseStatusException.class, () -> new EmailStrValidator("foo"));
    }
}

