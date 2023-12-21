package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.dto.GuestDto;
import com.hostfully.bookingservice.dto.UpdateGuestDto;

import java.math.BigInteger;

public interface GuestService {

    GuestDto getById(BigInteger id);
    GuestDto updateGuest(UpdateGuestDto guest);
}
