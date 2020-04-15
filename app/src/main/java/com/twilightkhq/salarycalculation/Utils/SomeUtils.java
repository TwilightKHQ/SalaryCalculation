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
            return price.substring(0, price.length() - 2) + "."
                    + price.substring(price.length() - 2);
        } catch (NumberFormatException e) {
            return price;
        }
    }

    public static String priceToShow(int price) {
        return price / 100 + "." + price % 100;
    }
}
