package com.example.bookYourCab.controller;

import com.example.bookYourCab.dto.request.DriverRequest;
import com.example.bookYourCab.dto.response.DriverResponse;
import com.example.bookYourCab.model.Driver;
import com.example.bookYourCab.service.DriverService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
public class DriverController {
    @Autowired
    DriverService driverService;

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDriver() {
        return new ResponseEntity<>(driverService.getAllDriver(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDriver(@RequestBody DriverRequest driver) {
        String message = driverService.addDriver(driver);
        if(message.contains("already exists")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/email")
    public ResponseEntity<String> deleteDriver(@RequestParam("email") String email) {
        String message = driverService.deleteDriver(email);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
