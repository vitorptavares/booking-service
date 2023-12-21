package com.hostfully.bookingservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.hostfully.bookingservice.dto.GuestDto;
import com.hostfully.bookingservice.dto.UpdateGuestDto;
import com.hostfully.bookingservice.entity.Guest;
import com.hostfully.bookingservice.mapper.GuestMapper;
import com.hostfully.bookingservice.repository.GuestRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {
    @MockBean
    private GuestMapper guestMapper;

    @MockBean
    private GuestRepository guestRepository;

    @Autowired
    private GuestServiceImpl guestServiceImpl;

    @BeforeEach
    void setup(){
        guestMapper = mock(GuestMapper.class);
        guestRepository = mock(GuestRepository.class);
        guestServiceImpl = new GuestServiceImpl(guestRepository, guestMapper);

    }

    @Test
    void testGetById() {
        Guest guest = new Guest();
        guest.setAddress("42 Main St");
        guest.setBooking(new ArrayList<>());
        guest.setCity("Oxford");
        guest.setCountry("GB");
        guest.setEmail("jane.doe@example.org");
        guest.setId(BigInteger.valueOf(42L));
        guest.setName("Name");
        guest.setPhone("6625550144");
        guest.setState("MD");
        guest.setZipcode("21654");
        Optional<Guest> ofResult = Optional.of(guest);
        when(guestRepository.findById((BigInteger) any())).thenReturn(ofResult);
        GuestDto guestDto = new GuestDto("Name", "6625550144", "42 Main St", "Oxford", "MD", "21654", "GB",
                "jane.doe@example.org");

        when(guestMapper.guestEntityToGuestDto((Guest) any())).thenReturn(guestDto);

        assertSame(guestDto, guestServiceImpl.getById(BigInteger.valueOf(42L)));

    }

    @Test
    void testGetByIdThrowException() {
        assertThrows(ResponseStatusException.class, () -> guestServiceImpl.getById(BigInteger.valueOf(42L)));
    }

    @Test
    void testUpdateGuest() {
        Guest guest = new Guest();
        guest.setAddress("42 Main St");
        guest.setBooking(new ArrayList<>());
        guest.setCity("Oxford");
        guest.setCountry("GB");
        guest.setEmail("jane.doe@example.org");
        guest.setId(BigInteger.valueOf(42L));
        guest.setName("Name");
        guest.setPhone("6625550144");
        guest.setState("MD");
        guest.setZipcode("21654");
        Optional<Guest> ofResult = Optional.of(guest);

        GuestDto guestDto = new GuestDto("Name", "6625550144", "42 Main St", "Oxford", "MD", "21654", "GB",
                "jane.doe@example.org");

        when(guestRepository.save((Guest) any())).thenReturn(guest);
        when(guestRepository.findById((BigInteger) any())).thenReturn(ofResult);
        when(guestMapper.guestEntityToGuestDto((Guest) any())).thenReturn(guestDto);
        when(guestMapper.guestDtoToExistingGuest((Guest) any(), (UpdateGuestDto) any())).thenReturn(guest);

        assertSame(guestDto, guestServiceImpl.updateGuest(new UpdateGuestDto(BigInteger.valueOf(42L), "Name",
                "6625550144", "42 Main St", "Oxford", "MD", "21654", "GB", "jane.doe@example.org")));
    }

    @Test
    void testUpdateGuestThrowResponseStatusException() {
        Guest guest = new Guest();
        guest.setAddress("42 Main St");
        guest.setBooking(new ArrayList<>());
        guest.setCity("Oxford");
        guest.setCountry("GB");
        guest.setEmail("jane.doe@example.org");
        guest.setId(BigInteger.valueOf(42L));
        guest.setName("Name");
        guest.setPhone("6625550144");
        guest.setState("MD");
        guest.setZipcode("21654");


        when(guestRepository.findById((BigInteger) any())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class,
                () -> guestServiceImpl.updateGuest(new UpdateGuestDto(BigInteger.valueOf(42L), "Name", "6625550144",
                        "42 Main St", "Oxford", "MD", "21654", "GB", "jane.doe@example.org")));
    }
}

