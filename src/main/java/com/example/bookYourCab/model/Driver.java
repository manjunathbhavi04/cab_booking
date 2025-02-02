package com.example.bookYourCab.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long driverId;
    private String name;
    private int age;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cab_id")
    private Cab cab;

    @OneToMany
    @JoinColumn(name = "driver_id")
    private List<Booking> bookings = new ArrayList<>();
}
