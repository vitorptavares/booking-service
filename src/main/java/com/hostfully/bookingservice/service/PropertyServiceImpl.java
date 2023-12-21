package com.hostfully.bookingservice.service;

import com.hostfully.bookingservice.dto.PropertyDto;
import com.hostfully.bookingservice.mapper.PropertyMapper;
import com.hostfully.bookingservice.entity.Property;
import com.hostfully.bookingservice.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class PropertyServiceImpl implements PropertyService{

    private final PropertyRepository propertyRepository;

    private final PropertyMapper propertyMapper;

    @Override
    public PropertyDto getById(BigInteger id) {
        Property property = propertyRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "property not found"));
        log.info("property id ({}) retrieved", property.getId());
        return propertyMapper.propertyEntityToPropertyDto(property);
    }
}
