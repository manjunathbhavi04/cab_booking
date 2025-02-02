package com.example.bookYourCab.service;

import com.example.bookYourCab.dto.request.CabRequest;
import com.example.bookYourCab.dto.response.CabResponse;
import com.example.bookYourCab.dto.response.CabResponse1;
import com.example.bookYourCab.dto.response.DriverResponse;
import com.example.bookYourCab.exception.DriverNotFoundException;
import com.example.bookYourCab.model.Cab;
import com.example.bookYourCab.model.Driver;
import com.example.bookYourCab.repository.CabRepository;
import com.example.bookYourCab.repository.DriverRepository;
import com.example.bookYourCab.transformer.CabTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CabService {

    @Autowired
    CabRepository cabRepo;

    @Autowired
    DriverRepository driverRepo;

    public List<CabResponse1> getAllCab() {
        List<Cab> cabs = cabRepo.findAll();
        return cabs.stream().map(CabTransformer::cabToCabResponse1).toList();
    }

    public CabResponse registerCab(CabRequest cabRequest, long driverId) {
        //first check if the driver exists or not
        Optional<Driver> d = driverRepo.findById(driverId);

        if(d.isEmpty()){ // if empty then driver doesn't exist
            throw new DriverNotFoundException("Invalid Driver Id");
        }

        Driver driver = d.get(); // getting the driver to set the cab to driver

        Cab cab = CabTransformer.cabReqToCab(cabRequest); // convert to cab request to cab to assign it to driver

        //assign the new cab to the driver
        //updating...
        driver.setCab(cab);

        //whenever we update or set any attribute of the entity we have make sure we save it in the database
        //after updating save the driver and cab
        Driver savedDriver = driverRepo.save(driver); // this will save both parent and child means cb will also get saved

        return CabTransformer.cabToCabResponse(savedDriver.getCab(), savedDriver);

    }
}
