package com.twilightkhq.salarycalculation.InformationList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.twilightkhq.salarycalculation.AddInformation.AddInformationActivity;
import com.twilightkhq.salarycalculation.AddInformation.FragmentAddProcess;
import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentEmployee extends Fragment {

    private SalaryDao salaryDao;
    private ArrayAdapter<String> adapter;
    private static List<String> names = new ArrayList<>();


    public FragmentEmployee() {
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
        View view = inflater.inflate(R.layout.fragment_employee, container, false);

        initData();
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                new XPopup.Builder(getContext())
                        .asBottomList("请选择一项", new String[]{"修改", "删除"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        if (position == 0) {
                                            Intent intent = new Intent(getActivity(), AddInformationActivity.class);
                                            intent.putExtra("type", 0);
                                            intent.putExtra("name", names.get(i));
                                            startActivityForResult(intent, 10);
                                        }
                                        if (position == 1) {
                                            salaryDao.deleteEmployee(
                                                    new EntityEmployee(names.get(i))
                                            );
                                            names.remove(names.get(i));
                                            adapter.notifyDataSetChanged();
                                            initData();
                                        }
                                    }
                                })
                        .show();
                return false;
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        salaryDao = SalaryDao.getInstance(getActivity());
        List<EntityEmployee> employeeList = salaryDao.getEmployeeList();
        names.clear();
        for (EntityEmployee entityEmployee : employeeList) {
            names.add(entityEmployee.getName());
        }
    }
}