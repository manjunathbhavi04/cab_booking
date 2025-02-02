package com.example.bookYourCab.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cabId;
    private String model;

    @Column(unique = true, nullable = false)
    private long carNum;

    private double pricePerKm;

    private boolean available;

}
