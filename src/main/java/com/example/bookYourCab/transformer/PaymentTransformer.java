package com.example.bookYourCab.transformer;

import com.example.bookYourCab.dto.request.PaymentRequest;
import com.example.bookYourCab.dto.response.BookingResponse;
import com.example.bookYourCab.dto.response.PaymentResponse;
import com.example.bookYourCab.model.Payment;

public class PaymentTransformer {
    public static Payment paymentDtoToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .amountPaid(paymentRequest.getAmountPaid())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .build();
    }

    public static PaymentResponse paymentToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .amountPaid(payment.getAmountPaid())
                .paymentMethod(payment.getPaymentMethod())
                .paymentDate(payment.getPaymentDate())
                .status(payment.getStatus())
                .bookingId(payment.getPaymentId())
                .build();
    }
}
