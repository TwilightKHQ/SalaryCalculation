package com.twilightkhq.salarycalculation.Datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;

import java.util.ArrayList;
import java.util.List;

public class SalaryDao {

    private Context context;
    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private String dbName = "salary.db";
    private static SalaryDao instance = null;

    private List<EntityEmployee> employeeList = new ArrayList<>();
    private List<EntityStyle> styleList = new ArrayList<>();
    private List<EntityProcess> processList = new ArrayList<>();
    private List<EntityCircuit> circuitList = new ArrayList<>();

    public static SalaryDao getInstance(Context context) {
        if (instance == null) {
            instance = new SalaryDao(context);
        }
        return instance;
    }

    private SalaryDao(Context context) {
        this.context = context;
        queryEmployeeTable();
        queryStyleTable();
        queryProcessTable();
        queryCircuitTable();
    }

    private SQLiteDatabase getReadableDb(String dbName) {
        SalaryDBHelper dbHelper = new SalaryDBHelper(context, dbName, null, 1);
        return dbHelper.getReadableDatabase();
    }

    private SQLiteDatabase getWritableDb(String dbName) {
        SalaryDBHelper dbHelper = new SalaryDBHelper(context, dbName, null, 1);
        return dbHelper.getWritableDatabase();
    }

    private void closeDb(SQLiteDatabase db) {
        db.close();
    }

    private void queryEmployeeTable() {
        Log.d(TAG, "queryEmployeeTable: ");
        SQLiteDatabase db = getReadableDb(dbName);
        Cursor cursor = db.query("employee", null, null,
                null, null, null, "name");
        employeeList.clear();
        while (cursor.moveToNext()) {
            employeeList.add(new EntityEmployee(cursor.getString(cursor.getColumnIndex("name"))));
            Log.d(TAG, "queryEmployeeTable: employeeList size = " + employeeList.size());
        }
        cursor.close();
        closeDb(db);
    }

    private void queryStyleTable() {
        SQLiteDatabase db = getReadableDb(dbName);
        Cursor cursor = db.query("style", null, null,
                null, null, null, "style");
        styleList.clear();
        while (cursor.moveToNext()) {
            styleList.add(new EntityStyle(cursor.getString(cursor.getColumnIndex("style")),
                    cursor.getInt(cursor.getColumnIndex("process_number")),
                    cursor.getInt(cursor.getColumnIndex("style_price")),
                    cursor.getInt(cursor.getColumnIndex("number"))));
        }
        cursor.close();
        closeDb(db);
    }

    private void queryProcessTable() {
        SQLiteDatabase db = getReadableDb(dbName);
        Cursor cursor = db.query("process", null, null,
                null, null, null, "style,process_id");
        processList.clear();
        while (cursor.moveToNext()) {
            processList.add(new EntityProcess(cursor.getString(cursor.getColumnIndex("style")),
                    cursor.getInt(cursor.getColumnIndex("process_id")),
                    cursor.getInt(cursor.getColumnIndex("process_price")),
                    cursor.getInt(cursor.getColumnIndex("number"))));
        }
        cursor.close();
        closeDb(db);
    }

    private void queryCircuitTable() {
        SQLiteDatabase db = getReadableDb(dbName);
        Cursor cursor = db.query("circuit", null, null,
                null, null, null, "name,style,process_id");
        circuitList.clear();
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
    }

    public void insertEmployee(EntityEmployee entityEmployee) {
        SQLiteDatabase db = getWritableDb(dbName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", entityEmployee.getName());
        db.insert("employee", null, contentValues);
        closeDb(db);
        queryEmployeeTable();
    }

    public void insertStyle(EntityStyle entityStyle) {
        SQLiteDatabase db = getWritableDb(dbName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("style", entityStyle.getStyle());
        contentValues.put("process_number", entityStyle.getProcessNumber());
        contentValues.put("style_price", entityStyle.getStylePrice());
        contentValues.put("number", entityStyle.getNumber());
        db.insert("style", null, contentValues);
        closeDb(db);
        queryStyleTable();
    }

    public void insertProcess(EntityProcess entityProcess) {
        SQLiteDatabase db = getWritableDb(dbName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("style", entityProcess.getStyle());
        contentValues.put("process_id", entityProcess.getProcessID());
        contentValues.put("process_price", entityProcess.getProcessPrice());
        contentValues.put("number", entityProcess.getNumber());
        db.insert("process", null, contentValues);
        closeDb(db);
        queryProcessTable();
    }

    public void insertCircuit(EntityCircuit entityCircuit) {
        SQLiteDatabase db = getWritableDb(dbName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", entityCircuit.getName());
        contentValues.put("process_id", entityCircuit.getProcessID());
        contentValues.put("style", entityCircuit.getStyle());
        contentValues.put("number", entityCircuit.getNumber());
        db.insert("process", null, contentValues);
        closeDb(db);
        queryCircuitTable();
    }

    public void deleteEmployee(EntityEmployee entityEmployee) {
        SQLiteDatabase db = getWritableDb(dbName);
        db.delete("employee", "name=?", new String[]{entityEmployee.getName()});
        db.delete("circuit", "name=?", new String[]{entityEmployee.getName()});
        closeDb(db);
        queryEmployeeTable();
        queryCircuitTable();
    }

    public void deleteStyle(EntityStyle entityStyle) {
        SQLiteDatabase db = getWritableDb(dbName);
        db.delete("style", "style=?", new String[]{entityStyle.getStyle()});
        db.delete("process", "style=?", new String[]{entityStyle.getStyle()});
        db.delete("circuit", "style=?", new String[]{entityStyle.getStyle()});
        closeDb(db);
        queryStyleTable();
        queryProcessTable();
        queryCircuitTable();
    }

    public void deleteProcess(EntityProcess entityProcess) {
        SQLiteDatabase db = getWritableDb(dbName);
        db.delete("process", "style=? and process_id=?",
                new String[]{entityProcess.getStyle(), entityProcess.getProcessID() + ""});
        closeDb(db);
        queryProcessTable();
    }

    public void deleteCircuit(EntityCircuit entityCircuit) {
        SQLiteDatabase db = getWritableDb(dbName);
        db.delete("circuit", "name=? and style=? and process_id=?",
                new String[]{entityCircuit.getName(), entityCircuit.getStyle(), entityCircuit.getProcessID() + ""});
        closeDb(db);
        queryCircuitTable();
    }

    public void updateEmployee(EntityEmployee oldEmployee, EntityEmployee newEmployee) {
        SQLiteDatabase db = getWritableDb(dbName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newEmployee.getName());
        db.update("employee", contentValues, "name=?", new String[]{oldEmployee.getName()});
        db.update("circuit", contentValues, "name=?", new String[]{oldEmployee.getName()});
        db.close();
        queryEmployeeTable();
        queryCircuitTable();
    }

    public void updateStyle(EntityStyle oldStyle, EntityStyle newStyle) {
        SQLiteDatabase db = getWritableDb(dbName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("style", newStyle.getStyle());
        if (!oldStyle.getStyle().equals(newStyle.getStyle())) {
            db.update("process", contentValues, "style=?", new String[]{oldStyle.getStyle()});
            db.update("circuit", contentValues, "style=?", new String[]{oldStyle.getStyle()});
        }
        contentValues.put("process_number", newStyle.getProcessNumber());
        contentValues.put("style_price", newStyle.getStylePrice());
        contentValues.put("number", newStyle.getNumber());
        db.update("style", contentValues, "style=?", new String[]{oldStyle.getStyle()});
        db.close();
        queryStyleTable();
        queryProcessTable();
        queryCircuitTable();
    }

    public void updateProcess(EntityProcess oldProcess, EntityProcess newProcess) {
        SQLiteDatabase db = getWritableDb(dbName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("style", newProcess.getStyle());
        contentValues.put("process_id", newProcess.getProcessID());
        contentValues.put("process_price", newProcess.getProcessPrice());
        contentValues.put("number", newProcess.getNumber());
        db.update("process", contentValues, "style=? and process_id=?",
                new String[]{oldProcess.getStyle(), oldProcess.getProcessID() + ""});
        db.close();
        queryProcessTable();
    }

    public void updateCircuit(EntityCircuit oldCircuit, EntityCircuit newCircuit) {
        SQLiteDatabase db = getWritableDb(dbName);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newCircuit.getName());
        contentValues.put("style", newCircuit.getStyle());
        contentValues.put("process_id", newCircuit.getProcessID());
        contentValues.put("number", newCircuit.getNumber());
        db.update("circuit", contentValues, "name=? and style=? and process_id=?",
                new String[]{oldCircuit.getName(), oldCircuit.getStyle(), oldCircuit.getProcessID() + ""});
        db.close();
        queryCircuitTable();
    }

    public List<EntityEmployee> getEmployeeList() {
        Log.d(TAG, "getEmployeeList: employeeList " + employeeList.size());
        return employeeList;
    }

    public List<EntityStyle> getStyleList() {
        return styleList;
    }

    public List<EntityProcess> getProcessList() {
        return processList;
    }

    public List<EntityCircuit> getCircuitList() {
        return circuitList;
    }

    public void updateEmployee() {
        queryEmployeeTable();
    }

    public void updateStyle() {
        queryStyleTable();
    }

    public void updateProcess() {
        queryProcessTable();
    }

    public void updateCircuit() {
        queryCircuitTable();
    }
}
