package com.twilightkhq.salarycalculation.SalaryList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Adapter.AdapterStyleNodeTree;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.Node.TitleNode;
import com.twilightkhq.salarycalculation.Entity.Node.ProcessNode;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentEmployeeSalary extends Fragment {

    private static final String dbName = "salary.db";

    private static int salary = 0;
    private static List<BaseNode> nodeList = new ArrayList<>();

    private AdapterStyleNodeTree adapter = new AdapterStyleNodeTree();

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

        initData();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setList(nodeList);

        return view;
    }

    private void initData() {
        List<EntityEmployee> employeeList = SalaryDao.getInstance(getActivity()).getEmployeeList();
        List<EntityCircuit> circuitList = SalaryDao.getInstance(getActivity()).getCircuitList();
        nodeList.clear();
        for (EntityEmployee entityEmployee : employeeList) {
            List<BaseNode> secondNodeList = new ArrayList<>();
            secondNodeList.add(new ProcessNode("款式", "工序", "件数"));
            for (EntityCircuit entityCircuit : circuitList) {
                if (entityCircuit.getName().equals(entityEmployee.getName())) {
                    secondNodeList.add(new ProcessNode(entityCircuit.getStyle(),
                            entityCircuit.getProcessID() + "",
                            entityCircuit.getNumber() + ""));
                }
            }
            nodeList.add(new TitleNode(secondNodeList, entityEmployee.getName()));
        }
    }
}