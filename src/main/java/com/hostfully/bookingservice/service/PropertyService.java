package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.dto.PropertyDto;

import java.math.BigInteger;

public interface PropertyService {

    PropertyDto getById(BigInteger id);

}
