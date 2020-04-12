package com.twilightkhq.salarycalculation.AddInformation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAddCircuit extends Fragment {
    
    private static final String dbName = "salary";
    private static List<String> names = new ArrayList<>();
    private static List<String> styles = new ArrayList<>();
    private static List<Integer> processNumbers = new ArrayList<>();
    private static List<String> processIDs = new ArrayList<>();

    public FragmentAddCircuit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_circuit, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
    }

    private void queryEmployee() {
        SalaryDBHelper dbHelper = new SalaryDBHelper(getActivity(), dbName, null, 1);
        SQLiteDatabase database =  dbHelper.getReadableDatabase();
        Cursor cursor = database.query("employee", new String[]{"id", "name"},
                null, null, null, null, null);
        names.clear();
        while (cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
        Collections.sort(names);
        database.close();
    }

    private void queryStyle() {
        SalaryDBHelper dbHelper = new SalaryDBHelper(getActivity(), dbName, null, 1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("style", null, null,
                null, null, null, null);
        styles.clear();
        while (cursor.moveToNext()) {
            styles.add(cursor.getString(cursor.getColumnIndex("style")));
            processNumbers.add(cursor.getInt(cursor.getColumnIndex("process_number")));
        }
        cursor.close();
        Collections.sort(styles);
        database.close();
    }
}