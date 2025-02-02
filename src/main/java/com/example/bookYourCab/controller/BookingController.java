package com.example.bookYourCab.controller;

import com.example.bookYourCab.dto.request.BookingRequest;
import com.example.bookYourCab.dto.response.BookingResponse;
import com.example.bookYourCab.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/book/cab/customer/{customerId}")
    public BookingResponse cabBooking(@PathVariable("customerId") long customerId, @RequestBody BookingRequest bookingRequest) {
        return bookingService.bookCab(customerId, bookingRequest);
    }

}
