package com.example.bookYourCab.controller;

import com.example.bookYourCab.dto.request.CabRequest;
import com.example.bookYourCab.dto.response.CabResponse;
import com.example.bookYourCab.dto.response.CabResponse1;
import com.example.bookYourCab.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cab")
public class CabController {

    @Autowired
    CabService cabService;

    @GetMapping
    public ResponseEntity<List<CabResponse1>> getAllCab() {
        return new ResponseEntity<>(cabService.getAllCab(), HttpStatus.OK);
    }

    @PostMapping("/register/driver/{driverid}")
    public CabResponse registerCab(@RequestBody CabRequest cab, @PathVariable("driverid") long driverId) {
        return cabService.registerCab(cab, driverId);
    }
}
