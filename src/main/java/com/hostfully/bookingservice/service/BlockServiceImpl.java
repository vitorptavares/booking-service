package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.dto.*;
import com.hostfully.bookingservice.entity.Block;
import com.hostfully.bookingservice.entity.Booking;
import com.hostfully.bookingservice.entity.Property;
import com.hostfully.bookingservice.mapper.BlockMapper;
import com.hostfully.bookingservice.repository.BookingRepository;
import com.hostfully.bookingservice.repository.PropertyRepository;
import com.hostfully.bookingservice.validators.ArrivalDepartureValidator;
import com.hostfully.bookingservice.validators.NameValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class BlockServiceImpl implements BlockService {

    private final BlockMapper blockMapper;
    private final BookingRepository bookingRepository;

    private final BookingService bookingService;

    private final PropertyRepository propertyRepository;

    @Override
    @Transactional
    public BlockResponseDto blockProperty(BlockRequestDto blockRequestDto) {
        new ArrivalDepartureValidator(blockRequestDto.arrivalDate(), blockRequestDto.departureDate());
        new NameValidator(blockRequestDto.hosterName());
        bookingService.verifyOverlappedBooking(blockRequestDto.arrivalDate(), blockRequestDto.departureDate(), blockRequestDto.propertyId());
        Property property = propertyRepository.findById(blockRequestDto.propertyId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "property not found"));
        Booking booking = setBookingProperties(blockRequestDto, property);
        Booking savedBooking = bookingRepository.save(booking);
        log.info("BlockServiceImpl#blockProperty - propertyId id ({}) is blocked between ({}) and ({})", blockRequestDto.propertyId(), blockRequestDto.arrivalDate(), blockRequestDto.departureDate());
        return blockMapper.bookingEntityToBookingResponseDto(savedBooking);
    }



    @Override
    @Transactional
    public BlockResponseDto updateBlockProperty(UpdateBlockRequestDto blockRequestDTo) {
        new ArrivalDepartureValidator(blockRequestDTo.arrivalDate(), blockRequestDTo.departureDate());
        Booking booking = getBooking(blockRequestDTo.bookingId());
        bookingService.verifyOverlappedBooking(blockRequestDTo.arrivalDate(), blockRequestDTo.departureDate(), booking.getId());
        booking.setArrivalDate(blockRequestDTo.arrivalDate());
        booking.setDepartureDate(blockRequestDTo.departureDate());
        Booking savedBooking = bookingRepository.save(booking);
        log.info("BlockServiceImpl#updateBlockProperty - booking id ({}) block updated", blockRequestDTo.bookingId());
        return blockMapper.bookingEntityToBookingResponseDto(savedBooking);
    }

    @Override
    @Transactional
    public void deleteBlockProperty(BigInteger bookingId) {
        Booking booking = getBooking(bookingId);
        if(Objects.isNull(booking.getBlock())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot delete a booking");
        }
        bookingRepository.deleteById(bookingId);
        log.info("BlockServiceImpl#deleteBookProperty - booking id ({}) deleted", bookingId);
    }

    private Booking getBooking(BigInteger bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "booking_id not found"));
        log.info("BlockServiceImpl#getBooking - booking id ({}) retrieved", bookingId);
        return booking;
    }

    private  Booking setBookingProperties(BlockRequestDto blockRequestDto, Property property) {
        Booking booking = new Booking();
        booking.setProperty(property);
        Block block = new Block();
        block.setRole(blockRequestDto.blocker());
        block.setBooking(booking);
        block.setHosterName(blockRequestDto.hosterName());
        booking.setBlock(block);
        booking.setArrivalDate(blockRequestDto.arrivalDate());
        booking.setDepartureDate(blockRequestDto.departureDate());
        return booking;
    }
}
