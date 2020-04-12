package com.twilightkhq.salarycalculation.AddInformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.tabs.TabLayout;
import com.twilightkhq.salarycalculation.Adapter.AdapterFragment;
import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.List;

public class AddInformationActivity extends AppCompatActivity {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private static int currentViewPagePosition = 0;
    private AdapterFragment adapterFragment;
    private static final String dbName = "salary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initView();
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        Button btChange = (Button) findViewById(R.id.bt_change);

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentViewPagePosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                currentViewPagePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) {
                    Log.d(TAG, "onClick: currentViewPagePosition " + currentViewPagePosition);
                }
                insertData(currentViewPagePosition);
//                switch (currentViewPagePosition) {
//                    case 0:
//                        insertEmployee();
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                }
            }
        });
    }

    private void insertData(int type) {
        if (DEBUG) {
            Log.d(TAG, "insertEmployee: 插入信息");
        }
        SalaryDBHelper dbHelper = new SalaryDBHelper(AddInformationActivity.this,
                dbName, null, 1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        View fragmentView = adapterFragment.getItem(type).getView();
        if (fragmentView != null) {
            switch (type) {
                case 0:
                    EditText editEmployee = (EditText) fragmentView.findViewById(R.id.edit_employee);
                    contentValues.put("name", editEmployee.getText().toString());
                    database.insert("employee", null, contentValues);
                    editEmployee.setText("");
                    break;
                case 1:
                    EditText editStyle = (EditText) fragmentView.findViewById(R.id.edit_style);
                    EditText editNumber = (EditText) fragmentView.findViewById(R.id.edit_number);
                    EditText editStylePrice = (EditText) fragmentView.findViewById(R.id.edit_style_price);
                    EditText editProcessNumber = (EditText) fragmentView.findViewById(R.id.edit_process_number);
                    contentValues.put("style", editStyle.getText().toString());
                    contentValues.put("number", Integer.valueOf(editNumber.getText().toString()));
                    contentValues.put("style_price", Integer.valueOf(editStylePrice.getText().toString()));
                    contentValues.put("process_number", Integer.valueOf(editProcessNumber.getText().toString()));
                    editStyle.setText("");
                    editNumber.setText("");
                    editStylePrice.setText("");
                    editProcessNumber.setText("");
                    break;
                case 2:
                    break;
                case 3:
                    break;

            }
        }
        database.close();
    }

//    private void insertEmployee() {
//        EditText editEmployee = (EditText) adapterFragment.getItem(0)
//                .getView().findViewById(R.id.edit_employee);
//        SalaryDBHelper dbHelper = new SalaryDBHelper(AddInformationActivity.this,
//                dbName, null, 1);
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", editEmployee.getText().toString());
//        database.insert("employee", null, contentValues);
//        database.close();
//        if (DEBUG) {
//            Log.d(TAG, "insertEmployee: 插入员工");
//            Log.d(TAG, "insertEmployee: name = " + editEmployee.getText());
//        }
//        editEmployee.setText("");
//    }
}