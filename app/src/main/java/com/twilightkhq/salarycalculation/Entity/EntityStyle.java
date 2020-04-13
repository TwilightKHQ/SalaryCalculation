package com.twilightkhq.salarycalculation.Entity;

public class EntityStyle {

    private String style;
    private int processNumber;
    private int stylePrice;
    private int number;

    public EntityStyle(String style, int processNumber, int stylePrice, int number) {
        this.style = style;
        this.processNumber = processNumber;
        this.stylePrice = stylePrice;
        this.number = number;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getProcessNumber() {
        return processNumber;
    }

    public void setProcessNumber(int processNumber) {
        this.processNumber = processNumber;
    }

    public int getStylePrice() {
        return stylePrice;
    }

    public void setStylePrice(int stylePrice) {
        this.stylePrice = stylePrice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
