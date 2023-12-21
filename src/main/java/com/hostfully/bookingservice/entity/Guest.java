package com.hostfully.bookingservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "guest")
@Data
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private BigInteger id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(mappedBy = "guest")
    private List<Booking> booking;
}
