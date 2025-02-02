package com.example.bookYourCab.dto.response;

import com.example.bookYourCab.Enum.TripStatus;
import lombok.*;
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
    Date bookedAt;
    Date lastUpdateAt;
    private CustomerResponse customer;
    private CabResponse cab;
}
