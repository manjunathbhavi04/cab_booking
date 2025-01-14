package com.example.bookYourCab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Driver {
    @Id
    private int driverId;
    private String name;
    private int age;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cab_id")
    private Cab cab;

    @OneToMany
    @JoinColumn(name = "driver_id")
    private List<Booking> bookings = new ArrayList<>();
}
