package com.twilightkhq.salarycalculation.Entity;

import androidx.annotation.NonNull;

public class EntityCircuit {

    private String name;
    private String style;
    private int processID;
    private int number;

    public EntityCircuit(String name, String style, int processID, int number) {
        this.name = name;
        this.style = style;
        this.processID = processID;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getProcessID() {
        return processID;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
