package com.example.demo.exception;

public class NoFreeOutletException extends RuntimeException{
    private int machineId;
    public NoFreeOutletException(int machineId) {
        super("Machine "+ machineId + " doesn't have any free outlets now. Please try later.");
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
