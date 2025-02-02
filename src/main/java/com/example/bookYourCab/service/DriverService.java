package com.example.bookYourCab.service;

import com.example.bookYourCab.dto.request.DriverRequest;
import com.example.bookYourCab.dto.response.DriverResponse;
import com.example.bookYourCab.model.Driver;
import com.example.bookYourCab.repository.DriverRepository;
import com.example.bookYourCab.transformer.DriverTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    @Autowired
    DriverRepository driverRepo;

    public List<DriverResponse> getAllDriver() {
        List<Driver> drivers = driverRepo.findAll();
        return drivers.stream().map(DriverTransformer::DriverToDriverResponse).toList();
    }

    public String addDriver(DriverRequest driverRequest) {
        List<Driver> driverByEmail = driverRepo.findByEmail(driverRequest.getEmail());
        if(!driverByEmail.isEmpty()){
            return "Driver already exists";
        }
        try{
            driverRepo.save(DriverTransformer.DriverRequestToDriver(driverRequest));
        }catch (Exception e){
            return "Error: "+ e;
        }
        return "Added Successfully";

    }

    public String deleteDriver(String email) {
        //check if the driver exists
        List<Driver> driver = driverRepo.findByEmail(email);
        if(driver.isEmpty()){
            return "driver do not exist";
        }
        try{
            driverRepo.deleteByEmail(email);
        }catch (Exception e) {
            return "Error: "+ e;
        }
        return "Driver removed from the database";
    }
}
