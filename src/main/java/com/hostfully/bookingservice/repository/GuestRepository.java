package com.hostfully.bookingservice.repository;

import com.hostfully.bookingservice.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface GuestRepository extends JpaRepository<Guest, BigInteger> {
}
