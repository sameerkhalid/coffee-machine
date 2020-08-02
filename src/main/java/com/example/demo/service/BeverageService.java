package com.example.demo.service;

import com.example.demo.dao.BeverageDao;
import com.example.demo.exception.BeverageExistsException;
import com.example.demo.exception.BeverageNotFoundException;
import com.example.demo.model.Beverage;
import org.hibernate.dialect.IngresDialect;

import java.util.List;
import java.util.Map;

public class BeverageService {

    private static BeverageService beverageService;
    public static BeverageService getInstance(){
        if(beverageService == null)
            beverageService = new BeverageService();
        return beverageService;
    }
    private BeverageDao beverageDao;
    public BeverageService() {
        beverageDao = BeverageDao.getInstance();
    }

    public Beverage createBeverage(String name, Map<String, Integer> ingredients){
        Beverage beverage = new Beverage(name, ingredients);
        Beverage result = beverageDao.createBeverage(beverage);
        if(result == null)
            throw new BeverageExistsException(name);
        return result;
    }

    public Beverage updateBeverage(String name, Map<String, Integer> ingredients){
        Beverage beverage = new Beverage(name, ingredients);
        Beverage result = beverageDao.updateBeverage(beverage);
        if(result==null)
            throw new BeverageNotFoundException(name);
        return result;
    }

    public Beverage removeBeverage(String name){
        Beverage beverage = beverageDao.removeBeverage(name);
        if(beverage==null)
            throw new BeverageNotFoundException(name);
        return beverage;
    }
}
