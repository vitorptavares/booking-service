package com.hostfully.bookingservice.mapper;

import com.hostfully.bookingservice.dto.GuestDto;
import com.hostfully.bookingservice.dto.UpdateGuestDto;
import com.hostfully.bookingservice.entity.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class GuestMapper {

    public abstract GuestDto guestEntityToGuestDto(Guest booking);

    public Guest guestDtoToExistingGuest(Guest guest, UpdateGuestDto guestDto){
        guest.setName(guestDto.name());
        guest.setEmail(guestDto.email());
        guest.setAddress(guestDto.address());
        guest.setCity(guestDto.city());
        guest.setCountry(guestDto.country());
        guest.setState(guestDto.state());
        guest.setZipcode(guestDto.zipCode());
        guest.setPhone(guestDto.phone());
        return guest;
    }
}
