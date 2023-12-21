package com.hostfully.bookingservice.mapper;

import com.hostfully.bookingservice.dto.BookRequestDto;
import com.hostfully.bookingservice.dto.PropertyDto;
import com.hostfully.bookingservice.entity.Booking;
import com.hostfully.bookingservice.entity.Property;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropertyMapper {

    PropertyDto propertyEntityToPropertyDto(Property booking);

    Booking bookingRequestDtoToBookingEntity(BookRequestDto requestDto);

}
