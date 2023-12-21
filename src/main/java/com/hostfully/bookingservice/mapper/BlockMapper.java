package com.hostfully.bookingservice.mapper;

import com.hostfully.bookingservice.dto.BlockRequestDto;
import com.hostfully.bookingservice.dto.BlockResponseDto;
import com.hostfully.bookingservice.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlockMapper {

    Booking bookingRequestDtoToBookingEntity(BlockRequestDto requestDto);

    @Mapping(target = "address", source = "property.address")
    @Mapping(target = "guestName", source = "guest.name")
    @Mapping(target = "status", source = "block.status")
    BlockResponseDto bookingEntityToBookingResponseDto(Booking booking);
}
