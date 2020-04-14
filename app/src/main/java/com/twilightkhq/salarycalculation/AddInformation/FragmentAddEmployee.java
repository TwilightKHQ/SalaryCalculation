package com.twilightkhq.salarycalculation.AddInformation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.R;

public class FragmentAddEmployee extends Fragment implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private boolean employeeFlag = false;

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

    private void initView(View view) {
        Button button = (Button) view.findViewById(R.id.bt_change);
        button.setOnClickListener(this);
        button.setEnabled(employeeFlag);

        editEmployee = (EditText) view.findViewById(R.id.edit_employee);
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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_change) {
            SalaryDao.getInstance(getActivity()).insertEmployee(new EntityEmployee(
                    editEmployee.getText().toString())
            );
            editEmployee.setText("");
        }
    }
}