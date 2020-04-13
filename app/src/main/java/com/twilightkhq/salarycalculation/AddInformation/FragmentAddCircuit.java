package com.twilightkhq.salarycalculation.AddInformation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Adapter.AdapterArray;
import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAddCircuit extends Fragment {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private static final String dbName = "salary.db";
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

        queryEmployee();
        queryStyle();
        initView(view);

        return view;
    }

    private void initView(View view) {
        Spinner spinnerEmployee = (Spinner) view.findViewById(R.id.spinner_employee);
        final Spinner spinnerStyle = (Spinner) view.findViewById(R.id.spinner_style);
        final Spinner spinnerProcessID = (Spinner) view.findViewById(R.id.spinner_process_id);
        final TextView tvChooseStyle = (TextView) view.findViewById(R.id.tv_choose_style);
        final TextView tvChooseProcessID = (TextView) view.findViewById(R.id.tv_choose_process_id);
        spinnerEmployee.setAdapter(new AdapterArray<String>(getActivity(),
                android.R.layout.simple_list_item_1, names));
        spinnerEmployee.setSelection(names.size() - 1, true);
        spinnerEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (DEBUG) {
                    Log.d(TAG, "onItemSelected: selected = " + names.get(position));
                }
                spinnerStyle.setAdapter(new AdapterArray<String>(getActivity(),
                        android.R.layout.simple_list_item_1, styles));
                spinnerStyle.setSelection(styles.size() - 1, true);
                if (tvChooseStyle.getVisibility() != View.VISIBLE) {
                    tvChooseStyle.setVisibility(View.VISIBLE);
                }
                if (spinnerStyle.getVisibility() != View.VISIBLE) {
                    spinnerStyle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != styles.size() - 1) {
                    if (DEBUG) {
                        Log.d(TAG, "onItemSelected: selected = " + styles.get(position));
                    }
                    processIDs.clear();
                    for (int i = 1; i <= processNumbers.get(position); i++) {
                        processIDs.add(i + "");
                    }
                    processIDs.add("请选择工序");
                    spinnerProcessID.setAdapter(new AdapterArray<String>(getActivity(),
                            android.R.layout.simple_list_item_1, processIDs));
                    spinnerProcessID.setSelection(processIDs.size() - 1, true);
                    if (tvChooseProcessID.getVisibility() != View.VISIBLE) {
                        tvChooseProcessID.setVisibility(View.VISIBLE);
                    }
                    if (spinnerProcessID.getVisibility() != View.VISIBLE) {
                        spinnerProcessID.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void queryEmployee() {
        SalaryDBHelper dbHelper = new SalaryDBHelper(getActivity(), dbName, null, 1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("employee", new String[]{"id", "name"},
                null, null, null, null, null);
        names.clear();
        while (cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
        Collections.sort(names);
        names.add("请选择员工");
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
        styles.add("请选择款式");
        database.close();
    }
}