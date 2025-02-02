package com.example.bookYourCab.service;

import com.example.bookYourCab.Enum.PaymentStatus;
import com.example.bookYourCab.Enum.TripStatus;
import com.example.bookYourCab.dto.request.PaymentRequest;
import com.example.bookYourCab.dto.response.PaymentResponse;
import com.example.bookYourCab.model.Booking;
import com.example.bookYourCab.model.Payment;
import com.example.bookYourCab.repository.BookingRepository;
import com.example.bookYourCab.repository.PaymentRepository;
import com.example.bookYourCab.transformer.PaymentTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public PaymentResponse paymentBooking(PaymentRequest paymentRequest, long bookingId) {
        Optional<Booking> b = bookingRepository.findById(bookingId);
        if(b.isEmpty()) {
            throw new RuntimeException("Invalid booking ID");
        }
        Booking booking = b.get();
        Optional<Payment> p = paymentRepository.findByBooking(booking);
        if(p.isEmpty()){
            throw new RuntimeException("Invalid Booking ID");
        }
        Payment payment = p.get();
        double amount = paymentRequest.getAmountPaid();
        payment.setAmountPaid(amount);
        double amountPending = remainingAmount(paymentRequest, bookingId);
        if(amountPending == 0){
            payment.setStatus(PaymentStatus.DONE);
            booking.setTripStatus(TripStatus.Completed);
        }else{
            payment.setStatus(PaymentStatus.PENDING);
        }
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        bookingRepository.save(booking);
        paymentRepository.save(payment);

        return PaymentTransformer.paymentToPaymentResponse(payment);
    }

    private double remainingAmount(PaymentRequest paymentRequest, long bookingId) {
        Optional<Booking> b = bookingRepository.findById(bookingId);
        Booking booking = b.get();
        return booking.getBillAmount() - paymentRequest.getAmountPaid();
    }
}
