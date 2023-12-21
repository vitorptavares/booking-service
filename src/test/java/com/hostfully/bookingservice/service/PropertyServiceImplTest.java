package com.hostfully.bookingservice.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.hostfully.bookingservice.dto.PropertyDto;
import com.hostfully.bookingservice.entity.Property;
import com.hostfully.bookingservice.mapper.PropertyMapper;
import com.hostfully.bookingservice.repository.PropertyRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class PropertyServiceImplTest {
    @MockBean
    private PropertyMapper propertyMapper;

    @MockBean
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyServiceImpl propertyServiceImpl;

    @BeforeEach
    public void setUp(){
        propertyMapper = mock(PropertyMapper.class);
        propertyRepository = mock(PropertyRepository.class);
        propertyServiceImpl = new PropertyServiceImpl(propertyRepository, propertyMapper);
    }


    @Test
    void testGetById() {
        Property property = new Property();
        property.setAddress("42 Main St");
        property.setBooking(new ArrayList<>());
        property.setCity("Oxford");
        property.setCountry("GB");
        property.setId(BigInteger.valueOf(42L));
        property.setState("MD");
        property.setZipcode("21654");
        Optional<Property> ofResult = Optional.of(property);
        when(propertyRepository.findById((BigInteger) any())).thenReturn(ofResult);
        PropertyDto propertyDto = new PropertyDto("42 Main St", "Oxford", "MD", "21654", "GB");

        when(propertyMapper.propertyEntityToPropertyDto((Property) any())).thenReturn(propertyDto);
        assertSame(propertyDto, propertyServiceImpl.getById(BigInteger.valueOf(42L)));
    }

    @Test
    void testGetByIdThrowResponseStatusException() {

        when(propertyRepository.findById((BigInteger) any())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> propertyServiceImpl.getById(BigInteger.valueOf(42L)));

    }
}

