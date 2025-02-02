package com.example.bookYourCab.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {
    private double amountPaid;
    private String paymentMethod;

}
