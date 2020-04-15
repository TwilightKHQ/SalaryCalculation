package com.twilightkhq.salarycalculation.AddInformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.tabs.TabLayout;
import com.twilightkhq.salarycalculation.Adapter.AdapterFragment;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.R;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddInformationActivity extends AppCompatActivity {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private AdapterFragment adapterFragment;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

//        btChange = (Button) findViewById(R.id.bt_change);
//        btChange.setOnClickListener(this);

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        fragmentList.add(new FragmentAddEmployee());
        fragmentList.add(new FragmentAddStyle());
        fragmentList.add(new FragmentAddProcess());
        fragmentList.add(new FragmentAddCircuit());
        titleList.add(getResources().getString(R.string.add_employee));
        titleList.add(getResources().getString(R.string.add_style));
        titleList.add(getResources().getString(R.string.add_process));
        titleList.add(getResources().getString(R.string.add_circuit));
        adapterFragment = new AdapterFragment(getSupportFragmentManager(),
                AddInformationActivity.this, fragmentList, titleList);
        viewPager.setAdapter(adapterFragment);
        tabLayout.setupWithViewPager(viewPager);

        initListener();
    }

    private void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void insertData(int type) {
        View fragmentView = adapterFragment.getItem(type).getView();
        if (fragmentView != null) {
            NiceSpinner spinnerStyle;
            NiceSpinner spinnerProcessID;
            EditText editNumber;
            switch (type) {
                case 0:
//                    EditText editEmployee = (EditText) fragmentView.findViewById(R.id.edit_employee);
//                    SalaryDao.getInstance(this).insertEmployee(new EntityEmployee(editEmployee.getText().toString()));
//                    editEmployee.setText("");
                    break;
                case 1:
//                    EditText editStyle = (EditText) fragmentView.findViewById(R.id.edit_style);
//                    editNumber = (EditText) fragmentView.findViewById(R.id.edit_number);
//                    EditText editStylePrice = (EditText) fragmentView.findViewById(R.id.edit_style_price);
//                    EditText editProcessNumber = (EditText) fragmentView.findViewById(R.id.edit_process_number);
//                    SalaryDao.getInstance(this).insertStyle(new EntityStyle(
//                            editStyle.getText().toString(),
//                            Integer.parseInt(editProcessNumber.getText().toString()),
//                            handlePrice(editStylePrice.getText().toString()),
//                            Integer.parseInt(editNumber.getText().toString())
//                    ));
//                    editStyle.setText("");
//                    editNumber.setText("");
//                    editStylePrice.setText("");
//                    editProcessNumber.setText("");
                    break;
                case 2:
//                    spinnerStyle = (NiceSpinner) fragmentView.findViewById(R.id.spinner_style);
//                    spinnerProcessID = (NiceSpinner) fragmentView.findViewById(R.id.spinner_process_id);
//                    editNumber = (EditText) fragmentView.findViewById(R.id.edit_number);
//                    EditText editProcessPrice = (EditText) fragmentView.findViewById(R.id.edit_process_price);
//                    SalaryDao.getInstance(this).insertProcess(new EntityProcess(
//                            spinnerStyle.getSelectedItem().toString(),
//                            Integer.parseInt(spinnerProcessID.getSelectedItem().toString()),
//                            handlePrice(editProcessPrice.getText().toString()),
//                            Integer.parseInt(editNumber.getText().toString())
//                    ));
//                    editNumber.setText("");
//                    editProcessPrice.setText("");
                    break;
                case 3:
//                    NiceSpinner spinnerEmployee = (NiceSpinner) fragmentView.findViewById(R.id.spinner_employee);
//                    spinnerStyle = (NiceSpinner) fragmentView.findViewById(R.id.spinner_style);
//                    spinnerProcessID = (NiceSpinner) fragmentView.findViewById(R.id.spinner_process_id);
//                    editNumber = (EditText) fragmentView.findViewById(R.id.edit_number);
//                    SalaryDao.getInstance(this).insertCircuit(new EntityCircuit(
//                            spinnerEmployee.getSelectedItem().toString(),
//                            spinnerStyle.getSelectedItem().toString(),
//                            Integer.parseInt(spinnerProcessID.getSelectedItem().toString()),
//                            Integer.parseInt(editNumber.getText().toString())
//                    ));
//                    editNumber.setText("");
                    break;
            }
        }
    }

//    @Override
//    public void onClick(View v) {
////        if (v.getId() == R.id.bt_change) {
////            insertData(currentViewPagePosition);
////        }
//    }

    // 将字符窜浮点数 转成 int类型值
    private int handlePrice(String string) {
        float floatPrice = Float.parseFloat(string);
        return Math.round(floatPrice * 100);
    }
}