package com.hostfully.bookingservice.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hostfully.bookingservice.dto.BookRequestDto;
import com.hostfully.bookingservice.dto.BookResponseDto;
import com.hostfully.bookingservice.dto.UpdateBookRequestDto;
import com.hostfully.bookingservice.enums.BookingStatus;
import com.hostfully.bookingservice.service.BookingService;
import java.math.BigInteger;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BookingController.class})
@ExtendWith(SpringExtension.class)
class BookingControllerTest {

    @Autowired
    private BookingController bookingController;

    @MockBean
    private BookingService bookingService;

    @Test
    void testBookProperty() throws Exception {

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setAddress("42 Main St");
        bookResponseDto.setArrivalDate(LocalDate.of(2050, 01, 01));
        bookResponseDto.setDepartureDate(LocalDate.of(2050, 01, 10));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CONFIRMED);

        BigInteger propertyId = BigInteger.valueOf(42L);
        LocalDate arrivalDate = LocalDate.of(2050, 01, 01);
        LocalDate departureDate = LocalDate.of(2050, 01, 10);

        BookRequestDto request = new BookRequestDto(propertyId, arrivalDate, departureDate, BigInteger.valueOf(42L));

        when(bookingService.bookProperty(request)).thenReturn(bookResponseDto);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/booking").contentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MockHttpServletRequestBuilder requestBuilder;
        requestBuilder = contentTypeResult.content(mapper.writeValueAsString(request));
        MockMvcBuilders.standaloneSetup(bookingController)
                .build().perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"arrival_date\":[2050,1,1],\"departure_date\":[2050,1,10],\"guest_name\":\"Guest Name\",\"address\":\"42 Main St\",\"status\":\"CONFIRMED\"}"));

    }

    @Test
    public void testUpdateBookProperty() throws Exception {
        UpdateBookRequestDto requestDto = new UpdateBookRequestDto(BigInteger.ONE, LocalDate.ofEpochDay(100000), LocalDate.ofEpochDay(100001));


        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(100000));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(100001));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CONFIRMED);
        bookResponseDto.setAddress("address test");

        when(bookingService.updateBookProperty(requestDto)).thenReturn(bookResponseDto);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/booking").contentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MockHttpServletRequestBuilder requestBuilder;
        requestBuilder = contentTypeResult.content(mapper.writeValueAsString(requestDto));
        MockMvcBuilders.standaloneSetup(bookingController)
                .build().perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"arrival_date\":[2243,10,17],\"departure_date\":[2243,10,18],\"guest_name\":\"Guest Name\",\"address\":\"address test\",\"status\":\"CONFIRMED\"}"));


    }

    @Test
    public void testCancelBookProperty() throws Exception {

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(100000));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(100001));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CANCELED);
        bookResponseDto.setAddress("address test");

        when(bookingService.cancelBookProperty(BigInteger.ONE)).thenReturn(bookResponseDto);
       MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookingController)
               .alwaysExpect(MockMvcResultMatchers.content().contentType("application/json")).build();
        mockMvc.perform(MockMvcRequestBuilders.patch("/booking/cancel/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"arrival_date\":[2243,10,17],\"departure_date\":[2243,10,18],\"guest_name\":\"Guest Name\",\"address\":\"address test\",\"status\":\"CANCELED\"}"));


    }

    @Test
    public void testDeleteBookProperty() throws Exception {

        doNothing().when(bookingService).deleteBookProperty(BigInteger.ONE);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookingController)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.delete("/booking/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetBookProperty() throws Exception {

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(100000));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(100001));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CONFIRMED);
        bookResponseDto.setAddress("address test");

        when(bookingService.getBookProperty(BigInteger.ONE)).thenReturn(bookResponseDto);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookingController)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.get("/booking/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"arrival_date\":[2243,10,17],\"departure_date\":[2243,10,18],\"guest_name\":\"Guest Name\",\"address\":\"address test\",\"status\":\"CONFIRMED\"}"));

    }

    @Test
    public void testRebookBookProperty() throws Exception {

        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setArrivalDate(LocalDate.ofEpochDay(100000));
        bookResponseDto.setDepartureDate(LocalDate.ofEpochDay(100001));
        bookResponseDto.setGuestName("Guest Name");
        bookResponseDto.setStatus(BookingStatus.CONFIRMED);
        bookResponseDto.setAddress("address test");

        when(bookingService.rebookProperty(BigInteger.ONE)).thenReturn(bookResponseDto);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookingController)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.patch("/booking/rebook/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"arrival_date\":[2243,10,17],\"departure_date\":[2243,10,18],\"guest_name\":\"Guest Name\",\"address\":\"address test\",\"status\":\"CONFIRMED\"}"));


    }


}

