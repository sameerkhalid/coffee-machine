package com.example.demo.model;

import java.util.Map;

public class Beverage {
    private String name;
    private Map<String,Integer> ingredients;

    public Beverage(String name, Map<String, Integer> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, Integer> ingredients) {
        this.ingredients = ingredients;
    }
}
