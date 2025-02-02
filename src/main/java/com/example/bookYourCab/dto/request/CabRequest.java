package com.example.bookYourCab.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabRequest {
    private String model;
    private long carNum;
    private double pricePerKm;
}
