package com.twilightkhq.salarycalculation.AddInformation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.R;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

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

        initData();
        initView(view);

        return view;
    }

    private void initData() {
        names.clear();
        names = SalaryDao.getInstance(getActivity()).getEmployeeList();
        names.add(0, "请选择员工");

        List<EntityStyle> styleList = SalaryDao.getInstance(getActivity()).getStyleList();
        styles.clear();
        processNumbers.clear();
        for (EntityStyle entityStyle : styleList) {
            styles.add(entityStyle.getStyle());
            processNumbers.add(entityStyle.getProcessNumber());
        }
        styles.add(0, "请选择款式");
        Log.d(TAG, "initData: ");
    }

    private void initView(View view) {
        NiceSpinner spinnerEmployee = (NiceSpinner) view.findViewById(R.id.spinner_employee);
        final NiceSpinner spinnerStyle = (NiceSpinner) view.findViewById(R.id.spinner_style);
        final NiceSpinner spinnerProcessID = (NiceSpinner) view.findViewById(R.id.spinner_process_id);
        final TextView tvChooseStyle = (TextView) view.findViewById(R.id.tv_choose_style);
        final TextView tvChooseProcessID = (TextView) view.findViewById(R.id.tv_choose_process_id);

        spinnerEmployee.attachDataSource(names);
        spinnerEmployee.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner niceSpinner, View view, int position, long l) {
                if (position == names.size() - 1) return;
                if (DEBUG) {
                    Log.d(TAG, "onItemSelected: selected = " + names.get(position));
                }
                spinnerStyle.attachDataSource(styles);
                if (tvChooseStyle.getVisibility() != View.VISIBLE) {
                    tvChooseStyle.setVisibility(View.VISIBLE);
                }
                if (spinnerStyle.getVisibility() != View.VISIBLE) {
                    spinnerStyle.setVisibility(View.VISIBLE);
                }
            }
        });
        spinnerStyle.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner niceSpinner, View view, int position, long l) {
                if (position == styles.size() - 1) return;
                if (DEBUG) {
                    Log.d(TAG, "onItemSelected: selected = " + styles.get(position));
                }
                processIDs.clear();
                for (int i = 1; i <= processNumbers.get(position); i++) {
                    processIDs.add(i + "");
                }
                processIDs.add(0, "请选择工序");
                spinnerProcessID.attachDataSource(processIDs);
                spinnerProcessID.setSelectedIndex(processIDs.size() - 1);
                if (tvChooseProcessID.getVisibility() != View.VISIBLE) {
                    tvChooseProcessID.setVisibility(View.VISIBLE);
                }
                if (spinnerProcessID.getVisibility() != View.VISIBLE) {
                    spinnerProcessID.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}