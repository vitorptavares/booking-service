package com.hostfully.bookingservice.entity;

import com.hostfully.bookingservice.enums.BlockStatus;
import com.hostfully.bookingservice.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;

@Entity
@Table(name = "block")
@Data
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private BigInteger id;


    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    BlockStatus status = BlockStatus.BLOCKED;

    @Column(name = "role_block")
    @Enumerated(value = EnumType.STRING)
    private Role role = null;

    @Column(name = "hoster_name")
    private String hosterName;

    @OneToOne
    @JoinColumn(name = "booking_id", referencedColumnName = "id")
    private Booking booking;




}
