package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.dto.*;
import com.hostfully.bookingservice.mapper.BookMapper;
import com.hostfully.bookingservice.entity.Booking;
import com.hostfully.bookingservice.repository.BookingRepository;
import com.hostfully.bookingservice.validators.ArrivalDepartureValidator;
import com.hostfully.bookingservice.validators.BookingValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

import static com.hostfully.bookingservice.enums.BookingStatus.CANCELED;
import static com.hostfully.bookingservice.enums.BookingStatus.CONFIRMED;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService{

    private final GuestService guestService;

    private final PropertyService propertyService;

    private final BookingRepository bookingRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDto bookProperty(BookRequestDto request) {
        new ArrivalDepartureValidator(request.arrivalDate(), request.departureDate());
        GuestDto guest = guestService.getById(request.guestId());
        PropertyDto property = propertyService.getById(request.propertyId());
        verifyOverlappedBooking(request.arrivalDate(), request.departureDate(), request.propertyId());
        Booking savedBooking = bookingRepository.save(bookMapper.bookingRequestDtoToBookingEntity(request));
        log.info("BlockServiceImpl#bookProperty - booking id ({}) saved", request.guestId());
        BookResponseDto bookResponseDto = bookMapper.bookingEntityToBookingResponseDto(savedBooking);
        bookResponseDto.setAddress(property.address());
        bookResponseDto.setGuestName(guest.name());
        return bookResponseDto;
    }

    @Override
    @Transactional
    public BookResponseDto updateBookProperty(UpdateBookRequestDto bookRequestDto) {
        new ArrivalDepartureValidator(bookRequestDto.arrivalDate(), bookRequestDto.departureDate());
        Booking booking = getBooking(bookRequestDto.bookingId());
        if (booking.getStatus().equals(CANCELED)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "this booking is canceled. Create a new one or rebook");
        }
        verifyOverlappedBooking(bookRequestDto.arrivalDate(), bookRequestDto.departureDate(), booking.getProperty().getId());
        booking.setArrivalDate(bookRequestDto.arrivalDate());
        booking.setDepartureDate(bookRequestDto.departureDate());
        Booking updatedBooking = bookingRepository.save(booking);
        log.info("BlockServiceImpl#updateBookProperty - booking id ({}) updated", bookRequestDto.bookingId());
        return bookMapper.bookingEntityToBookingResponseDto(updatedBooking);
    }

    @Override
    @Transactional
    public BookResponseDto cancelBookProperty(BigInteger bookingId) {
        Booking booking = getBooking(bookingId);
        booking.setStatus(CANCELED);
        Booking updatedBooking = bookingRepository.save(booking);
        log.info("BlockServiceImpl#updateBookProperty - booking id ({}) canceled", bookingId);
        return bookMapper.bookingEntityToBookingResponseDto(updatedBooking);
    }

    public Booking getBooking(BigInteger bookingId) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "booking_id not found"));
        log.info("BlockServiceImpl#getBooking - booking id ({}) retrieved", bookingId);
        return booking;
    }

    @Override
    @Transactional
    public void deleteBookProperty(BigInteger bookingId) {
        Booking booking = getBooking(bookingId);
        if(Objects.nonNull(booking.getBlock())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot delete a block");
        }
            bookingRepository.deleteById(bookingId);
            log.info("BlockServiceImpl#deleteBookProperty - booking id ({}) deleted", bookingId);
    }

    @Override
    public BookResponseDto getBookProperty(BigInteger bookingId) {
        Booking booking = getBooking(bookingId);
        log.info("BlockServiceImpl#getBookProperty - booking id ({}) retrieved", bookingId);
        return bookMapper.bookingEntityToBookingResponseDto(booking);
    }

    @Override
    @Transactional
    public BookResponseDto rebookProperty(BigInteger bookingId) {
        Booking booking = getBooking(bookingId);
        verifyOverlappedBooking(booking.getArrivalDate(), booking.getDepartureDate(), booking.getProperty().getId());
        if (!CANCELED.equals(booking.getStatus())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"booking is not canceled");
        }
        booking.setStatus(CONFIRMED);
        bookingRepository.save(booking);
        log.info("BlockServiceImpl#rebookProperty - booking id ({}) rebooked", bookingId);
        return bookMapper.bookingEntityToBookingResponseDto(booking);
    }

    public void verifyOverlappedBooking(LocalDate arrivalDate, LocalDate departureDate, BigInteger propertyId) {
        Collection<Booking> allBookings = bookingRepository.findAllByPropertyId(propertyId);
        new BookingValidator(arrivalDate, departureDate, allBookings.stream().toList());
    }
}
