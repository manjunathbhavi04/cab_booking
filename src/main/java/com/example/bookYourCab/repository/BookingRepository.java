package com.example.bookYourCab.repository;

import com.example.bookYourCab.model.Booking;
import com.example.bookYourCab.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query(value = "SELECT driver_id FROM booking WHERE booking_id = :bookingId", nativeQuery = true)
    Long findDriverIdByBookingId(@Param("bookingId") Long bookingId);

}
