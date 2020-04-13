package com.twilightkhq.salarycalculation.InformationList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.twilightkhq.salarycalculation.Adapter.Adapter2FloorNodeTree;
import com.twilightkhq.salarycalculation.Datebase.SalaryDBHelper;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.FirstNode;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.SecondNode;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.AppUtil;
import com.twilightkhq.salarycalculation.View.DialogBottom;

import java.util.ArrayList;
import java.util.List;

public class FragmentStyle extends Fragment {

    private static final String dbName = "salary.db";
    private Adapter2FloorNodeTree adapter = new Adapter2FloorNodeTree();
    private static List<BaseNode> nodeList = new ArrayList<>();

    public FragmentStyle() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_style, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        queryStyle();
        adapter.setList(nodeList);
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Log.d("zzq", "onItemLongClick: position = " + position);
                if (adapter.getItemViewType(position) == 1) {
                    DialogBottom dialogBottom = new DialogBottom();
                    dialogBottom.show(getActivity().getSupportFragmentManager(), "dialogBottom");
                }
                return false;
            }
        });

        return view;
    }

    private void queryStyle() {
        SalaryDBHelper dbHelper = new SalaryDBHelper(getActivity(), dbName, null, 1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("style", null, null,
                null, null, null, null);
        nodeList.clear();
        while (cursor.moveToNext()) {
            String style = cursor.getString(cursor.getColumnIndex("style"));
            List<BaseNode> secondNodeList = new ArrayList<>();
            secondNodeList.add(new SecondNode("工序数量", "件数", "款式单价"));
            secondNodeList.add(new SecondNode(cursor.getInt(cursor.getColumnIndex("process_number")) + "",
                    cursor.getInt(cursor.getColumnIndex("number")) + "",
                    cursor.getInt(cursor.getColumnIndex("style_price")) + ""
            ));
            queryProcess(style, secondNodeList);
            nodeList.add(new FirstNode(secondNodeList, style));
        }
        cursor.close();
        database.close();
    }

    private void queryProcess(String style, List<BaseNode> baseNodeList) {
        baseNodeList.add(new SecondNode("工序", "数量", "单价"));
        SalaryDBHelper dbHelper = new SalaryDBHelper(getActivity(), dbName, null, 1);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("process",
                new String[]{"style", "process_id", "process_price", "number"}, "style=?",
                new String[]{style}, null, null, "process_id");
        while (cursor.moveToNext()) {
            baseNodeList.add(new SecondNode(cursor.getInt(cursor.getColumnIndex("process_id")) + "",
                    cursor.getInt(cursor.getColumnIndex("number")) + "",
                    cursor.getInt(cursor.getColumnIndex("process_price")) + ""));
        }
        cursor.close();
        database.close();
    }
}