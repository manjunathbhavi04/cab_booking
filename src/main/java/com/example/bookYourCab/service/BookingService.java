package com.example.bookYourCab.service;

import com.example.bookYourCab.Enum.PaymentStatus;
import com.example.bookYourCab.Enum.TripStatus;
import com.example.bookYourCab.dto.request.BookingRequest;
import com.example.bookYourCab.dto.response.BookingResponse;
import com.example.bookYourCab.exception.CabNotAvailableException;
import com.example.bookYourCab.exception.CustomerNotFoundException;
import com.example.bookYourCab.exception.DriverNotFoundException;
import com.example.bookYourCab.model.*;
import com.example.bookYourCab.repository.*;
import com.example.bookYourCab.transformer.BookingTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private CabRepository cabRepo;

    @Autowired
    private DriverRepository driverRepo;

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private EmailService emailService;

    public BookingResponse bookCab(long customerId, BookingRequest bookingRequest) {
        //when booking what we need
        //want a customer to book so we have the customerId else return invalid customerId
        //request will have pickup destination and distance
        //response has bill tripStatus booked at
        //updated at
        Optional<Customer> c = customerRepo.findById(customerId);
        if(c.isEmpty()) {
            throw new CustomerNotFoundException("Invalid customerId");
        }
        Customer customer = c.get();
        Cab cab = cabRepo.getAvailableCabRandomly();
        if(cab == null) {
            throw new CabNotAvailableException("No available cabs at the moment");
        }else{
            cab.setAvailable(false);
            cabRepo.save(cab);
        }
        Booking booking = BookingTransformer.bookingDtoToBooking(bookingRequest, cab.getPricePerKm());
       //booking.setBookedAt(new Date().toInstant()); // Set current date

        Driver driver = driverRepo.findByCab(cab).orElseThrow(() -> new DriverNotFoundException("Cab is not associated with any driver"));

        // booking.setBillAmount(calculateBill(booking.getDistance(), cab.getPricePerKm())); this is calculated in the transformer
        // booking.setLastUpdateAt(new Date().toInstant());
        Booking savedBooking = bookingRepo.save(booking);

        //adding the booking assigned to the customer
        List<Booking> customerBookings = customer.getBookings();
        customerBookings.add(booking);
        customer.setBookings(customerBookings);
        Customer savedCustomer = customerRepo.save(customer);

        //add the booking to the driver assigned
        List<Booking> driverBookings = driver.getBookings();
        driverBookings.add(booking);
        driver.setBookings(driverBookings);
        Driver savedDriver = driverRepo.save(driver);

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setStatus(PaymentStatus.PENDING);
        Payment savedPayment = paymentRepo.save(payment);


        //sending mail when the booking is done
        String message = "Dear " + customer.getName() + ",\n\nYour cab has been successfully booked!\n" +
                "Pickup: " + booking.getPickUp() + "\n" +
                "Destination: " + booking.getDestination() + "\n" +
                "Amount: " + booking.getBillAmount() + "\n" +
                "Thank you for choosing BookYourCab!";
        emailService.sendEmail(customer.getEmail(), "Cab Booking Confirmation", message);

        return BookingTransformer.bookingToBookingResponse(savedBooking, savedCustomer, savedDriver);
    }

    private double calculateBill(double distance, double pricePerKm) {
        return distance*pricePerKm;
    }

    public Cab checkCab(){
        List<Cab> cabs = cabRepo.findAll();
        for(Cab cab: cabs){
            if(cab.isAvailable()){
                Optional<Driver> driverOpt = driverRepo.findByCab(cab);
                if (driverOpt.isPresent()) { // Only return cab if a driver is assigned
                    return cab;
                }
            }
        }
        return null;
    }
}
