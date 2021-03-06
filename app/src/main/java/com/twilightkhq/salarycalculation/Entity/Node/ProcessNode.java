package com.twilightkhq.salarycalculation.Entity.Node;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

public class ProcessNode extends BaseExpandNode {

    private String processID;
    private String number;
    private String price;

    public ProcessNode(String processID, String number, String price) {
        this.processID = processID;
        this.number = number;
        this.price = price;
    }

    public String getProcessID() {
        return processID;
    }

    public String getNumber() {
        return number;
    }

    public String getPrice() {
        return price;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }
}
