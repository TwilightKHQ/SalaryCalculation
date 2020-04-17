package com.twilightkhq.salarycalculation.Entity.Node;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

public class EmployeeNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private String title;

    public EmployeeNode(List<BaseNode> childNode, String title) {
        this.childNode = childNode;
        this.title = title;

        setExpanded(false);
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }

    public int getChildNodeSize() {
        return childNode.size();
    }
}
