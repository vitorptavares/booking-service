package com.hostfully.bookingservice.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String FIELDS = "Field '%s' %s";


    @ExceptionHandler({BindException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            BadRequestException.class})
    public ResponseEntity<Error> constraintViolationException(Exception ex) {
        List<String> messages = new ArrayList<>();
        if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) ex;
            for (ConstraintViolation<?> c : cve.getConstraintViolations()) {
                String path = c.getPropertyPath().toString();
                String field = path.lastIndexOf(".") != -1 ?
                        path.substring(path.lastIndexOf(".") + 1) : path;
                messages.add(String.format(FIELDS, field, c.getMessage()));
            }
        }
        else if (ex instanceof HttpMessageNotReadableException) {
            messages.add("Please check request valid params for this endpoint, some input is not following a valid structure.");
        }
        else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException mane = (MethodArgumentNotValidException) ex;
            for (FieldError error : mane.getBindingResult().getFieldErrors()) {
                messages.add(String.format(FIELDS, error.getField(), error.getDefaultMessage()));
            }
        } else if (ex instanceof BindException ) {
            BindException be = (BindException) ex;
            for (FieldError error : be.getBindingResult().getFieldErrors()) {
                messages.add(String.format(FIELDS, error.getField(), error.getDefaultMessage()));
            }

        } else {
            messages.add(ex.getLocalizedMessage());
        }
        return new ResponseEntity<>(new Error("Error Request", messages), HttpStatus.BAD_REQUEST);
    }
}
