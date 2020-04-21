package com.twilightkhq.salarycalculation.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.Entity.Node.SalaryNode;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImportExportActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + ImportExportActivity.class.getSimpleName();

    private SalaryDao salaryDao;
    private List<EntityEmployee> employeeList;
    private List<EntityStyle> styleList;
    private List<EntityProcess> processList;
    private List<EntityCircuit> circuitList;

    private String Comma_Separation = ",";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_export);

        initData();
        initView();

        initPermission();
    }

    private void initPermission() {
        List<String> permissionList = new ArrayList<>();
        //一次性申请多个权限
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    private void initData() {
        salaryDao = SalaryDao.getInstance(this);
        employeeList = salaryDao.getEmployeeList();
        styleList = salaryDao.getStyleList();
        processList = salaryDao.getProcessList();
        circuitList = salaryDao.getCircuitList();
    }

    private void initView() {
        Button btImport = findViewById(R.id.import_salary);
        btImport.setOnClickListener(this);
        Button btExport = findViewById(R.id.export_salary);
        btExport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.import_salary:
                Toast.makeText(this, "暂时未制作！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.export_salary:
                Toast.makeText(this, "正在导出列表", Toast.LENGTH_SHORT).show();
                createSalaryCsv();
                break;
        }
    }

    private void createSalaryCsv() {
        //  FIXME:需要适配不同版本的储存系统
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        try {
            File file;
            Log.d(TAG, "createSalaryCsv: SDK_INT " + Build.VERSION.SDK_INT);
            Log.d(TAG, "createSalaryCsv: path " + getExternalFilesDir(Environment.DIRECTORY_MOVIES));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.) {
//                file = new File(this.getFilesDir().getAbsolutePath() +
//                        File.separator + "工资导出" + simpleDateFormat.format(date) + ".csv");
//                Log.d(TAG, "createSalaryCsv: >= Q file.path " + file.toString());
//            } else {
            file = new File(Environment.getExternalStoragePublicDirectory("") +
                    File.separator + "工资导出" + simpleDateFormat.format(date) + ".csv");
            Log.d(TAG, "createSalaryCsv: file.path " + file.toString());
//            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write("时间:," + simpleDateFormat.format(date));
            bw.newLine();
            // 写员工名单
            bw.newLine();
            bw.write("本月员工:");
            for (EntityEmployee entityEmployee : employeeList) {
                bw.write(entityEmployee.getName() + Comma_Separation);
            }
            bw.newLine();
            // 写款式信息
            bw.newLine();
            bw.write("款式,工序数量,件数,款式单价");
            bw.newLine();
            for (EntityStyle entityStyle : styleList) {
                bw.write(entityStyle.getStyle()
                        + Comma_Separation + entityStyle.getProcessNumber()
                        + Comma_Separation + entityStyle.getNumber()
                        + Comma_Separation + SomeUtils.priceToShow(entityStyle.getStylePrice())
                );
                for (int i = 1; i <= entityStyle.getProcessNumber(); ++i) {
                    boolean flag = true;
                    for (EntityProcess entityProcess : processList) {
                        if (entityProcess.getStyle().equals(entityStyle.getStyle())
                                && entityProcess.getProcessID() == i) {
                            bw.write(Comma_Separation
                                    + SomeUtils.priceToShow(entityProcess.getProcessPrice()));
                            flag = false;
                        }
                    }
                    if (flag) bw.write(Comma_Separation + "-1");
                }
                bw.newLine();
            }
            bw.newLine();
            // 写工序表
            bw.newLine();
            bw.write("款式,工序,工序单价,工序件数,款式件数");
            bw.newLine();
            for (EntityStyle entityStyle : styleList) {
                for (int i = 1; i <= entityStyle.getProcessNumber(); ++i) {
                    boolean flag = true;
                    for (EntityProcess entityProcess : processList) {
                        if (entityProcess.getStyle().equals(entityStyle.getStyle())
                                && entityProcess.getProcessID() == i) {
                            bw.write(entityStyle.getStyle()
                                    + Comma_Separation + i
                                    + Comma_Separation + SomeUtils.priceToShow(entityProcess.getProcessPrice())
                                    + Comma_Separation + entityProcess.getNumber()
                                    + Comma_Separation + entityStyle.getNumber());

                            flag = false;
                        }
                    }
                    if (flag) bw.write(entityStyle.getStyle()
                            + Comma_Separation + i
                            + Comma_Separation + "-1"
                            + Comma_Separation + "-1"
                            + Comma_Separation + entityStyle.getNumber());
                    bw.newLine();
                }
            }
            bw.newLine();
            // 写员工工资
            bw.newLine();
            for (EntityEmployee entityEmployee : employeeList) {
                bw.write(entityEmployee.getName());
                bw.newLine();
                int salary = 0;
                bw.write("款式,工序,件数,单价");
                bw.newLine();
                for (EntityCircuit entityCircuit : circuitList) {
                    int processPrice = getProcessPrice(entityCircuit.getStyle(), entityCircuit.getProcessID());
                    if (entityCircuit.getName().equals(entityEmployee.getName())) {
                        bw.write(entityCircuit.getStyle()
                                + Comma_Separation + entityCircuit.getProcessID()
                                + Comma_Separation + entityCircuit.getNumber()
                                + Comma_Separation + SomeUtils.priceToShow(processPrice));
                        bw.newLine();
                        if (processPrice != -1)
                            salary = salary + processPrice * entityCircuit.getNumber();
                    }
                }
                bw.write("合计:," + SomeUtils.priceToShow(salary) + ",元");
                bw.newLine();
                bw.newLine();
            }
            // 写总工资
            bw.newLine();
            bw.newLine();
            int styleSalary = 0;
            int processSalary = 0;
            int circuitSalary = 0;
            for (EntityStyle entityStyle : styleList) {
                styleSalary += entityStyle.getNumber() * entityStyle.getStylePrice();
            }
            for (EntityProcess entityProcess : processList) {
                processSalary += entityProcess.getNumber() * entityProcess.getProcessPrice();
            }
            for (EntityCircuit entityCircuit : circuitList) {
                int price = getProcessPrice(entityCircuit.getStyle(), entityCircuit.getProcessID());
                circuitSalary += entityCircuit.getNumber() * price;
            }
            bw.write("款式工资:," + SomeUtils.priceToShow(styleSalary));
            bw.newLine();
            bw.write("流程工资:," + SomeUtils.priceToShow(processSalary));
            bw.newLine();
            String realSalary = "实发工资:," + SomeUtils.priceToShow(circuitSalary);
            bw.write(realSalary);
            bw.newLine();
            bw.close();
            Toast.makeText(this, "导出完成。", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getProcessPrice(String style, int processID) {
        List<EntityProcess> processList = salaryDao.getProcessList();
        for (EntityProcess entityProcess : processList) {
            if (entityProcess.getStyle().equals(style) && entityProcess.getProcessID() == processID)
                return entityProcess.getProcessPrice();
        }
        return -1;
    }

    private int getProcessNumber(String style, int processID) {
        List<EntityProcess> processList = salaryDao.getProcessList();
        for (EntityProcess entityProcess : processList) {
            if (entityProcess.getStyle().equals(style) && entityProcess.getProcessID() == processID)
                return entityProcess.getNumber();
        }
        return -1;
    }

    //判断是否获取所有权限，必须拥有全部权限程序才运行
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "必须同意所有权限才能正常使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}