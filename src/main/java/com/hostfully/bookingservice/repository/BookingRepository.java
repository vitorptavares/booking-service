package com.hostfully.bookingservice.repository;

import com.hostfully.bookingservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, BigInteger> {


    Collection<Booking> findAllByPropertyId(BigInteger propertyId);
}
