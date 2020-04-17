package com.twilightkhq.salarycalculation.AddInformation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.R;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentAddCircuit extends Fragment implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + FragmentAddCircuit.class.getSimpleName();

    private SalaryDao salaryDao;
    private EntityCircuit oldCircuit;
    private boolean numberFlag = false;
    private boolean employeeFlag = false;
    private boolean styleFlag = false;
    private boolean processFlag = false;
    private SharedPreferences mSharedPreferences;
    private static List<String> names = new ArrayList<>();
    private static List<String> styles = new ArrayList<>();
    private static List<String> processIDs = new ArrayList<>();
    private static List<Integer> processNumbers = new ArrayList<>();

    private Button button;
    private NiceSpinner spinnerEmployee;
    private NiceSpinner spinnerStyle;
    private NiceSpinner spinnerProcessID;
    private EditText editNumber;
    private LinearLayout layoutChooseStyle;
    private LinearLayout layoutChooseProcess;

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
        layoutChooseStyle = (LinearLayout) view.findViewById(R.id.layout_choose_style);
        spinnerStyle = (NiceSpinner) view.findViewById(R.id.spinner_style);
        layoutChooseProcess = (LinearLayout) view.findViewById(R.id.layout_choose_process);
        spinnerProcessID = (NiceSpinner) view.findViewById(R.id.spinner_process_id);
        editNumber = (EditText) view.findViewById(R.id.edit_number);

        button = (Button) view.findViewById(R.id.bt_change);
        button.setOnClickListener(this);

        spinnerEmployee.attachDataSource(names);
        spinnerEmployee.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner niceSpinner, View view, int position, long l) {
                employeeFlag = position != 0;
                judgeButton();
                if (position == 0) return;
                employeeSelected(position);
            }
        });
        spinnerStyle.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner niceSpinner, View view, int position, long l) {
                styleFlag = position != 0;
                judgeButton();
                if (position == 0) return;
                styleSelected(position);
            }
        });
        spinnerProcessID.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner niceSpinner, View view, int position, long l) {
                processFlag = position != 0;
                judgeButton();
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
                judgeButton();
            }
        });

        intentAction();
        judgeButton();
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.putString("action", "add");
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_change) {
            if (button.getText().equals(getString(R.string.add))) {
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
                        Integer.parseInt(editNumber.getText().toString())
                ));
                editNumber.setText("");
            } else if (button.getText().equals(getString(R.string.change))) {
                salaryDao.updateCircuit(oldCircuit, new EntityCircuit(
                        spinnerEmployee.getSelectedItem().toString(),
                        spinnerStyle.getSelectedItem().toString(),
                        Integer.parseInt(spinnerProcessID.getSelectedItem().toString()),
                        Integer.parseInt(editNumber.getText().toString())
                ));
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("action", "add");
                editor.apply();
                getActivity().onBackPressed();
            }
        }
    }

    private boolean findCircuit(String name, String style, int processID) {
        List<EntityCircuit> circuitList = salaryDao.getCircuitList();
        for (EntityCircuit entityCircuit : circuitList) {
            if (entityCircuit.getName().equals(name) && entityCircuit.getStyle().equals(style)
                    && entityCircuit.getProcessID() == processID) return true;
        }
        return false;
    }

    private EntityCircuit findEntityCircuit(String name, String style, int processID) {
        List<EntityCircuit> circuitList = salaryDao.getCircuitList();
        for (EntityCircuit entityCircuit : circuitList) {
            if (entityCircuit.getName().equals(name) && entityCircuit.getStyle().equals(style)
                    && entityCircuit.getProcessID() == processID) return entityCircuit;
        }
        return null;
    }

    private void intentAction() {
        mSharedPreferences = getActivity().getSharedPreferences("shared",
                AddInformationActivity.MODE_PRIVATE);
        if ("change".equals(mSharedPreferences.getString("action", "add"))
                && mSharedPreferences.getInt("type", -1) == 3) {
            button.setText(R.string.change);
            Log.d(TAG, "intentAction: ");
            String oldName = mSharedPreferences.getString("oldName", "");
            String oldStyle = mSharedPreferences.getString("oldStyle", "");
            String oldProcessID = mSharedPreferences.getString("oldProcessID", "");
            oldCircuit = findEntityCircuit(oldName, oldStyle, Integer.parseInt(oldProcessID));
            Log.d(TAG, "intentAction: oldCircuit " + oldCircuit);
            if (oldCircuit != null) {
                int namePosition = findNamePosition(names, oldName);
                Log.d(TAG, "intentAction: names" + names + " oldName " + oldName);
                Log.d(TAG, "intentAction: namePosition " + namePosition);
                if (namePosition != -1) {
                    spinnerEmployee.setSelectedIndex(namePosition);
                    employeeSelected(namePosition);
                    int stylePosition = findStylePosition(styles, oldStyle);
                    Log.d(TAG, "intentAction: stylePosition " + stylePosition);
                    if (stylePosition != -1) {
                        spinnerStyle.setSelectedIndex(stylePosition);
                        styleSelected(stylePosition);
                        spinnerProcessID.setSelectedIndex(Integer.parseInt(oldProcessID));
                        editNumber.setText(oldCircuit.getNumber() + "");
                    }
                }
            }
        }
    }

    private void employeeSelected(int position) {
        if (DEBUG) {
            Log.d(TAG, "onItemSelected: selected = " + names.get(position));
        }
        spinnerStyle.attachDataSource(styles);
        if (layoutChooseStyle.getVisibility() != View.VISIBLE) {
            layoutChooseStyle.setVisibility(View.VISIBLE);
        }
    }

    private void styleSelected(int position) {
        if (DEBUG) {
            Log.d(TAG, "onItemSelected: selected = " + styles.get(position));
        }
        processIDs.clear();
        for (int i = 1; i <= processNumbers.get(position - 1); i++) {
            processIDs.add(i + "");
        }
        processIDs.add(0, "请选择工序");
        spinnerProcessID.attachDataSource(processIDs);
        if (layoutChooseProcess.getVisibility() != View.VISIBLE) {
            layoutChooseProcess.setVisibility(View.VISIBLE);
        }
    }

    private int findNamePosition(List<String> names, String oldName) {
        for (int i = 0; i < names.size(); ++i) {
            if (names.get(i).equals(oldName)) {
                return i;
            }
        }
        return -1;
    }

    private int findStylePosition(List<String> styles, String style) {
        for (int i = 0; i < styles.size(); ++i) {
            if (styles.get(i).equals(style)) {
                return i;
            }
        }
        return -1;
    }

    private void judgeButton() {
        button.setEnabled(employeeFlag && styleFlag && processFlag && numberFlag);
    }
}