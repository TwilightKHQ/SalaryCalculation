package com.twilightkhq.salarycalculation.Datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SalaryDBHelper extends SQLiteOpenHelper {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug--";

    //员工表 记录 员工名
    private static final String createTableEmployee = "create table if not exists "
            + "employee" + " ("
            + "name varchar primary key"
            + ")";
    //款式表 记录 款式 工序数量 款式单价 件数
    private static final String createTableStyle = "create table if not exists "
            + "style" + " ("
            + "style varchar primary key,"
            + "process_number int,"
            + "style_price int,"
            + "number int"
            + ")";
    //工序表 记录 款式 工序号 工序单价 件数
    private static final String createTableProcess = "create table if not exists "
            + "process" + " ("
            + "id INTEGER primary key AUTOINCREMENT,"
            + "style varchar,"
            + "process_id int,"
            + "process_price int,"
            + "number int"
            + ")";
    //流程表 记录 员工名 款式 工序号 件数 工序单价
    private static final String createTableCircuit = "create table if not exists "
            + "circuit" + " ("
            + "id INTEGER primary key AUTOINCREMENT,"
            + "name varchar,"
            + "style varchar,"
            + "process_id int,"
            + "number int"
            + ")";

    public SalaryDBHelper(@Nullable Context context, @Nullable String name,
                          @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (DEBUG) {
            Log.d(TAG, "onCreate: 创建数据库和表");
        }
        db.execSQL(createTableEmployee);
        db.execSQL(createTableStyle);
        db.execSQL(createTableProcess);
        db.execSQL(createTableCircuit);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DEBUG) {
            Log.d(TAG, "onUpgrade: 更新数据库版本，清理当前存在的表并重建");
        }
        db.execSQL("drop table if exists " + "employee");
        db.execSQL("drop table if exists " + "style");
        db.execSQL("drop table if exists " + "process");
        db.execSQL("drop table if exists " + "circuit");
        onCreate(db);
    }
}
