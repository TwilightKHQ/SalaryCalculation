package com.twilightkhq.salarycalculation.InformationList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Adapter.AdapterNodeTree;
import com.twilightkhq.salarycalculation.Entity.FirstNode;
import com.twilightkhq.salarycalculation.Entity.SecondNode;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentStyle extends Fragment {

    private AdapterNodeTree adapter = new AdapterNodeTree();

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

        adapter.setList(getEntity());

        return view;
    }

    private List<BaseNode> getEntity() {
        List<BaseNode> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {

            List<BaseNode> secondNodeList = new ArrayList<>();
            for (int n = 0; n <= 5; n++) {
                SecondNode seNode = new SecondNode( "Second Node " + n);
                secondNodeList.add(seNode);
            }

            FirstNode entity = new FirstNode(secondNodeList, "First Node " + i);

            // 模拟 默认第0个是展开的
            entity.setExpanded(i == 0);

            list.add(entity);
        }
        return list;
    }
}