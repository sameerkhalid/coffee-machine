package com.example.demo.service;

import com.example.demo.dao.BeverageDao;
import com.example.demo.dao.MachineDao;
import com.example.demo.exception.*;
import com.example.demo.model.Beverage;
import com.example.demo.model.Machine;

import java.util.Map;

public class MachineService {

    //each beverage takes 8 seconds to prepared
    private static long PREPARATION_TIME = 4000l;

    private static MachineService machineService;
    public static MachineService getInstance(){
        if(machineService==null)
            machineService = new MachineService();
        return machineService;
    }

    private MachineDao machineDao;
    private BeverageDao beverageDao;

    public MachineService() {
        machineDao = MachineDao.getInstance();
        beverageDao = BeverageDao.getInstance();
    }

    public Machine getMachine(int machineId){
        Machine machine = machineDao.getMachine(machineId);
        if(machine==null)
            throw new MachineNotFoundException(String.valueOf(machineId));
        return machine;
    }

    public Beverage serveBeverage(int machineID, String beverageName){
        Beverage beverage = beverageDao.getBeverage(beverageName);
        Machine machine = machineDao.getMachine(machineID);

        if(machine==null)
            throw new MachineNotFoundException(String.valueOf(machineID));
        if(beverage==null)
            throw new BeverageNotFoundException(beverageName);

        //reduce outlet by 1 ... occupying one outlet
        synchronized (machine.getOutletLock()){
            int outlets = machine.getOutlets();
            if(outlets==0)
                throw new NoFreeOutletException(machineID);
            machineDao.updateOutlets(machineID,outlets-1);
        }

        //reduce ingredients required for the beverage
        synchronized (machine.getIngredientLock()){
            Map<String,Integer> machineIngredients = machine.getIngredients();
            for(Map.Entry<String,Integer> e:beverage.getIngredients().entrySet()){
                Integer avail = machineIngredients.get(e.getKey());
                if(avail==null)
                    throw new IngredientNotFoundException(machineID,e.getKey());
                if(avail<e.getValue())
                    throw new IngredientInsufficientException(machineID, e.getKey());
            }
            for(Map.Entry<String,Integer> e:beverage.getIngredients().entrySet()){
                Integer avail = machineIngredients.get(e.getKey());
                machineDao.updateIngredient(machineID,e.getKey(),avail-e.getValue());
//                machineIngredients.put(e.getKey(),avail-e.getValue());
            }
        }

        try {
            Thread.sleep(PREPARATION_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (machine.getOutletLock()){
            int outlets = machine.getOutlets();
            machineDao.updateOutlets(machineID,outlets+1);
        }

        return beverage;
    }

    public Machine createMachine(int outlets, Map<String,Integer> ingredients){
        return machineDao.createMachine(outlets,ingredients);
    }

    public Machine addIngredient(int machineId, String ingredient, int amount){
        Machine machine = machineDao.getMachine(machineId);
        if(machine==null)
            throw new MachineNotFoundException(String.valueOf(machineId));
        synchronized (machine.getIngredientLock()){
            Integer avail = machine.getIngredients().get(ingredient);
            avail = avail==null?0:avail;
            machineDao.updateIngredient(machineId,ingredient,avail+amount);
        }
        return machine;
    }
}
