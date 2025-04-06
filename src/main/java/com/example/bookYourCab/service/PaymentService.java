package com.example.bookYourCab.service;

import com.example.bookYourCab.Enum.PaymentStatus;
import com.example.bookYourCab.Enum.TripStatus;
import com.example.bookYourCab.dto.request.PaymentRequest;
import com.example.bookYourCab.dto.response.PaymentResponse;
import com.example.bookYourCab.exception.CustomerNotFoundException;
import com.example.bookYourCab.exception.ResourceNotFoundException;
import com.example.bookYourCab.model.*;
import com.example.bookYourCab.repository.*;
import com.example.bookYourCab.transformer.PaymentTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private DriverRepository driverRepo;

    @Autowired
    private CabRepository cabRepo;

    @Autowired
    private EmailService emailService;

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

            // set the cab to available because trip is completed
            updateCabAvailability(bookingId);

            bookingRepository.save(booking);

            long customerId = bookingRepository.findCustomerIdByBookingId(bookingId);
            Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Invalid customer ID"));

            //sending mail when the booking is done
            String message = "Dear " + customer.getName() + ",\n\nYour payment of Rs. " + payment.getAmountPaid() +
                    " has been successfully processed for Booking ID: " + bookingId + ".\n" +
                    "Payment Date: " + payment.getPaymentDate() + "\n" +
                    "Thank you for choosing BookYourCab!";
            emailService.sendEmail(customer.getEmail(), "Payment Confirmation", message);
            System.out.println("paid");
        }else{
            payment.setStatus(PaymentStatus.PENDING);
        }
        payment.setPaymentDate(new Date());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());

        paymentRepository.save(payment);


        return PaymentTransformer.paymentToPaymentResponse(payment);
    }

    private void updateCabAvailability(long bookingId) {
        Long driverId = bookingRepository.findDriverIdByBookingId(bookingId);
        if(driverId == null) {
            log.warn("No driver found for Booking Id: {}", bookingId);
            return;
        }
        Driver driver = driverRepo.findById(driverId).orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if(driver != null) {
            Cab cab = driver.getCab();
            cab.setAvailable(true);
            cabRepo.save(cab);
            log.info("Cab Id: {} is now available", cab.getCabId());
        }
    }

    private double remainingAmount(PaymentRequest paymentRequest, long bookingId) {
        Optional<Booking> b = bookingRepository.findById(bookingId);
        Booking booking = b.get();
        return booking.getBillAmount() - paymentRequest.getAmountPaid();
    }

}
