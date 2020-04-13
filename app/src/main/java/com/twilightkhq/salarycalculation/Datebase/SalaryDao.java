package com.twilightkhq.salarycalculation.Datebase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;

import java.util.ArrayList;
import java.util.List;

public class SalaryDao {

    private Context context;
    private static final String dbName = "salary.db";
    private List<EntityCircuit> circuitList = new ArrayList<>();

    public SalaryDao(Context context) {
        this.context = context;
    }

    private SQLiteDatabase getReadableDb() {
        SalaryDBHelper dbHelper = new SalaryDBHelper(context, SalaryDao.dbName, null, 1);
        return dbHelper.getReadableDatabase();
    }

    private SQLiteDatabase getWritableDb(String dbName) {
        SalaryDBHelper dbHelper = new SalaryDBHelper(context, dbName, null, 1);
        return dbHelper.getWritableDatabase();
    }

    private void closeDb(SQLiteDatabase db) {
        db.close();
    }

    public List<EntityStyle> queryStyleTable() {
        SQLiteDatabase db = getReadableDb();
        Cursor cursor = db.query("style", null, null,
                null, null, null, "style");
        List<EntityStyle> styleList = new ArrayList<>();
        while (cursor.moveToNext()) {
            styleList.add(new EntityStyle(cursor.getString(cursor.getColumnIndex("style")),
                    cursor.getInt(cursor.getColumnIndex("process_number")),
                    cursor.getInt(cursor.getColumnIndex("style_price")),
                    cursor.getInt(cursor.getColumnIndex("number"))));
        }
        cursor.close();
        closeDb(db);
        return styleList;
    }

    public List<EntityProcess> queryProcessTable() {
        SQLiteDatabase db = getReadableDb();
        Cursor cursor = db.query("process", null, null,
                null, null, null, "style,process_id");
        List<EntityProcess> processList = new ArrayList<>();
        while (cursor.moveToNext()) {
            processList.add(new EntityProcess(cursor.getString(cursor.getColumnIndex("style")),
                    cursor.getInt(cursor.getColumnIndex("process_id")),
                    cursor.getInt(cursor.getColumnIndex("process_price")),
                    cursor.getInt(cursor.getColumnIndex("number"))));
        }
        cursor.close();
        closeDb(db);
        return processList;
    }

    public List<EntityCircuit> queryCircuitTable() {
        SQLiteDatabase db = getReadableDb();
        Cursor cursor = db.query("circuit", null, null,
                null, null, null, "name,style,process_id");
        List<EntityCircuit> circuitList = new ArrayList<>();
        while (cursor.moveToNext()) {
            circuitList.add(new EntityCircuit(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("style")),
                    cursor.getInt(cursor.getColumnIndex("process_id")),
                    cursor.getInt(cursor.getColumnIndex("number"))));
        }
        cursor.close();
        closeDb(db);
        for (EntityCircuit circuit : circuitList) {
            Log.d("zzq", "queryCircuitTable: circuit = " + circuit.toString());
        }
        return circuitList;
    }

}
