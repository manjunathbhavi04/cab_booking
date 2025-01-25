package com.example.bookYourCab.transformer;

import com.example.bookYourCab.dto.request.CustomerRequest;
import com.example.bookYourCab.dto.response.CustomerResponse;
import com.example.bookYourCab.model.Customer;

public class CustomerTransformer {

    public static Customer CustomerReqToCustomer(CustomerRequest customerRequest){
        // Customer customer = new Customer();
        // customer.setName(customerRequest.getName());
        // customer.setAge(customerRequest.getAge());
        // customer.setEmail(customerRequest.getEmail());
        // customer.setGender(customerRequest.getGender());
        // return customer;
        // or
        // below code is same as above code
        return Customer.builder()
                .name(customerRequest.getName())
                .age(customerRequest.getAge())
                .email(customerRequest.getEmail())
                .gender(customerRequest.getGender())
                .build();
    }

    public static CustomerResponse CustomerToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .email(customer.getEmail())
                .build();
    }
}
