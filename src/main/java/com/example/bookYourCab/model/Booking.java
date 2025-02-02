package com.example.bookYourCab.model;

import com.example.bookYourCab.Enum.TripStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Booking {
    //customer to booking relation is one to many one customer can have multiple different bookings
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;

    private String pickUp;

    private String destination;

    private double distance;

    private double billAmount;

    @Enumerated(value = EnumType.STRING)
    private TripStatus tripStatus;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private Payment payment;

    @CreationTimestamp
    Date bookedAt;

    @UpdateTimestamp
    Date lastUpdateAt;
}
