package com.hostfully.bookingservice.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hostfully.bookingservice.dto.GuestDto;
import com.hostfully.bookingservice.dto.UpdateGuestDto;
import com.hostfully.bookingservice.service.GuestService;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@ContextConfiguration(classes = {GuestController.class})
@ExtendWith(SpringExtension.class)
class GuestControllerTest {

    @Autowired
    private GuestController guestController;

    @MockBean
    private GuestService guestService;

    @Test
    void testUpdateGuest() throws Exception {

        UpdateGuestDto UpdateGuestDto =  new UpdateGuestDto(BigInteger.ONE, "name", "6625550144", "42 Main St", "Oxford", "MD", "21654", "GBO","jane.doe@example.org");

        GuestDto guestDto = new GuestDto("jane", "6625550144", "42 Main St", "Oxford", "MD", "21654", "GBO","jane.doe@example.org");

        when(guestService.updateGuest(Mockito.<UpdateGuestDto>any())).thenReturn(guestDto);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/guest").contentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        MockHttpServletRequestBuilder requestBuilder;
        requestBuilder = contentTypeResult.content(mapper.writeValueAsString(UpdateGuestDto));
        MockMvcBuilders.standaloneSetup(guestController)
                .build().perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"name\":\"jane\",\"phone\":\"6625550144\",\"address\":\"42 Main St\",\"city\":\"Oxford\",\"state\":\"MD\",\"zipcode\":\"21654\",\"country\":\"GBO\",\"email\":\"jane.doe@example.org\"}"));

    }

}

