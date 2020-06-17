package com.twilightkhq.salarycalculation.Activity.AddInformation;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.R;

import java.util.List;

public class FragmentAddEmployee extends Fragment implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + FragmentAddProcess.class.getSimpleName();

    private boolean employeeFlag = false;
    private EntityEmployee oldEmployee;
    private SharedPreferences mSharedPreferences;

    private Button button;
    private EditText editEmployee;

    public FragmentAddEmployee() {
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
        View view = inflater.inflate(R.layout.fragment_add_employee, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.putString("action", "add");
        editor.apply();
    }

    private void initView(View view) {
        button = view.findViewById(R.id.bt_change);
        button.setOnClickListener(this);

        editEmployee = view.findViewById(R.id.edit_employee);
        editEmployee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                employeeFlag = s.length() >= 1 && !" ".contentEquals(s);
                Log.d(TAG, "onTextChanged: employeeFlag = " + employeeFlag);
                button.setEnabled(employeeFlag);
            }
        });
        intentAction();
        button.setEnabled(employeeFlag);
    }

    private void intentAction() {
        mSharedPreferences = getActivity().getSharedPreferences("shared",
                AddInformationActivity.MODE_PRIVATE);
        if ("change".equals(mSharedPreferences.getString("action", "add"))
                && mSharedPreferences.getInt("type", -1) == 0) {
            button.setText(R.string.change);
            String oldName = mSharedPreferences.getString("oldName", "");
            oldEmployee = new EntityEmployee(oldName);
            editEmployee.setText(oldName);
            editEmployee.setSelection(editEmployee.getText().length());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_change) {
            if (button.getText().equals(getString(R.string.add))) {
                if (findPersonName(editEmployee.getText().toString())) {
                    Toast.makeText(getActivity(), "员工重名", Toast.LENGTH_SHORT).show();
                    return;
                }
                SalaryDao.getInstance(getActivity()).insertEmployee(new EntityEmployee(
                        editEmployee.getText().toString()
                ));
                editEmployee.setText("");
            } else if (button.getText().equals(getString(R.string.change))) {
                SalaryDao.getInstance(getActivity()).updateEmployee(oldEmployee, new EntityEmployee(
                        editEmployee.getText().toString()
                ));
                getActivity().onBackPressed();
            }
        }
    }

    private boolean findPersonName(String name) {
        List<EntityEmployee> employeeList = SalaryDao.getInstance(getActivity()).getEmployeeList();
        for (EntityEmployee entityEmployee : employeeList) {
            if (entityEmployee.getName().equals(name)) return true;
        }
        return false;
    }
}