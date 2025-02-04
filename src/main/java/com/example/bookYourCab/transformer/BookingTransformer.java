package com.example.bookYourCab.transformer;

import com.example.bookYourCab.Enum.TripStatus;
import com.example.bookYourCab.dto.request.BookingRequest;
import com.example.bookYourCab.dto.response.BookingResponse;
import com.example.bookYourCab.model.Booking;
import com.example.bookYourCab.model.Cab;
import com.example.bookYourCab.model.Customer;
import com.example.bookYourCab.model.Driver;
import lombok.Builder;

@Builder
public class BookingTransformer {
    public static Booking bookingDtoToBooking(BookingRequest bookingRequest, double ratePerKm) {
        return Booking.builder()
                .pickUp(bookingRequest.getPickUp())
                .destination(bookingRequest.getDestination())
                .distance(bookingRequest.getDistance())
                .tripStatus(TripStatus.pending)
                .billAmount(bookingRequest.getDistance() * ratePerKm)
                .build();
    }

    public static BookingResponse bookingToBookingResponse(Booking booking, Customer customer, Driver driver) {
        return BookingResponse.builder()
                .pickUp(booking.getPickUp())
                .destination(booking.getDestination())
                .bookedAt(booking.getBookedAt())
                .billAmount(booking.getBillAmount())
                .distance(booking.getDistance())
                .lastUpdateAt(booking.getLastUpdateAt())
                .tripStatus(booking.getTripStatus())
                .cab(CabTransformer.cabToCabResponse(driver.getCab(), driver))
                .customer(CustomerTransformer.CustomerToCustomerResponse(customer))
                .build();
    }
}
