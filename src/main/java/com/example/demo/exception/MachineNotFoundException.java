package com.example.demo.exception;

public class MachineNotFoundException extends ResourceNotFoundException{
    public MachineNotFoundException(String resource) {
        super("Machine", resource);
    }
}
