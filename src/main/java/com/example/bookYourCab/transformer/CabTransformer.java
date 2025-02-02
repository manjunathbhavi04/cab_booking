package com.example.bookYourCab.transformer;

import com.example.bookYourCab.dto.request.CabRequest;
import com.example.bookYourCab.dto.response.CabResponse;
import com.example.bookYourCab.dto.response.CabResponse1;
import com.example.bookYourCab.model.Cab;
import com.example.bookYourCab.model.Driver;

public class CabTransformer {
    public static Cab cabReqToCab(CabRequest cabReq){
        return Cab.builder()
                .model(cabReq.getModel())
                .carNum(cabReq.getCarNum())
                .pricePerKm(cabReq.getPricePerKm())
                .available(true)
                .build();
    }

    public static CabResponse cabToCabResponse(Cab cab, Driver driver){
        return CabResponse.builder()
                .carNum(cab.getCarNum())
                .model(cab.getModel())
                .pricePerKm(cab.getPricePerKm())
                .available(cab.isAvailable())
                .driver(DriverTransformer.DriverToDriverResponse(driver))
                .build();
    }

    public static CabResponse1 cabToCabResponse1(Cab cab){
        return CabResponse1.builder()
                .carNum(cab.getCarNum())
                .model(cab.getModel())
                .pricePerKm(cab.getPricePerKm())
                .available(cab.isAvailable())
                .build();
    }


}
