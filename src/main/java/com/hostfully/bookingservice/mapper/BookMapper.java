package com.hostfully.bookingservice.mapper;

import com.hostfully.bookingservice.dto.BookResponseDto;
import com.hostfully.bookingservice.entity.Booking;
import com.hostfully.bookingservice.dto.BookRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {


    @Mapping(target = "address", source = "property.address")
    @Mapping(target = "guestName", source = "guest.name")
    @Mapping(target = "status")
    BookResponseDto bookingEntityToBookingResponseDto(Booking booking);

    @Mapping(target = "property.id", source = "propertyId")
    @Mapping(target = "guest.id", source = "guestId")
    Booking bookingRequestDtoToBookingEntity(BookRequestDto requestDto);
}
