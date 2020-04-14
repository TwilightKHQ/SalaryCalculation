package com.twilightkhq.salarycalculation.Utils;

public class SomeUtils {

    // 将字符窜浮点数 转成 int类型值
    public static int handlePrice(String string) {
        float floatPrice = Float.parseFloat(string);
        return Math.round(floatPrice * 100);
    }
}
