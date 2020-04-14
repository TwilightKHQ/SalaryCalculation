package com.twilightkhq.salarycalculation.SalaryList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Adapter.Adapter2FloorNodeTree;
import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.FirstNode;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.SecondNode;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentEmployeeSalary extends Fragment {

    private static final String dbName = "salary.db";

    private static int salary = 0;
    private static List<BaseNode> nodeList = new ArrayList<>();
    private Adapter2FloorNodeTree adapter = new Adapter2FloorNodeTree();

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
        queryEmployee();
        adapter.setList(nodeList);

        return view;
    }

    private void queryEmployee() {
        SalaryDBHelper dbHelper = new SalaryDBHelper(getActivity(), dbName, null, 1);
        SQLiteDatabase database =  dbHelper.getReadableDatabase();
        Cursor cursor = database.query("employee", null,
                null, null, null, null, "name");
        nodeList.clear();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            List<BaseNode> secondNodeList = new ArrayList<>();
            queryCircuit(name, secondNodeList);
            nodeList.add(new FirstNode(secondNodeList, name));
        }
        cursor.close();
        database.close();
    }

    private void queryCircuit(String name, List<BaseNode> baseNodeList) {
        baseNodeList.add(new SecondNode("款式", "工序", "件数"));
        SalaryDBHelper dbHelper = new SalaryDBHelper(getActivity(), dbName, null, 1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("circuit",
                new String[]{"name", "style", "process_id", "number"}, "name=?",
                new String[]{name}, null, null, "style");
        while (cursor.moveToNext()) {
            baseNodeList.add(new SecondNode(cursor.getString(cursor.getColumnIndex("style")),
                    cursor.getInt(cursor.getColumnIndex("process_id")) + "",
                    cursor.getInt(cursor.getColumnIndex("number")) + ""));
        }
        cursor.close();
        database.close();
    }
}