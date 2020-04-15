package com.twilightkhq.salarycalculation.Entity;

import androidx.annotation.NonNull;

public class EntityCircuit {

    private String name;
    private String style;
    private int processID;
    private int number;
    private int processPrice;

    public EntityCircuit(String name, String style, int processID, int number, int processPrice) {
        this.name = name;
        this.style = style;
        this.processID = processID;
        this.number = number;
        this.processPrice = processPrice;
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

    public int getProcessPrice() {
        return processPrice;
    }

    public void setProcessPrice(int processPrice) {
        this.processPrice = processPrice;
    }

    @NonNull
    @Override
    public String toString() {
        return "EntityCircuit{" +
                "name='" + name + '\'' +
                ", style='" + style + '\'' +
                ", processID=" + processID +
                ", number=" + number +
                '}';
    }
}
