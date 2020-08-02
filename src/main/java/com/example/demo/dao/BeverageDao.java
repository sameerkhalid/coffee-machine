package com.example.demo.dao;

import com.example.demo.exception.BeverageExistsException;
import com.example.demo.exception.BeverageNotFoundException;
import com.example.demo.model.Beverage;

import java.util.HashMap;
import java.util.Map;

public class BeverageDao {

    //singleton...........................
    private static BeverageDao beverageDao;
    public static BeverageDao getInstance(){
        if(beverageDao==null)
            beverageDao = new BeverageDao();
        return beverageDao;
    }
    //....................................

    private Map<String, Beverage> beverages;


    public BeverageDao() {
        beverages = new HashMap<>();
    }

    synchronized public Beverage createBeverage(Beverage beverage){
        if(beverages.containsKey(beverage.getName()))
            return null;
        beverages.put(beverage.getName(),beverage);
        return beverage;
    }

    public Beverage getBeverage(String name){
        Beverage beverage = beverages.get(name);
        return beverage;
    }

    synchronized public Beverage updateBeverage(Beverage beverage){
        String name = beverage.getName();
        Beverage oldBeverage = beverages.get(name);
        if(oldBeverage == null)
            return null;
        beverages.put(name,beverage);
        return beverage;
    }

    synchronized public Beverage removeBeverage(String name) {
        Beverage beverage = beverages.get(name);
        if(beverage==null)
            return null;
        beverages.remove(name);
        return beverage;
    }
}
