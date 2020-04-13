package com.twilightkhq.salarycalculation.Provider.ThreeColProvider;

import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.twilightkhq.salarycalculation.Adapter.Adapter2FloorNodeTree;
import com.twilightkhq.salarycalculation.Entity.ThreeColNode.FirstNode;
import com.twilightkhq.salarycalculation.R;

import java.util.List;

public class FirstProvider extends BaseNodeProvider {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug--";

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_node_title;
    }


    @Override
    public void convert(BaseViewHolder baseViewHolder, BaseNode baseNode) {
        FirstNode entity = (FirstNode) baseNode;
        baseViewHolder.setText(R.id.title, entity.getTitle());
        baseViewHolder.setImageResource(R.id.iv, R.mipmap.arrow_r);

        setArrowSpin(baseViewHolder, baseNode, false);
    }

    @Override
    public void convert(@NonNull BaseViewHolder helper, BaseNode item, List<?> payloads) {
        for (Object payload : payloads) {
            if (payload instanceof Integer && (int) payload == Adapter2FloorNodeTree.EXPAND_COLLAPSE_PAYLOAD) {
                // 增量刷新，使用动画变化箭头
                setArrowSpin(helper, item, true);
            }
        }
    }

    @Override
    public void onClick(@NonNull BaseViewHolder helper, @NonNull View view, BaseNode data, int position) {
        // 这里使用payload进行增量刷新（避免整个item刷新导致的闪烁，不自然）
        getAdapter().expandOrCollapse(position, true, true, Adapter2FloorNodeTree.EXPAND_COLLAPSE_PAYLOAD);
    }

    @Override
    public boolean onLongClick(BaseViewHolder helper, View view, BaseNode data, int position) {
        Log.d("zzq", "onLongClick: 长按 " + position);
        return super.onLongClick(helper, view, data, position);
    }

    private void setArrowSpin(BaseViewHolder helper, BaseNode data, boolean isAnimate) {
        FirstNode entity = (FirstNode) data;

        ImageView imageView = helper.getView(R.id.iv);

        if (entity.isExpanded()) {
            if (isAnimate) {
                ViewCompat.animate(imageView).setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .rotation(0f)
                        .start();
            } else {
                imageView.setRotation(0f);
            }
        } else {
            if (isAnimate) {
                ViewCompat.animate(imageView).setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .rotation(90f)
                        .start();
            } else {
                imageView.setRotation(90f);
            }
        }
    }
}
