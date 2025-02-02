package com.example.bookYourCab.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    private String pickUp;

    private String destination;

    private double distance;
}
