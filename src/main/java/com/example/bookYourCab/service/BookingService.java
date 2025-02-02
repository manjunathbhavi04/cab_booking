package com.example.bookYourCab.service;

import com.example.bookYourCab.Enum.TripStatus;
import com.example.bookYourCab.dto.request.BookingRequest;
import com.example.bookYourCab.dto.response.BookingResponse;
import com.example.bookYourCab.exception.CustomerNotFoundException;
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
        Booking booking = BookingTransformer.bookingDtoToBooking(bookingRequest);
        booking.setBookedAt(new Date()); // Set current date
        booking.setTripStatus(TripStatus.pending); // Set initial trip status
        Cab cab = checkCab();
        if(cab == null) {
            throw new RuntimeException("No available cabs at the moment");
        }else{
            cab.setAvailable(false);
            cabRepo.save(cab);
        }
        Optional<Driver> d = driverRepo.findByCab(cab);
        Driver driver = d.get();
        booking.setBillAmount(calculateBill(booking.getDistance(), cab.getPricePerKm()));
        booking.setLastUpdateAt(new Date());
        bookingRepo.save(booking);

        //adding the booking assigned to the customer
        List<Booking> customerBookings = customer.getBookings();
        customerBookings.add(booking);
        customer.setBookings(customerBookings);
        customerRepo.save(customer);

        //add the booking to the driver assigned
        List<Booking> driverBookings = driver.getBookings();
        driverBookings.add(booking);
        driver.setBookings(driverBookings);
        driverRepo.save(driver);

        Payment payment = new Payment();
        payment.setBooking(booking);
        paymentRepo.save(payment);

        return BookingTransformer.bookingToBookingResponse(booking, customer, driver);
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
