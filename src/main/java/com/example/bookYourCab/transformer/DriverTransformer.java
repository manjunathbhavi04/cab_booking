package com.example.bookYourCab.transformer;

import com.example.bookYourCab.dto.request.CustomerRequest;
import com.example.bookYourCab.dto.request.DriverRequest;
import com.example.bookYourCab.dto.response.CustomerResponse;
import com.example.bookYourCab.dto.response.DriverResponse;
import com.example.bookYourCab.model.Customer;
import com.example.bookYourCab.model.Driver;

public class DriverTransformer {
    public static Driver DriverRequestToDriver(DriverRequest driverRequest){
        // Driver driver = new Customer();
        // driver.setName(customerRequest.getName());
        // driver.setAge(customerRequest.getAge());
        // driver.setEmail(customerRequest.getEmail());
        // return driver;
        // or
        // below code is same as above code
        return Driver.builder()
                .name(driverRequest.getName())
                .age(driverRequest.getAge())
                .email(driverRequest.getEmail())
                .build();
    }

    public static DriverResponse DriverToDriverResponse(Driver driver){
        return DriverResponse.builder()
                .name(driver.getName())
                .age(driver.getAge())
                .email(driver.getEmail())
                .build();
    }
}
