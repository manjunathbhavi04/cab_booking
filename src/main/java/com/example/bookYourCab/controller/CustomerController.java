package com.example.bookYourCab.controller;

import com.example.bookYourCab.model.Customer;
import com.example.bookYourCab.service.CustomerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return new ResponseEntity<>(customService.getAllCustomer(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<Optional<Customer>> getCustomer(@RequestParam long customerId){
        return new ResponseEntity<>(customService.getCustomer(customerId), HttpStatus.OK);
    }

    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer){
        String message = customService.addCustomer(customer);
        if(message.contains("already exists")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer){
        String message = customService.updateCustomer(customer);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<String> deleteCustomer(@RequestParam("id") long customerId){
        String message = customService.deleteCustomer(customerId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAllCustomer")
    public ResponseEntity<String> deleteAllCustomer(){
        return new ResponseEntity<>(customService.deleteAllCustomer(), HttpStatus.OK);
    }
}
