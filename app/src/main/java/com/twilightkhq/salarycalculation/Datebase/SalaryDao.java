package com.twilightkhq.salarycalculation.Datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SalaryDao {

    private Context context;
    private SalaryDao salaryDao = null;

    public SalaryDao getInstance(Context context) {

        if (null == salaryDao) {
            salaryDao = new SalaryDao(context);
        }
        return salaryDao;

    }

    public SalaryDao(Context context) {
        this.context = context;
    }

    public SQLiteDatabase getReadableDb(String dbName) {
        SalaryDBHelper dbHelper = new SalaryDBHelper(context, dbName, null, 1);
        return dbHelper.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDb(String dbName) {
        SalaryDBHelper dbHelper = new SalaryDBHelper(context, dbName, null, 1);
        return dbHelper.getWritableDatabase();
    }

}
