package com.hostfully.bookingservice.validators;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class NameValidator implements StrValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z]+([\\ A-Za-z]+)*";

    public NameValidator(String name){
        this.nameValidation(name);
    }

    private void nameValidation(String name){
        if(!validate(EMAIL_REGEX).test(name)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name invalid");
        }
    }

}
