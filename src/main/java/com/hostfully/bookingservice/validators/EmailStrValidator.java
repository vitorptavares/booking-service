package com.hostfully.bookingservice.validators;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailStrValidator implements StrValidator {

    private static final String EMAIL_REGEX = "^(.+)@(\\S+)$";

    public EmailStrValidator(String email){
        this.emailValidation(email);
    }

    private void emailValidation(String email){
        if(!validate(EMAIL_REGEX).test(email)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email invalid");
        }
    }
}
