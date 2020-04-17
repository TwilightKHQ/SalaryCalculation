package com.twilightkhq.salarycalculation.Provider;

import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.twilightkhq.salarycalculation.Adapter.AdapterStyleNodeTree;
import com.twilightkhq.salarycalculation.Entity.Node.EmployeeNode;
import com.twilightkhq.salarycalculation.Entity.Node.TitleNode;
import com.twilightkhq.salarycalculation.R;

import java.util.List;

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
}
