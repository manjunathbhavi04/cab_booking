package com.example.bookYourCab.dto.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabResponse1 {
    private double carNum;
    private String model;
    private double pricePerKm;
    private boolean available;
}
