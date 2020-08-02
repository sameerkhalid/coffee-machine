package com.example.demo.exception;

public class IngredientInsufficientException extends RuntimeException {
    private int machineId;
    private String ingredient;

    public IngredientInsufficientException(int machineID, String ingredient) {
        super("Machine "+ machineID + " doesn't have enough " + ingredient);
        this.machineId = machineId;
        this.ingredient = ingredient;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
