package com.twilightkhq.salarycalculation.Adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.twilightkhq.salarycalculation.Entity.Node.ProcessNode;
import com.twilightkhq.salarycalculation.Entity.Node.SalaryNode;
import com.twilightkhq.salarycalculation.Entity.Node.TitleNode;
import com.twilightkhq.salarycalculation.Provider.ProcessProvider;
import com.twilightkhq.salarycalculation.Provider.SalaryProvider;
import com.twilightkhq.salarycalculation.Provider.StyleTitleProvider;

import java.util.List;

public class AdapterSalaryNodeTree extends BaseNodeAdapter {

    public static final int EXPAND_COLLAPSE_PAYLOAD = 110;

    public AdapterSalaryNodeTree() {
        super();
        addNodeProvider(new StyleTitleProvider());
        addNodeProvider(new SalaryProvider());
    }

    @Override
    protected int getItemType(List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof TitleNode) {
            return 1;
        } else if (node instanceof SalaryNode) {
            return 3;
        }
        return -1;
    }
}
