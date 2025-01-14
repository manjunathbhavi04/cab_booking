package com.example.bookYourCab.model;

import com.example.bookYourCab.enums.TripStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Booking {
    @Id
    private int bookingId;
    private String pickUp;
    private String destination;
    private int distance;
    private int bill;
    private TripStatus tripStatus;
}
