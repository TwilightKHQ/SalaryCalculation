package com.twilightkhq.salarycalculation.Entity.SalaryNode;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

public class ThirdNode extends BaseExpandNode {

    private String str1;
    private String str2;
    private String str3;

    public ThirdNode(String str1, String str2, String str3) {
        this.str1 = str1;
        this.str2 = str2;
        this.str3 = str3;
    }

    public String getStr1() {
        return str1;
    }

    public String getStr2() {
        return str2;
    }

    public String getStr3() {
        return str3;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
