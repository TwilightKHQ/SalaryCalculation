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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Adapter.AdapterArray;
import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentAddProcess extends Fragment implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private boolean processPriceFlag = false;
    private List<String> styles = new ArrayList<>();
    private List<Integer> processNumbers = new ArrayList<>();
    private List<String> processIDs = new ArrayList<>();

    private NiceSpinner spinnerStyle;
    private NiceSpinner spinnerProcessID;
    private EditText editNumber;
    private EditText editProcessPrice;

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

        initData();
        initView(view);

        return view;
    }

    private void initData() {
        List<EntityStyle> styleList = SalaryDao.getInstance(getActivity()).getStyleList();
        styles.clear();
        processNumbers.clear();
        for (EntityStyle entityStyle : styleList) {
            styles.add(entityStyle.getStyle());
            processNumbers.add(entityStyle.getProcessNumber());
        }
        Collections.sort(styles);
        Collections.sort(processNumbers);
        styles.add(0, "请选择款式");
    }

    private void initView(View view) {
        Log.d(TAG, "initView: ");
        spinnerStyle = (NiceSpinner) view.findViewById(R.id.spinner_style);
        spinnerProcessID = (NiceSpinner) view.findViewById(R.id.spinner_process_id);
        final TextView tvChooseProcess = (TextView) view.findViewById(R.id.text_choose_process);
        editNumber = (EditText) view.findViewById(R.id.edit_number);
        editProcessPrice = (EditText) view.findViewById(R.id.edit_process_price);

        Button button = (Button) view.findViewById(R.id.bt_change);
        button.setOnClickListener(this);
        button.setEnabled(processPriceFlag);

        spinnerStyle.attachDataSource(styles);
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
                if (tvChooseProcess.getVisibility() != View.VISIBLE) {
                    tvChooseProcess.setVisibility(View.VISIBLE);
                }
                if (spinnerProcessID.getVisibility() != View.VISIBLE) {
                    spinnerProcessID.setVisibility(View.VISIBLE);
                }
            }
        });
        editProcessPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                processPriceFlag = s.length() >= 1 && !" ".contentEquals(s);
                Log.d(TAG, "onTextChanged: employeeFlag = " + processPriceFlag);
                button.setEnabled(processPriceFlag);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_change) {
            SalaryDao.getInstance(getActivity()).insertProcess(new EntityProcess(
                    spinnerStyle.getSelectedItem().toString(),
                    Integer.parseInt(spinnerProcessID.getSelectedItem().toString()),
                    SomeUtils.handlePrice(editProcessPrice.getText().toString()),
                    Integer.parseInt(editNumber.getText().toString())
            ));
            editNumber.setText("");
            editProcessPrice.setText("");
        }
    }
}