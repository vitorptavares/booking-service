package com.hostfully.bookingservice.controller;

import com.hostfully.bookingservice.dto.*;
import com.hostfully.bookingservice.service.GuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {

    private final GuestService guestService;

    /**
     * Update guest details
     * @param updateGuestDto
     * @return {@code GuestDto}
     */
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuestDto> updateGuest(@Valid @RequestBody UpdateGuestDto updateGuestDto){
        return ResponseEntity.status(HttpStatus.OK).body(guestService.updateGuest(updateGuestDto));
    }
}
