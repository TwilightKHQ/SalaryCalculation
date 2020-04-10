package com.twilightkhq.salarycalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.twilightkhq.salarycalculation.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "---zzq---debug";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        addClickListener();
    }

    private void addClickListener() {
        binding.informationList.setOnClickListener(this);
        binding.addInformation.setOnClickListener(this);
        binding.salaryList.setOnClickListener(this);
        binding.checkSalary.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.information_list:
                startActivity(new Intent(MainActivity.this, InformationListActivity.class));
                break;
            case R.id.add_information:
                startActivity(new Intent(MainActivity.this, AddInformationActivity.class));
                break;
            case R.id.salary_list:
                startActivity(new Intent(MainActivity.this, SalaryListActivity.class));
                break;
            case R.id.check_salary:
                startActivity(new Intent(MainActivity.this, CheckSalaryActivity.class));
                break;
        }
    }
}