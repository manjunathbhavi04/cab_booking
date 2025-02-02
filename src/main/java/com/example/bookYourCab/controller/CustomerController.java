package com.example.bookYourCab.controller;

import com.example.bookYourCab.Enum.Gender;
import com.example.bookYourCab.dto.request.CustomerRequest;
import com.example.bookYourCab.dto.response.CustomerResponse;
import com.example.bookYourCab.model.Customer;
import com.example.bookYourCab.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomer(){
        return new ResponseEntity<>(customService.getAllCustomer(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") long customerId){
        CustomerResponse customer = customService.getCustomer(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/get/gender/{gender}")
    public ResponseEntity<List<CustomerResponse>> getCustomerWithGender(@PathVariable("gender")Gender gender){
        List<CustomerResponse> customers = customService.getCustomerByGender(gender);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/get/age/gender")
    public ResponseEntity<List<CustomerResponse>> getByAgeAndGender(@RequestParam("age") int age, @RequestParam("gender") Gender gender){
        return new ResponseEntity<>(customService.getByAgeAndGender(age, gender), HttpStatus.OK);
    }

    @GetMapping("/get/ageAndGender")
    public ResponseEntity<List<CustomerResponse>> getWithAgeAndGender(@RequestParam("age") int age, @RequestParam("gender") Gender gender){
        List<CustomerResponse> customers = customService.getWithAgeAndGender(age, gender);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("get/age/count")
    public ResponseEntity<Integer> ageCount(@RequestParam("age") int age){
        return new ResponseEntity<>(customService.ageCount(age), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerRequest customerRequest){
        String message = customService.addCustomer(customerRequest);
        if(message.contains("already exists")){
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("id") Long customerId, @RequestBody CustomerRequest customerRequest){
        CustomerResponse customer = customService.updateCustomer(customerId, customerRequest);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomer(@RequestParam("id") long customerId){
        String message = customService.deleteCustomer(customerId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllCustomer(){
        return new ResponseEntity<>(customService.deleteAllCustomer(), HttpStatus.OK);
    }
}
