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

import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.View.DialogBottom;

import java.util.ArrayList;
import java.util.Collection;
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

        ListView listView = (ListView) view.findViewById(R.id.list_view);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, names);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogBottom dialogBottom = new DialogBottom();
                dialogBottom.show(getActivity().getSupportFragmentManager(), "dialogBottom");
                return false;
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        queryEmployee();
    }

    private void queryEmployee() {
        SalaryDBHelper dbHelper = new SalaryDBHelper(getActivity(), dbName, null, 1);
        SQLiteDatabase database =  dbHelper.getReadableDatabase();
        Cursor cursor = database.query("employee", new String[]{"id", "name"},
                null, null, null, null, null);
        names.clear();
        while (cursor.moveToNext()) {
            names.add(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
        Collections.sort(names);
        database.close();
    }
}