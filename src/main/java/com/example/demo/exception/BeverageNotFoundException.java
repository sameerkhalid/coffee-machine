package com.example.demo.exception;

public class BeverageNotFoundException extends ResourceNotFoundException{
    public BeverageNotFoundException(String resourceName) {
        super("Beverage", resourceName);
    }
}
