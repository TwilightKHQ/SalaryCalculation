package com.twilightkhq.salarycalculation.View;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.List;

public class DialogBottom extends DialogFragment {

    private TextView textCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_bottom);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部
        final Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
//        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() / 3;
        window.setAttributes(lp);

//        initView(dialog);
        // 窗口初始化后 请求网络数据
        return dialog;
    }

//    private void initView(Dialog dialog) {
//
//        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.left_recycle_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(AppUtil.getContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
//        AdapterMenuItem adapterMenuItem = new AdapterMenuItem(R.layout.item_menu, menuItemList);
//        recyclerView.setAdapter(adapterMenuItem);
//
//        textCancel = (TextView) dialog.findViewById(R.id.cancel);
//
//        setListener();
//
//    }

    private void setListener() {
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}