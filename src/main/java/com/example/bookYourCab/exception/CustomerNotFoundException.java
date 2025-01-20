package com.example.bookYourCab.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String e){
        super(e);
    }
}
