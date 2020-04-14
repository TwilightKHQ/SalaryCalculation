package com.twilightkhq.salarycalculation.Utils;

import android.app.Application;
import android.content.Context;

import com.twilightkhq.salarycalculation.Datebase.SalaryDao;

public class AppUtil extends Application {

    private static Context context;
    private static SalaryDao instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static SalaryDao getSalaryDaoInstance(String dbName) {
        if (instance == null) {
            instance = new SalaryDao(context, dbName);
        }
        return instance;
    }
}
