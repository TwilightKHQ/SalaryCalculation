package com.twilightkhq.salarycalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.twilightkhq.salarycalculation.Activity.AddInformation.AddInformationActivity;
import com.twilightkhq.salarycalculation.Activity.ImportExportActivity;
import com.twilightkhq.salarycalculation.Activity.InformationList.InformationListActivity;
import com.twilightkhq.salarycalculation.Activity.SalaryList.SalaryListActivity;
import com.twilightkhq.salarycalculation.Activity.CheckSalaryActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addClickListener();
    }

    private void addClickListener() {
        Button btInformationList = (Button) findViewById(R.id.information_list);
        btInformationList.setOnClickListener(this);
        Button btAddInformation = (Button) findViewById(R.id.add_information);
        btAddInformation.setOnClickListener(this);
        Button btSalaryList = (Button) findViewById(R.id.salary_list);
        btSalaryList.setOnClickListener(this);
        Button btCheckSalary = (Button) findViewById(R.id.check_salary);
        btCheckSalary.setOnClickListener(this);
        Button btImportExport = (Button) findViewById(R.id.input_output);
        btImportExport.setOnClickListener(this);
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
            case R.id.check_salary:
                startActivity(new Intent(MainActivity.this, CheckSalaryActivity.class));
                break;
            case R.id.salary_list:
                startActivity(new Intent(MainActivity.this, SalaryListActivity.class));
                break;
            case R.id.input_output:
                startActivity(new Intent(MainActivity.this, ImportExportActivity.class));
                break;
        }
    }
}