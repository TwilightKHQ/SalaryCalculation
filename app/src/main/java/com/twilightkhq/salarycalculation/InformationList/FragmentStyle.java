package com.twilightkhq.salarycalculation.InformationList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Adapter.AdapterStyleNodeTree;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.Entity.Node.TitleNode;
import com.twilightkhq.salarycalculation.Entity.Node.ProcessNode;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentStyle extends Fragment {

    private static List<TitleNode> nodeList = new ArrayList<>();
    private AdapterStyleNodeTree adapter = new AdapterStyleNodeTree();

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

        initData();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.setList(nodeList);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        adapter.notifyDataSetChanged();
    }

    private void initData() {
        List<EntityStyle> styleList = SalaryDao.getInstance(getActivity()).getStyleList();
        List<EntityProcess> processList = SalaryDao.getInstance(getActivity()).getProcessList();
        nodeList.clear();
        for (EntityStyle entityStyle : styleList) {
            List<BaseNode> secondNodeList = new ArrayList<>();
            secondNodeList.add(new ProcessNode("工序数量", "件数", "款式单价"));
            secondNodeList.add(new ProcessNode(entityStyle.getProcessNumber() + "",
                    entityStyle.getNumber() + "", entityStyle.getStylePrice() + ""));
            secondNodeList.add(new ProcessNode("工序", "数量", "单价"));
            for (EntityProcess entityProcess : processList) {
                if (entityProcess.getStyle().equals(entityStyle.getStyle())) {
                    secondNodeList.add(new ProcessNode(entityProcess.getProcessID() + "",
                            entityProcess.getNumber() + "", entityProcess.getProcessPrice() + ""));
                }
            }
            nodeList.add(new TitleNode(secondNodeList, entityStyle.getStyle()));
        }
    }
}