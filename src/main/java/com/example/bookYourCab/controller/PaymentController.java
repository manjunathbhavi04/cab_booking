package com.example.bookYourCab.controller;

import com.example.bookYourCab.dto.request.PaymentRequest;
import com.example.bookYourCab.dto.response.PaymentResponse;
import com.example.bookYourCab.service.PaymentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay/booking/{bookingid}")
    public ResponseEntity<PaymentResponse> payForTheBooking(@RequestBody PaymentRequest paymentRequest, @PathVariable("bookingid") long bookingId) {
        PaymentResponse paymentResponse = paymentService.paymentBooking(paymentRequest, bookingId);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
