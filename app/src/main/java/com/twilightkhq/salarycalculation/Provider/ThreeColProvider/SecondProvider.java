package com.twilightkhq.salarycalculation.Provider.ThreeColProvider;

import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.SecondNode;
import com.twilightkhq.salarycalculation.R;

public class SecondProvider extends BaseNodeProvider {
    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_three_col;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, BaseNode baseNode) {
        SecondNode entity = (SecondNode) baseNode;
        baseViewHolder.setText(R.id.tv_str1, entity.getStr1());
        baseViewHolder.setText(R.id.tv_str2, entity.getStr2());
        baseViewHolder.setText(R.id.tv_str3, entity.getStr3());
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, View view, BaseNode data, int position) {
        new XPopup.Builder(getContext())
                .asBottomList("请选择一项", new String[]{"条目1", "条目2"},
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                            }
                        })
                .show();
        return super.onLongClick(helper, view, data, position);
    }
}
