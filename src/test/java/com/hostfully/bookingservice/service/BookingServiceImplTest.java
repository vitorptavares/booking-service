package com.hostfully.bookingservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hostfully.bookingservice.dto.BookRequestDto;
import com.hostfully.bookingservice.dto.BookResponseDto;
import com.hostfully.bookingservice.dto.GuestDto;
import com.hostfully.bookingservice.dto.PropertyDto;
import com.hostfully.bookingservice.dto.UpdateBookRequestDto;
import com.hostfully.bookingservice.entity.Block;
import com.hostfully.bookingservice.entity.Booking;
import com.hostfully.bookingservice.entity.Property;
import com.hostfully.bookingservice.enums.BookingStatus;
import com.hostfully.bookingservice.mapper.BookMapper;
import com.hostfully.bookingservice.repository.BookingRepository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {
    @MockBean
    private BookMapper bookMapper;

    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingServiceImpl bookingServiceImpl;

    @MockBean
    private GuestService guestService;

    @MockBean
    private PropertyService propertyService;


    @BeforeEach
    public void setup() {
        guestService = Mockito.mock(GuestServiceImpl.class);
        bookMapper = Mockito.mock(BookMapper.class);
        propertyService = Mockito.mock(PropertyServiceImpl.class);
        bookingRepository = mock(BookingRepository.class);
        bookingServiceImpl = new BookingServiceImpl(guestService, propertyService, bookingRepository, bookMapper);
    }

    @Test
    void testBookProperty() {


        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setAddress("42 Main St");
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CONFIRMED);

        Booking booking = new Booking();
        booking.setArrivalDate(LocalDate.ofEpochDay(1L));
        booking.setDepartureDate(LocalDate.ofEpochDay(1L));
        booking.setId(BigInteger.valueOf(42L));
        booking.setStatus(BookingStatus.CONFIRMED);


        when(guestService.getById((BigInteger) any())).thenReturn(
                new GuestDto("Name", "6625550144", "42 Main St", "Oxford", "MD", "21654", "GB", "jane.doe@example.org"));
        when(propertyService.getById((BigInteger) any()))
                .thenReturn(new PropertyDto("42 Main St", "Oxford", "MD", "21654", "GB"));

        when(bookingRepository.save((Booking) any())).thenReturn(booking);
        when(bookingRepository.findAllByPropertyId((BigInteger) any())).thenReturn(new ArrayList<>());

        when(bookMapper.bookingEntityToBookingResponseDto((Booking) any())).thenReturn(bookResponseDto);
        when(bookMapper.bookingRequestDtoToBookingEntity((BookRequestDto) any())).thenReturn(booking);


        BigInteger propertyId = BigInteger.valueOf(42L);
        LocalDate arrivalDate = LocalDate.ofEpochDay(0L);
        LocalDate departureDate = LocalDate.ofEpochDay(1L);
        BookResponseDto actualBookPropertyResult = bookingServiceImpl
                .bookProperty(new BookRequestDto(propertyId, arrivalDate, departureDate, BigInteger.valueOf(42L)));
        assertSame(bookResponseDto, actualBookPropertyResult);
        assertEquals("42 Main St", actualBookPropertyResult.getAddress());
        assertEquals("Name", actualBookPropertyResult.getGuestName());
        assertEquals("CONFIRMED", actualBookPropertyResult.getStatus().name());
        assertEquals("1970-01-02", actualBookPropertyResult.getArrivalDate().toString());
        assertEquals("1970-01-02", actualBookPropertyResult.getDepartureDate().toString());

    }

    @Test
    void testUpdateBookProperty() {


        Booking booking = new Booking();
        booking.setArrivalDate(LocalDate.ofEpochDay(1L));
        booking.setDepartureDate(LocalDate.ofEpochDay(1L));
        booking.setId(BigInteger.valueOf(42L));
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setProperty(new Property());
        Optional<Booking> ofResult = Optional.of(booking);

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setAddress("42 Main St");
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CONFIRMED);

        when(bookingRepository.save((Booking) any())).thenReturn(booking);
        when(bookingRepository.findAllByPropertyId((BigInteger) any())).thenReturn(new ArrayList<>());
        when(bookingRepository.findById((BigInteger) any())).thenReturn(ofResult);
        when(bookMapper.bookingEntityToBookingResponseDto((Booking) any())).thenReturn(bookResponseDto);

        assertSame(bookResponseDto, bookingServiceImpl.updateBookProperty(
                new UpdateBookRequestDto(BigInteger.valueOf(42L), LocalDate.ofEpochDay(0L), LocalDate.ofEpochDay(1L))));

    }

    @Test
    void testCancelBookProperty() {

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setAddress("42 Main St");
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CANCELED);

        when(bookMapper.bookingEntityToBookingResponseDto((Booking) any())).thenReturn(bookResponseDto);
        when(bookingRepository.findById(BigInteger.valueOf(42L))).thenReturn(Optional.of(new Booking()));
        assertSame(bookResponseDto, bookingServiceImpl.cancelBookProperty(BigInteger.valueOf(42L)));

    }

    @Test
    void testDeleteBookProperty() {
        doNothing().when(bookingRepository).deleteById((BigInteger) any());
        when(bookingRepository.findById((BigInteger) any())).thenReturn(Optional.of(new Booking()));
        bookingServiceImpl.deleteBookProperty(BigInteger.valueOf(42L));
        verify(bookingRepository).deleteById((BigInteger) any());
    }

    @Test
    void testDeleteBookPropertyResponseStatusException() {

        Booking booking = new Booking();
        booking.setBlock(new Block());
        when(bookingRepository.findById((BigInteger) any())).thenReturn(Optional.of(booking));
        assertThrows(ResponseStatusException.class, () -> bookingServiceImpl.deleteBookProperty(BigInteger.valueOf(42L)));
    }

    @Test
    void testGetBookProperty() {

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setAddress("42 Main St");
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CONFIRMED);

        when(bookingRepository.findById((BigInteger) any())).thenReturn(Optional.of(new Booking()));
        when(bookMapper.bookingEntityToBookingResponseDto((Booking) any())).thenReturn(bookResponseDto);
        assertSame(bookResponseDto, bookingServiceImpl.getBookProperty(BigInteger.valueOf(42L)));

    }

    @Test
    void testRebookProperty() {

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setAddress("42 Main St");
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(1L));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CONFIRMED);

        Booking booking = new Booking();
        booking.setArrivalDate(LocalDate.ofEpochDay(1L));
        booking.setDepartureDate(LocalDate.ofEpochDay(1L));
        booking.setId(BigInteger.valueOf(42L));
        booking.setStatus(BookingStatus.CANCELED);
        booking.setProperty(new Property());
        Optional<Booking> ofResult = Optional.of(booking);

        when(bookingRepository.save((Booking) any())).thenReturn(booking);
        when(bookMapper.bookingEntityToBookingResponseDto((Booking) any())).thenReturn(bookResponseDto);
        when(bookingRepository.findById((BigInteger) any())).thenReturn(ofResult);

        assertEquals(bookResponseDto, bookingServiceImpl.rebookProperty(BigInteger.valueOf(42L)));
    }

    @Test
    void testRebookPropertyException() {

        Booking booking = new Booking();
        booking.setBlock(new Block());
        booking.setProperty(new Property());
        when(bookingRepository.findById((BigInteger) any())).thenReturn(Optional.of(booking));
        when(bookingRepository.findAllByPropertyId((BigInteger) any())).thenReturn(new ArrayList<>());
        assertThrows(ResponseStatusException.class, () -> bookingServiceImpl.rebookProperty(BigInteger.valueOf(42L)));
    }

    @Test
    void testVerifyOverlappedBooking() {
        when(bookingRepository.findAllByPropertyId((BigInteger) any())).thenReturn(new ArrayList<>());
        LocalDate arrivalDate = LocalDate.ofEpochDay(1L);
        LocalDate departureDate = LocalDate.ofEpochDay(1L);
        bookingServiceImpl.verifyOverlappedBooking(arrivalDate, departureDate, BigInteger.valueOf(42L));

    }
}

