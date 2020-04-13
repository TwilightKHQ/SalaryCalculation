package com.twilightkhq.salarycalculation.Entity;

public class EntityProcess {

    private String style;
    private int processId;
    private int processPrice;
    private int number;

    public EntityProcess(String style, int processId, int processPrice, int number) {
        this.style = style;
        this.processId = processId;
        this.processPrice = processPrice;
        this.number = number;
    }

    public String getStyle() {
        return style;
    }

    public int getProcessId() {
        return processId;
    }

    public int getProcessPrice() {
        return processPrice;
    }

    public int getNumber() {
        return number;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public void setProcessPrice(int processPrice) {
        this.processPrice = processPrice;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
