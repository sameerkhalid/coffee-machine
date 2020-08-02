package com.example.demo.dao;

import com.example.demo.model.Machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineDao {

    private static MachineDao machineDao;


    public MachineDao() {
        machines = new HashMap<>();
    }

    public static MachineDao getInstance(){
        if(machineDao==null)
            machineDao = new MachineDao();
        return machineDao;
    }

    private Map<Integer, Machine> machines;

    public List<Machine> getAllMachines(){
        List<Machine> machineList = new ArrayList<>();
        for(Map.Entry<Integer,Machine> e:machines.entrySet()){
            machineList.add(e.getValue());
        }
        return machineList;
    }

    public Machine getMachine(int machineId){
        return machines.get(machineId);
    }

    //synchronized so that two machines don't have same ids when craeting
    synchronized public Machine createMachine(int outlets, Map<String,Integer> ingredients){
        int machineId = machines.size()+1;
        Machine machine = new Machine(machineId,ingredients,outlets);
        machines.put(machineId,machine);
        return machine;
    }

    public Machine updateIngredient(int machineID, String ingredient, int amount) {
        Machine machine = machines.get(machineID);
        if(machine==null)
            return null;
        machine.getIngredients().put(ingredient,amount);
        return machine;
    }

    public Machine updateOutlets(int machineID, int outlets) {
        Machine machine = machines.get(machineID);
        if(machine==null)
            return null;
        machine.setOutlets(outlets);
        return machine;
    }
}
