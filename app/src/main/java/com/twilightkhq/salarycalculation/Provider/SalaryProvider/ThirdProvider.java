package com.twilightkhq.salarycalculation.Provider.SalaryProvider;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.twilightkhq.salarycalculation.Entity.Node.ProcessNode;
import com.twilightkhq.salarycalculation.R;

public class ThirdProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId()  {
        return R.layout.item_node_three_col;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, BaseNode baseNode) {
        ProcessNode entity = (ProcessNode) baseNode;
        baseViewHolder.setText(R.id.tv_str1, entity.getStr1());
        baseViewHolder.setText(R.id.tv_str2, entity.getStr2());
        baseViewHolder.setText(R.id.tv_str3, entity.getStr3());
    }


}
