package com.hostfully.bookingservice.validators;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public interface StrValidator {

    default Predicate<String> validate(String regex) {
        return o -> Pattern.compile(regex).matcher(o).matches();
    }

}
