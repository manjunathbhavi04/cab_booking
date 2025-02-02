package com.example.bookYourCab.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabResponse {
    private long carNum;
    private String model;
    private double pricePerKm;
    private boolean available;

    // the cab's owner is the driver
    private DriverResponse driver;
}
