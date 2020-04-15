package com.twilightkhq.salarycalculation.InformationList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.EntityStyle;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.FirstNode;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.SecondNode;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentStyle extends Fragment {

    private static List<FirstNode> nodeList = new ArrayList<>();
    private Adapter2FloorNodeTree adapter = new Adapter2FloorNodeTree();

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
//        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//                Log.d("zzq", "onItemLongClick: position = " + position);
//                DialogBottom dialogBottom = new DialogBottom();
//                if (adapter.getItemViewType(position) == 1) {
//                    TextView textView = (TextView) view.findViewById(R.id.title);
//                    dialogBottom.setInformation("style", textView.getText().toString());
//                } else if (adapter.getItemViewType(position) == 2) {
//                    TextView textView = (TextView) view.findViewById(R.id.title);
//                    TextView tvStr = (TextView) view.findViewById(R.id.tv_str1);
//                    Log.d("zzq", "onItemLongClick: string = " + nodeList.get(0).getTitle());
//                    dialogBottom.setInformation("style", tvStr.getText().toString());
//                }
//                dialogBottom.show(getActivity().getSupportFragmentManager(), "dialogBottom");
//                return false;
//            }
//        });
//        adapter.setOnItemChildLongClickListener(new OnItemChildLongClickListener() {
//            @Override
//            public boolean onItemChildLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//
//                return false;
//            }
//        });

        return view;
    }

    private void initData() {
        List<EntityStyle> styleList = SalaryDao.getInstance(getActivity()).getStyleList();
        List<EntityProcess> processList = SalaryDao.getInstance(getActivity()).getProcessList();
        nodeList.clear();
        for (EntityStyle entityStyle : styleList) {
            List<BaseNode> secondNodeList = new ArrayList<>();
            secondNodeList.add(new SecondNode("工序数量", "件数", "款式单价"));
            secondNodeList.add(new SecondNode(entityStyle.getProcessNumber() + "",
                    entityStyle.getNumber() + "", entityStyle.getStylePrice() + ""));
            secondNodeList.add(new SecondNode("工序", "数量", "单价"));
            for (EntityProcess entityProcess : processList) {
                if (entityProcess.getStyle().equals(entityStyle.getStyle())) {
                    secondNodeList.add(new SecondNode(entityProcess.getProcessID() + "",
                            entityProcess.getNumber() + "", entityProcess.getProcessPrice() + ""));
                }
            }
            nodeList.add(new FirstNode(secondNodeList, entityStyle.getStyle()));
        }
        Collections.sort(nodeList, new Comparator<FirstNode>() {
            @Override
            public int compare(FirstNode o1, FirstNode o2) {
                return o2.getTitle().compareTo(o1.getTitle());
            }
        });
    }
}