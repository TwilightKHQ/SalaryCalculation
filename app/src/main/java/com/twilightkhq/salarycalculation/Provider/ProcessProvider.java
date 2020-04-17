package com.twilightkhq.salarycalculation.Provider;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.twilightkhq.salarycalculation.Adapter.AdapterStyleNodeTree;
import com.twilightkhq.salarycalculation.Activity.AddInformation.AddInformationActivity;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityProcess;
import com.twilightkhq.salarycalculation.Entity.Node.ProcessNode;
import com.twilightkhq.salarycalculation.Entity.Node.TitleNode;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

public class ProcessProvider extends BaseNodeProvider {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + ProcessProvider.class.getSimpleName();

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
        ProcessNode entity = (ProcessNode) baseNode;
        baseViewHolder.setText(R.id.tv_str1, entity.getProcessID());
        baseViewHolder.setText(R.id.tv_str2, entity.getNumber());
        baseViewHolder.setText(R.id.tv_str3, SomeUtils.priceToShow(entity.getPrice()));
    }

    @Override
    public boolean onLongClick(@NonNull BaseViewHolder helper, @NonNull View view,
                               BaseNode data, int position) {
        ProcessNode processNode = (ProcessNode) data;
        AdapterStyleNodeTree adapter = (AdapterStyleNodeTree) getAdapter();
        if (adapter != null) {
            int parentPosition = adapter.findParentNode(data);
            if (position - parentPosition <= 3)
                return super.onLongClick(helper, view, data, position);

            TitleNode parentNode = (TitleNode) adapter.getData().get(parentPosition);
            String style = parentNode.getTitle();

            new XPopup.Builder(getContext())
                    .asBottomList("请选择一项", new String[]{"修改", "删除"},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int p, String text) {
                                    if (p == 0) {
                                        Intent intent = new Intent(getContext(), AddInformationActivity.class);
                                        intent.putExtra("type", 2);
                                        intent.putExtra("style", style);
                                        intent.putExtra("processID", processNode.getProcessID());
                                        getContext().startActivity(intent);
                                    }
                                    if (p == 1) {
                                        adapter.nodeRemoveData(parentNode, position - parentPosition - 1);
                                        adapter.notifyDataSetChanged();
                                        SalaryDao.getInstance(getContext()).deleteProcess(new EntityProcess(
                                                style, Integer.parseInt(processNode.getProcessID()),
                                                0, 0));
                                    }
                                }
                            })
                    .show();
        }

        return super.onLongClick(helper, view, data, position);
    }
}
