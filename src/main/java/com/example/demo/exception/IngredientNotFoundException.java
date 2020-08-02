package com.example.demo.exception;

public class IngredientNotFoundException extends RuntimeException{
    private int machineId;
    private String ingredient;

    public IngredientNotFoundException(int machineId, String ingredient) {
        super("Ingredient " + ingredient + " unavailable in Machine " + machineId);
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
