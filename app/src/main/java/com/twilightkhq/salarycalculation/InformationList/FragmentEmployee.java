package com.twilightkhq.salarycalculation.InformationList;

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
import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentEmployee extends Fragment {

    private static final String dbName = "salary.db";
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
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new XPopup.Builder(getContext())
                        .asBottomList("请选择一项", new String[]{"修改", "删除"},
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        Toast.makeText(getContext(), "员工" + text, Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
                return false;
            }
        });
        return view;
    }

    private void initData() {
        List<EntityEmployee> employeeList = SalaryDao.getInstance(getActivity()).getEmployeeList();
        names.clear();
        for (EntityEmployee entityEmployee : employeeList) {
            names.add(entityEmployee.getName());
        }
    }
}