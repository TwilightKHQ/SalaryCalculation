package com.twilightkhq.salarycalculation.AddInformation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAddProcess extends Fragment {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private static final String dbName = "salary";
    private static List<String> styles = new ArrayList<>();
    private static List<Integer> processNumbers = new ArrayList<>();
    private static List<String> processIDs = new ArrayList<>();

    public FragmentAddProcess() {
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
        View view = inflater.inflate(R.layout.fragment_add_process, container, false);

        initView(view);
        queryStyle();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        queryStyle();
    }

    private void initView(View view) {
        Spinner spinnerStyle = (Spinner) view.findViewById(R.id.spinner_style);
        Spinner spinnerProcessID = (Spinner) view.findViewById(R.id.spinner_process_id);
        spinnerStyle.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, styles));
        spinnerProcessID.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, processIDs));
        spinnerStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (DEBUG) {
                    Log.d(TAG, "onItemSelected: selected = " + styles.get(position));
                }
                processIDs.clear();
                for (int i = 1; i <= processNumbers.get(position); i++) {
                    processIDs.add(i + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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