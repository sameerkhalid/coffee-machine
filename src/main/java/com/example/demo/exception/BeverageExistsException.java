package com.example.demo.exception;

public class BeverageExistsException extends RuntimeException{
    private String beverageName;
    public BeverageExistsException(String beverageName) {
        super("Beverage '"+beverageName+"' already exists");
        this.beverageName = beverageName;
    }

    public String getBeverageName() {
        return beverageName;
    }

    public void setBeverageName(String beverageName) {
        this.beverageName = beverageName;
    }
}
