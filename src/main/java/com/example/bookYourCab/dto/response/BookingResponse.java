package com.example.bookYourCab.dto.response;

import com.example.bookYourCab.Enum.TripStatus;
import lombok.*;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {
    private String pickUp;
    private String destination;
    private double distance;
    private double billAmount;
    private TripStatus tripStatus;
    Instant bookedAt;
    Instant lastUpdateAt;
    private CustomerResponse customer;
    private CabResponse cab;
}
