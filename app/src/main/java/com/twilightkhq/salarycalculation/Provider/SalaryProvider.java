package com.twilightkhq.salarycalculation.Provider;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.twilightkhq.salarycalculation.Adapter.AdapterSalaryNodeTree;
import com.twilightkhq.salarycalculation.Activity.AddInformation.AddInformationActivity;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityCircuit;
import com.twilightkhq.salarycalculation.Entity.Node.EmployeeNode;
import com.twilightkhq.salarycalculation.Entity.Node.SalaryNode;
import com.twilightkhq.salarycalculation.Entity.Node.TitleNode;
import com.twilightkhq.salarycalculation.R;
import com.twilightkhq.salarycalculation.Utils.SomeUtils;

public class SalaryProvider extends BaseNodeProvider {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--" + SalaryProvider.class.getSimpleName();

    @Override
    public int getItemViewType() {
        return 3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_four_col;
    }

    @Override
    public void convert(BaseViewHolder baseViewHolder, BaseNode baseNode) {
        SalaryNode entity = (SalaryNode) baseNode;
        baseViewHolder.setText(R.id.tv_str1, entity.getStyle());
        baseViewHolder.setText(R.id.tv_str2, entity.getProcessID());
        baseViewHolder.setText(R.id.tv_str3, entity.getNumber());
        baseViewHolder.setText(R.id.tv_str4, SomeUtils.priceToShow(entity.getPrice()));
    }

    @Override
    public boolean onLongClick(@NonNull BaseViewHolder helper, @NonNull View view,
                               BaseNode data, int position) {
        String name = "";
        int nodeSize = 0;

        SalaryNode salaryNode = (SalaryNode) data;
        AdapterSalaryNodeTree adapter = (AdapterSalaryNodeTree) getAdapter();
        if (adapter != null) {
            int parentPosition = adapter.findParentNode(data);
            BaseNode parentNode = (BaseNode) adapter.getData().get(parentPosition);
            if (parentNode instanceof TitleNode) {
                Log.d(TAG, "onLongClick: TitleNode");
                TitleNode titleNode = (TitleNode) parentNode;
                name = titleNode.getTitle();
                nodeSize = titleNode.getChildNodeSize();
            } else if (parentNode instanceof EmployeeNode) {
                Log.d(TAG, "onLongClick: EmployeeNode");
                EmployeeNode employeeNode = (EmployeeNode) parentNode;
                name = employeeNode.getTitle();
                nodeSize = employeeNode.getChildNodeSize();
            }
            if (position - parentPosition <= 1
                    || position - parentPosition == nodeSize)
                return super.onLongClick(helper, view, data, position);

            String finalName = name;
            new XPopup.Builder(getContext())
                    .asBottomList("请选择一项", new String[]{"修改", "删除"},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int p, String text) {
                                    if (p == 0) {
                                        Intent intent = new Intent(getContext(), AddInformationActivity.class);
                                        intent.putExtra("type", 3);
                                        intent.putExtra("name", finalName);
                                        intent.putExtra("style", salaryNode.getStyle());
                                        intent.putExtra("processID", salaryNode.getProcessID());
                                        getContext().startActivity(intent);
                                    }
                                    if (p == 1) {
                                        adapter.nodeRemoveData(parentNode, position - parentPosition - 1);
                                        adapter.notifyDataSetChanged();
                                        SalaryDao.getInstance(getContext()).deleteCircuit(new EntityCircuit(
                                                finalName, salaryNode.getStyle(),
                                                Integer.parseInt(salaryNode.getProcessID()),
                                                0));
                                    }
                                }
                            })
                    .show();
        } else Log.d(TAG, "onLongClick: adapter is Null!");

        return super.onLongClick(helper, view, data, position);
    }
}
