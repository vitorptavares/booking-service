package com.hostfully.bookingservice.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hostfully.bookingservice.dto.BlockRequestDto;
import com.hostfully.bookingservice.dto.BlockResponseDto;
import com.hostfully.bookingservice.dto.UpdateBlockRequestDto;
import com.hostfully.bookingservice.enums.BlockStatus;
import com.hostfully.bookingservice.enums.Role;
import com.hostfully.bookingservice.repository.PropertyRepository;
import com.hostfully.bookingservice.service.*;

import java.math.BigInteger;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
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

@ContextConfiguration(classes = {BlockController.class})
@ExtendWith(SpringExtension.class)
class BlockControllerTest {

    @Autowired
    private BlockController blockController;

    @MockBean
    private BlockService blockService;

    @MockBean
    private PropertyRepository propertyRepository;

    @Test
    void testCreateBlock() throws Exception {


        BlockResponseDto blockResponseDto = new BlockResponseDto();
        blockResponseDto.setGuestName("Guest name");
        blockResponseDto.setRole(Role.OWNER);
        blockResponseDto.setStatus(BlockStatus.BLOCKED);
        blockResponseDto.setAddress("address name");


        BlockRequestDto request = new BlockRequestDto(BigInteger.ONE, Role.OWNER, "", LocalDate.ofEpochDay(1), LocalDate.ofEpochDay(2));

        when(blockService.blockProperty(Mockito.<BlockRequestDto>any())).thenReturn(blockResponseDto);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/block").contentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MockHttpServletRequestBuilder requestBuilder;
        requestBuilder = contentTypeResult.content(mapper.writeValueAsString(request));
        MockMvcBuilders.standaloneSetup(blockController)
                .build().perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"guest_name\":\"Guest name\",\"address\":\"address name\",\"status\":\"BLOCKED\",\"role_block\":\"OWNER\"}"));

    }

    @Test
    void testUpdateBlock() throws Exception {

        BlockResponseDto blockResponseDto = new BlockResponseDto();
        blockResponseDto.setGuestName("Guest name");
        blockResponseDto.setRole(Role.OWNER);
        blockResponseDto.setStatus(BlockStatus.BLOCKED);
        blockResponseDto.setAddress("address name");
        blockResponseDto.setArrivalDate(LocalDate.ofEpochDay(3));
        blockResponseDto.setDepartureDate(LocalDate.ofEpochDay(4));

        UpdateBlockRequestDto request = new UpdateBlockRequestDto(BigInteger.ONE, LocalDate.ofEpochDay(3), LocalDate.ofEpochDay(4));

        when(blockService.updateBlockProperty(Mockito.<UpdateBlockRequestDto>any())).thenReturn(blockResponseDto);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/block").contentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MockHttpServletRequestBuilder requestBuilder;
        requestBuilder = contentTypeResult.content(mapper.writeValueAsString(request));
        MockMvcBuilders.standaloneSetup(blockController)
                .build().perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"arrival_date\":[1970,1,4],\"departure_date\":[1970,1,5],\"guest_name\":\"Guest name\",\"address\":\"address name\",\"status\":\"BLOCKED\",\"role_block\":\"OWNER\"}"));
    }

    @Test
    void testDeleteBlock() throws Exception {
        doNothing().when(blockService).deleteBlockProperty(BigInteger.ONE);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(blockController)
                .build();
        mockMvc.perform(MockMvcRequestBuilders.delete("/block/1"))
                .andExpect(status().isNoContent());
    }

}

