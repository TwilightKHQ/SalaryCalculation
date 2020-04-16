package com.twilightkhq.salarycalculation.Utils;

public class SomeUtils {

    // 将字符窜浮点数 转成 int类型值
    public static int handlePrice(String string) {
        float floatPrice = Float.parseFloat(string);
        return Math.round(floatPrice * 100);
    }

    public static String priceToShow(String price) {
        try {
            Integer.parseInt(price);
            String partInteger = null;
            String partDecimal = null;
            if (price.length() >= 2) {
                partInteger = price.substring(0, price.length() - 2);
                partDecimal = price.substring(price.length() - 2);
                if (partInteger.length() < 1) partInteger = "0";
                return partInteger + "." + partDecimal;
            } else {
                return "0";
            }
        } catch (NumberFormatException e) {
            return price;
        }
    }

    public static String priceToShow(int price) {
        return price / 100 + "." + price % 100;
    }
}
