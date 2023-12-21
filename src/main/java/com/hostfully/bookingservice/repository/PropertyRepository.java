package com.hostfully.bookingservice.repository;

import com.hostfully.bookingservice.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PropertyRepository extends JpaRepository<Property, BigInteger> {
}
