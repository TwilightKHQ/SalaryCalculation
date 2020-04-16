package com.twilightkhq.salarycalculation.AddInformation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private static String TAG = "--zzq--" + AddInformationActivity.class.getSimpleName();

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initView();
    }

    private void initView() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

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
        AdapterFragment adapterFragment = new AdapterFragment(getSupportFragmentManager(),
                AddInformationActivity.this, fragmentList, titleList);
        viewPager.setAdapter(adapterFragment);
        tabLayout.setupWithViewPager(viewPager);

        intentAction();
    }

    private void intentAction() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", -1);
        if (type == -1) return;
        viewPager.setCurrentItem(type);
        SharedPreferences mSharedPreferences = getSharedPreferences("shared",
                AddInformationActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("action", "change");
        editor.putInt("type", type);
        switch (type) {
            case 0:
                Log.d(TAG, "intentAction: 修改员工");
                editor.putString("oldName", intent.getStringExtra("name"));
                break;
            case 1:
                Log.d(TAG, "intentAction: 修改款式");
                editor.putString("oldStyle", intent.getStringExtra("style"));
                break;
            case 2:
                Log.d(TAG, "intentAction: 修改工序");
                editor.putString("oldStyle", intent.getStringExtra("style"));
                editor.putString("oldProcessID", intent.getStringExtra("processID"));
                break;
            case 3:
                Log.d(TAG, "intentAction: 修改流程");
                editor.putString("oldName", intent.getStringExtra("name"));
                editor.putString("oldStyle", intent.getStringExtra("style"));
                editor.putString("oldProcessID", intent.getStringExtra("processID"));
                break;
        }
        editor.apply();
    }
}