package com.twilightkhq.salarycalculation.Adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Entity.FirstNode;
import com.twilightkhq.salarycalculation.Entity.SecondNode;

import java.util.List;

public class AdapterNodeTree extends BaseNodeAdapter {

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

    public AdapterNodeTree() {
        super();
        addNodeProvider(new FirstProvider());
        addNodeProvider(new SecondProvider());
    }

    @Override
    protected int getItemType(List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof FirstNode) {
            return 1;
        } else if (node instanceof SecondNode) {
            return 2;
        }
        return -1;
    }
}
