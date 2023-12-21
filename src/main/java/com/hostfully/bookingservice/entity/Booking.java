package com.hostfully.bookingservice.entity;


import com.hostfully.bookingservice.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    private BigInteger id;

    @Column(name = "arrival_date", nullable = false)
    private LocalDate arrivalDate;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    BookingStatus status = BookingStatus.CONFIRMED;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Block block;

}
