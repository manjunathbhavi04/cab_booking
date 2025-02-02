package com.example.bookYourCab.model;

import com.example.bookYourCab.Enum.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long paymentId;

    private double amountPaid;
    private String paymentMethod;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;


}
