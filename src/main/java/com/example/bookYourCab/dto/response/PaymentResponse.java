package com.example.bookYourCab.dto.response;

import com.example.bookYourCab.Enum.PaymentStatus;
import com.example.bookYourCab.model.Booking;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private double amountPaid;
    private String paymentMethod;
    private PaymentStatus status;

    private Date paymentDate;

    private long  bookingId;
}
