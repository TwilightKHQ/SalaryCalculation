package com.twilightkhq.salarycalculation.Adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.FirstNode;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.SecondNode;
import com.twilightkhq.salarycalculation.Provider.ThreeColProvider.FirstProvider;
import com.twilightkhq.salarycalculation.Provider.ThreeColProvider.SecondProvider;

import java.util.List;

public class Adapter2FloorNodeTree extends BaseNodeAdapter {

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

    public Adapter2FloorNodeTree() {
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
