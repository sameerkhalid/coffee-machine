package com.example.demo.model;

import java.util.Map;

public class Machine {
    private int machineId;
    private Map<String,Integer> ingredients;
    private int outlets;

    private Object outletLock;
    private Object ingredientLock;

    public Machine(int machineId, Map<String, Integer> ingredients, int outlets) {
        this.machineId = machineId;
        this.ingredients = ingredients;
        this.outlets = outlets;

        outletLock = new Object();
        ingredientLock = new Object();
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public int getOutlets() {
        return outlets;
    }

    public void setOutlets(int outlets) {
        this.outlets = outlets;
    }

    public Object getOutletLock() {
        return outletLock;
    }

    public Object getIngredientLock() {
        return ingredientLock;
    }
}
