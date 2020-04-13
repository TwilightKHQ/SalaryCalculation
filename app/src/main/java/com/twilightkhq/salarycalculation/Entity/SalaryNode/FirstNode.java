package com.twilightkhq.salarycalculation.Entity.SalaryNode;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

public class FirstNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private String style;

    public FirstNode(List<BaseNode> childNode, String style) {
        this.childNode = childNode;
        this.style = style;

        setExpanded(false);
    }

    public String getStyle() {
        return style;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
