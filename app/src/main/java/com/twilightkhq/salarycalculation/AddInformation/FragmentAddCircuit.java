package com.twilightkhq.salarycalculation.AddInformation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAddCircuit extends Fragment implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private SalaryDao salaryDao;
    private boolean numberFlag = false;
    private static List<String> names = new ArrayList<>();
    private static List<String> styles = new ArrayList<>();
    private static List<String> processIDs = new ArrayList<>();
    private static List<Integer> processNumbers = new ArrayList<>();

    private NiceSpinner spinnerEmployee;
    private NiceSpinner spinnerStyle;
    private NiceSpinner spinnerProcessID;
    private EditText editNumber;

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
        salaryDao = SalaryDao.getInstance(getActivity());
        List<EntityEmployee> employeeList = salaryDao.getEmployeeList();
        names.clear();
        for (EntityEmployee entityEmployee : employeeList) {
            names.add(entityEmployee.getName());
        }
        names.add(0, "请选择员工");
        List<EntityStyle> styleList = salaryDao.getStyleList();
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
        spinnerEmployee = (NiceSpinner) view.findViewById(R.id.spinner_employee);
        final TextView tvChooseStyle = (TextView) view.findViewById(R.id.tv_choose_style);
        spinnerStyle = (NiceSpinner) view.findViewById(R.id.spinner_style);
        final TextView tvChooseProcessID = (TextView) view.findViewById(R.id.tv_choose_process_id);
        spinnerProcessID = (NiceSpinner) view.findViewById(R.id.spinner_process_id);
        editNumber = (EditText) view.findViewById(R.id.edit_number);

        Button button = (Button) view.findViewById(R.id.bt_change);
        button.setOnClickListener(this);
        button.setEnabled(numberFlag);

        spinnerEmployee.attachDataSource(names);
        spinnerEmployee.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner niceSpinner, View view, int position, long l) {
                if (position == 0) return;
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
                if (position == 0) return;
                if (DEBUG) {
                    Log.d(TAG, "onItemSelected: selected = " + styles.get(position));
                }
                processIDs.clear();
                for (int i = 1; i <= processNumbers.get(position - 1); i++) {
                    processIDs.add(i + "");
                }
                processIDs.add(0, "请选择工序");
                spinnerProcessID.attachDataSource(processIDs);
                if (tvChooseProcessID.getVisibility() != View.VISIBLE) {
                    tvChooseProcessID.setVisibility(View.VISIBLE);
                }
                if (spinnerProcessID.getVisibility() != View.VISIBLE) {
                    spinnerProcessID.setVisibility(View.VISIBLE);
                }
            }
        });

        editNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numberFlag = s.length() >= 1 && !" ".contentEquals(s);
                Log.d(TAG, "onTextChanged: employeeFlag = " + numberFlag);
                button.setEnabled(numberFlag);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_change) {
            int price =  findProcessPrice(spinnerStyle.getSelectedItem().toString(),
                    Integer.parseInt(spinnerProcessID.getSelectedItem().toString()));
            if (price == 1000000) {
                Toast.makeText(getActivity(), "未查到对应工序价格", Toast.LENGTH_SHORT).show();
                return;
            }
            if (findCircuit(spinnerEmployee.getSelectedItem().toString(),
                    spinnerStyle.getSelectedItem().toString(),
                    Integer.parseInt(spinnerProcessID.getSelectedItem().toString()))) {
                Toast.makeText(getActivity(), "重复, 请到信息列表中修改！", Toast.LENGTH_SHORT).show();
                return;
            }
            salaryDao.insertCircuit(new EntityCircuit(
                    spinnerEmployee.getSelectedItem().toString(),
                    spinnerStyle.getSelectedItem().toString(),
                    Integer.parseInt(spinnerProcessID.getSelectedItem().toString()),
                    Integer.parseInt(editNumber.getText().toString()),
                    price
            ));
            editNumber.setText("");
        }
    }

    private int findProcessPrice(String style, int processID) {
        List<EntityProcess> processList = salaryDao.getProcessList();
        for (EntityProcess entityProcess : processList) {
            if (entityProcess.getStyle().equals(style) &&entityProcess.getProcessID() == processID) {
                return entityProcess.getProcessPrice();
            }
        }
        return 100000;
    }

    private boolean findCircuit(String name, String style, int processID) {
        List<EntityCircuit> circuitList = salaryDao.getCircuitList();
        for (EntityCircuit entityCircuit : circuitList) {
            if (entityCircuit.getName().equals(name) && entityCircuit.getStyle().equals(style)
            && entityCircuit.getProcessID() == processID) return true;
        }
        return false;
    }
}