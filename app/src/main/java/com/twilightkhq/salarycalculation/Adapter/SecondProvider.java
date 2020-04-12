package com.twilightkhq.salarycalculation.Adapter;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.twilightkhq.salarycalculation.Entity.SecondNode;
import com.twilightkhq.salarycalculation.R;

public class SecondProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId()  {
        return R.layout.item_node_second;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, BaseNode baseNode) {
        SecondNode entity = (SecondNode) baseNode;
        baseViewHolder.setText(R.id.title, entity.getTitle());
    }


}
