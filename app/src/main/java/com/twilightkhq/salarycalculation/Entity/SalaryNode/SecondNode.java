package com.twilightkhq.salarycalculation.Entity.SalaryNode;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

public class SecondNode extends BaseExpandNode {

    private List<BaseNode> childNode;
    private String processID;

    public SecondNode(List<BaseNode> childNode, String processID) {
        this.childNode = childNode;
        this.processID = processID;

        setExpanded(false);
    }

    public String getProcessID() {
        return processID;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childNode;
    }
}
