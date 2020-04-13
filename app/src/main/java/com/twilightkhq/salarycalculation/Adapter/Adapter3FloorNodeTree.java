package com.twilightkhq.salarycalculation.Adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Entity.SalaryNode.FirstNode;
import com.twilightkhq.salarycalculation.Entity.SalaryNode.SecondNode;
import com.twilightkhq.salarycalculation.Provider.SalaryProvider.FirstProvider;
import com.twilightkhq.salarycalculation.Provider.SalaryProvider.SecondProvider;

import java.util.List;

public class Adapter3FloorNodeTree extends BaseNodeAdapter {

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

    public Adapter3FloorNodeTree() {
        super();
        addNodeProvider(new FirstProvider());
        addNodeProvider(new SecondProvider());
        addNodeProvider(new SecondProvider());
    }

    @Override
    protected int getItemType(List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof FirstNode) {
            return 1;
        } else if (node instanceof SecondNode) {
            return 2;
        } else if (node instanceof SecondNode) {
            return 3;
        }
        return -1;
    }
}
