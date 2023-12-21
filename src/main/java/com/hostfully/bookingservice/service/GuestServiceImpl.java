package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.validators.EmailStrValidator;
import com.hostfully.bookingservice.validators.NameValidator;
import com.hostfully.bookingservice.dto.GuestDto;
import com.hostfully.bookingservice.dto.UpdateGuestDto;
import com.hostfully.bookingservice.mapper.GuestMapper;
import com.hostfully.bookingservice.entity.Guest;
import com.hostfully.bookingservice.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestServiceImpl implements GuestService{

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    @Override
    public GuestDto getById(BigInteger id) {
        Guest guest = getGuest(id);
        return guestMapper.guestEntityToGuestDto(guest);
    }

    private Guest getGuest(BigInteger id) {
        Guest guest = guestRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Guest Not Found"));
        return guest;
    }

    @Override
    @Transactional
    public GuestDto updateGuest(UpdateGuestDto guestDto) {
        new NameValidator(guestDto.name());
        new EmailStrValidator(guestDto.email());
        Guest guest = getGuest(guestDto.id());
        Guest updatedGuest = guestMapper.guestDtoToExistingGuest(guest, guestDto);
        Guest savedGuest = guestRepository.save(updatedGuest);
        log.info("guest id ({}) updated", guestDto.id());
        return guestMapper.guestEntityToGuestDto(savedGuest);
    }
}
