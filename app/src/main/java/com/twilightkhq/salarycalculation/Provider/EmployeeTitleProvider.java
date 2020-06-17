package com.twilightkhq.salarycalculation.Provider;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.twilightkhq.salarycalculation.Activity.AddInformation.AddInformationActivity;
import com.twilightkhq.salarycalculation.Adapter.AdapterSalaryNodeTree;
import com.twilightkhq.salarycalculation.Datebase.SalaryDao;
import com.twilightkhq.salarycalculation.Entity.EntityEmployee;
import com.twilightkhq.salarycalculation.Entity.Node.EmployeeNode;
import com.twilightkhq.salarycalculation.R;

public class EmployeeTitleProvider extends BaseNodeProvider {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug--";

    @Override
    public int getItemViewType() {
        return 4;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_employee;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseNode data) {
        EmployeeNode entity = (EmployeeNode) data;
        helper.setText(R.id.title, entity.getTitle());

        if (entity.isExpanded()) {
            helper.setImageResource(R.id.iv, R.mipmap.arrow_b);
        } else {
            helper.setImageResource(R.id.iv, R.mipmap.arrow_r);
        }
    }

    @Override
    public void onClick(@NonNull BaseViewHolder helper, @NonNull View view, BaseNode data, int position) {
        EmployeeNode entity = (EmployeeNode) data;
        if (entity.isExpanded()) {
            getAdapter().collapse(position);
        } else {
            getAdapter().expandAndCollapseOther(position);
        }
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, View view, BaseNode data, int position) {
        EmployeeNode employeeNode = (EmployeeNode) data;
        AdapterSalaryNodeTree adapter = (AdapterSalaryNodeTree) getAdapter();
        if (adapter != null) {
            new XPopup.Builder(getContext())
                    .asBottomList("请选择一项", new String[]{"修改", "删除"},
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int p, String text) {
                                    if (p == 0) {
                                        Intent intent = new Intent(getContext(), AddInformationActivity.class);
                                        intent.putExtra("type", 0);
                                        intent.putExtra("name", employeeNode.getTitle());
                                        getContext().startActivity(intent);
                                    }
                                    if (p == 1) {
                                        SalaryDao.getInstance(getContext()).deleteEmployee(new EntityEmployee(
                                                employeeNode.getTitle()
                                        ));
                                        adapter.collapseAndChild(position);
                                        adapter.getData().remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            })
                    .show();
        }
        return super.onLongClick(helper, view, data, position);
    }
}
