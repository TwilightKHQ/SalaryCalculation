package com.twilightkhq.salarycalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.SalaryList.FragmentEmployeeSalary;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

import java.util.List;

public class CheckSalaryActivity extends AppCompatActivity {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + CheckSalaryActivity.class.getSimpleName();

    private SalaryDao salaryDao;
    private List<EntityStyle> styleList;
    private List<EntityProcess> processList;
    private List<EntityCircuit> circuitList;

    private LinearLayout layoutStyle;
    private LinearLayout layoutProcess;
    private LinearLayout layoutSalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_salary);

        initData();
        initView();
    }

    private void initData() {
        salaryDao = SalaryDao.getInstance(this);
        styleList = salaryDao.getStyleList();
        processList = salaryDao.getProcessList();
        circuitList = salaryDao.getCircuitList();
    }

    private void initView() {
        layoutStyle = (LinearLayout) findViewById(R.id.layout_style);
        checkStylePrice();
        layoutProcess = (LinearLayout) findViewById(R.id.layout_process);
        checkProcess();
        layoutSalary = (LinearLayout) findViewById(R.id.layout_salary);
        checkSalary();
    }

    private void checkStylePrice() {
        for (EntityStyle entityStyle : styleList) {
            int stylePrice = 0;
            for (EntityProcess entityProcess : processList) {
                if (entityProcess.getStyle().equals(entityStyle.getStyle())) {
                    stylePrice += entityProcess.getProcessPrice();
                }
            }

            TextView textStylePrice = new TextView(this);
            layoutStyle.addView(textStylePrice);
            StringBuilder sb = new StringBuilder();

            if (stylePrice == entityStyle.getStylePrice()) {
                sb.append(entityStyle.getStyle()).append(" 款: ").append("单价 无误 .");
                textStylePrice.setText(sb);
            } else {
                sb.append(entityStyle.getStyle()).append(" 款: ").append("单价 有误 .\n");
                sb.append("款式单价为 ").append(SomeUtils.priceToShow(entityStyle.getStylePrice()))
                        .append(" 工序单价和为 ").append(SomeUtils.priceToShow(stylePrice));
                textStylePrice.setText(sb);
                textStylePrice.setTextColor(getResources().getColor(R.color.red, null));
            }
        }
    }

    private void checkProcess() {
        for (EntityStyle entityStyle : styleList) {
            TextView textStyle = new TextView(this);
            layoutProcess.addView(textStyle);
            textStyle.setText(entityStyle.getStyle());
            for (int i = 1; i <= entityStyle.getProcessNumber(); ++i) {
                TextView textProcess = new TextView(this);
                layoutProcess.addView(textProcess);
                for (EntityProcess entityProcess : processList) {
                    if (entityProcess.getStyle().equals(entityStyle.getStyle())
                            && entityProcess.getProcessID() == i) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("工序 ").append(i).append(" 已录入. ");
                        if (entityProcess.getNumber() == entityStyle.getNumber()) {
                            sb.append("工序件数与款式件数 相等 .");
                            textProcess.setText(sb);
                        } else {
                            sb.append("工序件数与款式件数 不等 .");
                            textProcess.setText(sb);
                            textProcess.setTextColor(getResources().getColor(R.color.red, null));
                        }
                    }
                }
                if ("".contentEquals(textProcess.getText())) {
                    String iProcess = "工序 " + i + " 未录入. ";
                    textProcess.setText(iProcess);
                    textProcess.setTextColor(getResources().getColor(R.color.red, null));
                }
            }
        }
    }

    private void checkSalary() {
        int styleSalary = 0;
        int processSalary = 0;
        int circuitSalary = 0;
        for (EntityStyle entityStyle : styleList) {
            styleSalary += entityStyle.getNumber() * entityStyle.getStylePrice();
            Log.d(TAG, "checkSalary: getNumber " + entityStyle.getNumber() + " getStylePrice " + entityStyle.getStylePrice());
            Log.d(TAG, "checkSalary: styleSalary " + styleSalary);
        }
        for (EntityProcess entityProcess : processList) {
            processSalary += entityProcess.getNumber() * entityProcess.getProcessPrice();
        }
        for (EntityCircuit entityCircuit : circuitList) {
            int price = getProcessPrice(entityCircuit.getStyle(), entityCircuit.getProcessID());
            circuitSalary += entityCircuit.getNumber() * price;
        }
        TextView textCalSalary = new TextView(this);
        layoutSalary.addView(textCalSalary);
        StringBuilder sb = new StringBuilder();
        sb.append("款式工资： ").append(SomeUtils.priceToShow(styleSalary)).append("\n");
        sb.append("流程工资： ").append(SomeUtils.priceToShow(processSalary)).append("\n");
        textCalSalary.setText(sb);
        TextView textSalary = new TextView(this);
        layoutSalary.addView(textSalary);
        String realSalary = "实发工资： " + SomeUtils.priceToShow(circuitSalary) + "\n";
        textSalary.setText(realSalary);
        textSalary.setTextColor(getResources().getColor(R.color.red, null));
    }

    private int getProcessPrice(String style, int processID) {
        List<EntityProcess> processList = salaryDao.getProcessList();
        for (EntityProcess entityProcess : processList) {
            if (entityProcess.getStyle().equals(style) && entityProcess.getProcessID() == processID)
                return entityProcess.getProcessPrice();
        }
        return -1;
    }
}