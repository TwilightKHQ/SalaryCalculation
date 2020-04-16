package com.twilightkhq.salarycalculation.AddInformation;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.twilightkhq.salarycalculation.Adapter.AdapterArray;
import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
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

public class FragmentAddProcess extends Fragment implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + FragmentAddProcess.class.getSimpleName();

    private SalaryDao salaryDao;
    private EntityProcess oldProcess;
    private boolean processPriceFlag = false;
    private boolean processStyleFlag = false;
    private boolean processProcessFlag = false;
    private boolean processNumberFlag = false;
    private SharedPreferences mSharedPreferences;
    private List<String> styles = new ArrayList<>();
    private List<String> numbers = new ArrayList<>();
    private List<String> processIDs = new ArrayList<>();
    private List<Integer> processNumbers = new ArrayList<>();

    private Button button;
    private NiceSpinner spinnerStyle;
    private NiceSpinner spinnerProcessID;
    private EditText editNumber;
    private EditText editProcessPrice;
    private TextView tvChooseProcess;

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

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.putString("action", "add");
        editor.apply();
    }

    private void initData() {
        salaryDao = SalaryDao.getInstance(getActivity());
        List<EntityStyle> styleList = salaryDao.getStyleList();
        styles.clear();
        numbers.clear();
        processNumbers.clear();
        for (EntityStyle entityStyle : styleList) {
            styles.add(entityStyle.getStyle());
            numbers.add(entityStyle.getNumber() + "");
            processNumbers.add(entityStyle.getProcessNumber());
        }
        styles.add(0, "请选择款式");
    }

    private void initView(View view) {
        Log.d(TAG, "initView: ");
        spinnerStyle = (NiceSpinner) view.findViewById(R.id.spinner_style);
        spinnerProcessID = (NiceSpinner) view.findViewById(R.id.spinner_process_id);
        tvChooseProcess = (TextView) view.findViewById(R.id.text_choose_process);
        editNumber = (EditText) view.findViewById(R.id.edit_number);
        editProcessPrice = (EditText) view.findViewById(R.id.edit_process_price);

        button = (Button) view.findViewById(R.id.bt_change);
        button.setOnClickListener(this);

        spinnerStyle.attachDataSource(styles);
        spinnerStyle.setSelectedIndex(0);
        spinnerStyle.getSelectedIndex();
        Log.d(TAG, "initView: SelectedIndex " + spinnerStyle.getSelectedIndex());
        spinnerStyle.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner niceSpinner, View view, int position, long l) {
                processStyleFlag = position != 0;
                judgeButton();
                if (position == 0) return;
                styleSelected(position);
            }
        });
        spinnerProcessID.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner niceSpinner, View view, int position, long l) {
                processProcessFlag = position != 0;
                judgeButton();
                editNumber.setText(numbers.get(spinnerStyle.getSelectedIndex() - 1));
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
        editNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                processNumberFlag = s.length() >= 1 && !" ".contentEquals(s);
                judgeButton();
            }
        });

        intentAction();
        judgeButton();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_change) {
            if (button.getText().equals(getString(R.string.add))) {
                if (findProcess(spinnerStyle.getSelectedItem().toString(),
                        Integer.parseInt(spinnerProcessID.getSelectedItem().toString()))) {
                    Toast.makeText(getActivity(), "该工序已录入", Toast.LENGTH_SHORT).show();
                    return;
                }
                salaryDao.insertProcess(new EntityProcess(
                        spinnerStyle.getSelectedItem().toString(),
                        Integer.parseInt(spinnerProcessID.getSelectedItem().toString()),
                        SomeUtils.handlePrice(editProcessPrice.getText().toString()),
                        Integer.parseInt(editNumber.getText().toString())
                ));
            } else if (button.getText().equals(getString(R.string.change))) {
                salaryDao.updateProcess(oldProcess, new EntityProcess(
                        spinnerStyle.getSelectedItem().toString(),
                        Integer.parseInt(spinnerProcessID.getSelectedItem().toString()),
                        SomeUtils.handlePrice(editProcessPrice.getText().toString()),
                        Integer.parseInt(editNumber.getText().toString())
                ));
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("action", "add");
                editor.apply();
                getActivity().onBackPressed();
            }
            editNumber.setText("");
            editProcessPrice.setText("");
        }
    }

    private boolean findProcess(String style, int processID) {
        List<EntityProcess> processList = salaryDao.getProcessList();
        for (EntityProcess entityProcess : processList) {
            if (entityProcess.getStyle().equals(style) && entityProcess.getProcessID() == processID)
                return true;
        }
        return false;
    }

    private EntityProcess findEntityProcess(String style, int processID) {
        List<EntityProcess> processList = salaryDao.getProcessList();
        for (EntityProcess entityProcess : processList) {
            if (entityProcess.getStyle().equals(style) && entityProcess.getProcessID() == processID)
                return entityProcess;
        }
        return null;
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
        if (tvChooseProcess.getVisibility() != View.VISIBLE) {
            tvChooseProcess.setVisibility(View.VISIBLE);
        }
        if (spinnerProcessID.getVisibility() != View.VISIBLE) {
            spinnerProcessID.setVisibility(View.VISIBLE);
        }
    }

    private void intentAction() {
        mSharedPreferences = getActivity().getSharedPreferences("shared",
                AddInformationActivity.MODE_PRIVATE);
        if ("change".equals(mSharedPreferences.getString("action", "add"))
                && mSharedPreferences.getInt("type", -1) == 2) {
            button.setText(R.string.change);
            String oldStyle = mSharedPreferences.getString("oldStyle", "");
            String oldProcessID = mSharedPreferences.getString("oldProcessID", "");
            oldProcess = findEntityProcess(oldStyle, Integer.parseInt(oldProcessID));
            if (oldProcess != null) {
                int position = findPosition(styles, oldStyle);
                if (position != -1) {
                    spinnerStyle.setSelectedIndex(position);
                    styleSelected(position);
                    spinnerProcessID.setSelectedIndex(Integer.parseInt(oldProcessID));
                    editNumber.setText(oldProcess.getNumber() + "");
                    editProcessPrice.setText(SomeUtils.priceToShow(oldProcess.getProcessPrice() + ""));
                }
            }
        }
    }

    private int findPosition(List<String> styles, String style) {
        for (int i = 0; i < styles.size(); ++i) {
            if (styles.get(i).equals(style)) {
                return i;
            }
        }
        return -1;
    }

    private void judgeButton() {
        button.setEnabled(processStyleFlag && processProcessFlag &&
                processPriceFlag && processNumberFlag);
    }
}