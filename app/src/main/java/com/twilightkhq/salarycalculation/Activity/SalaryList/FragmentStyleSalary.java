package com.twilightkhq.salarycalculation.Activity.SalaryList;

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
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.Entity.Node.EmployeeNode;
import com.twilightkhq.salarycalculation.Entity.Node.SalaryNode;
import com.twilightkhq.salarycalculation.Entity.Node.TitleNode;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

import java.util.ArrayList;
import java.util.List;

public class FragmentStyleSalary extends Fragment {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + FragmentStyleSalary.class.getSimpleName();

    private SalaryDao salaryDao;
    private static List<BaseNode> nodeList = new ArrayList<>();

    private AdapterSalaryNodeTree adapter = new AdapterSalaryNodeTree();

    public FragmentStyleSalary() {
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
        View view = inflater.inflate(R.layout.fragment_style_salary, container, false);

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
        List<EntityStyle> styleList = salaryDao.getStyleList();
        List<EntityEmployee> employeeList = salaryDao.getEmployeeList();
        List<EntityCircuit> circuitList = salaryDao.getCircuitList();
        nodeList.clear();
        for (EntityStyle entityStyle : styleList) {
            List<BaseNode> secondNodeList = new ArrayList<>();
            for (EntityEmployee entityEmployee : employeeList) {
                List<BaseNode> thirdNodeList = new ArrayList<>();
                thirdNodeList.add(new SalaryNode("款式", "工序", "件数", "单价"));
                for (EntityCircuit entityCircuit : circuitList) {
                    int processPrice = getProcessPrice(entityCircuit.getStyle(), entityCircuit.getProcessID());
                    if (entityCircuit.getName().equals(entityEmployee.getName())) {
                        thirdNodeList.add(new SalaryNode(
                                entityCircuit.getStyle(),
                                entityCircuit.getProcessID() + "",
                                entityCircuit.getNumber() + "",
                                SomeUtils.priceToShow(processPrice)
                        ));
                        Log.d(TAG, "initData: 款式 " + entityCircuit.getStyle()
                                + " 工序 " + entityCircuit.getProcessID() + " 件数 "
                                + entityCircuit.getNumber() + " 单价 " + SomeUtils.priceToShow(processPrice));
                    }
                }
                secondNodeList.add(new EmployeeNode(thirdNodeList, entityEmployee.getName()));
            }
            nodeList.add(new TitleNode(secondNodeList, entityStyle.getStyle()));
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