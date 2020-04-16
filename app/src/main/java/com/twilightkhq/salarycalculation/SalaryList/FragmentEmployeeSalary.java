package com.twilightkhq.salarycalculation.SalaryList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Adapter.AdapterSalaryNodeTree;
import com.twilightkhq.salarycalculation.Adapter.AdapterStyleNodeTree;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.Node.SalaryNode;
import com.twilightkhq.salarycalculation.Entity.Node.TitleNode;
import com.twilightkhq.salarycalculation.Entity.Node.ProcessNode;
import com.twilightkhq.salarycalculation.Provider.ProcessProvider;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentEmployeeSalary extends Fragment {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + FragmentEmployeeSalary.class.getSimpleName();

    private SalaryDao salaryDao;
    private static int salary;
    private static List<BaseNode> nodeList = new ArrayList<>();

    private AdapterSalaryNodeTree adapter = new AdapterSalaryNodeTree();

    public FragmentEmployeeSalary() {
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
        View view = inflater.inflate(R.layout.fragment_employee_salary, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        initData();
        adapter.setList(nodeList);
    }

    private void initData() {
        salaryDao = SalaryDao.getInstance(getActivity());
        List<EntityEmployee> employeeList = salaryDao.getEmployeeList();
        List<EntityCircuit> circuitList = salaryDao.getCircuitList();
        nodeList.clear();
        for (EntityEmployee entityEmployee : employeeList) {
            salary = 0;
            List<BaseNode> secondNodeList = new ArrayList<>();
            secondNodeList.add(new SalaryNode("款式", "工序", "件数", "单价"));
            for (EntityCircuit entityCircuit : circuitList) {
                int processPrice = getProcessPrice(entityCircuit.getStyle(), entityCircuit.getProcessID());
                if (entityCircuit.getName().equals(entityEmployee.getName())) {
                    secondNodeList.add(new SalaryNode(
                            entityCircuit.getStyle(),
                            entityCircuit.getProcessID() + "",
                            entityCircuit.getNumber() + "",
                            SomeUtils.priceToShow(processPrice)
                    ));
                    if (processPrice != -1)
                        salary = salary + processPrice * entityCircuit.getNumber();
                    Log.d(TAG, "initData: salary " + salary + " 款式 " + entityCircuit.getStyle()
                            + " 工序 " + entityCircuit.getProcessID() + " 件数 "
                            + entityCircuit.getNumber() + " 单价 " + SomeUtils.priceToShow(processPrice));
                }
            }
            secondNodeList.add(new SalaryNode("合计", SomeUtils.priceToShow(salary), "元", ""));
            nodeList.add(new TitleNode(secondNodeList, entityEmployee.getName()));
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
}