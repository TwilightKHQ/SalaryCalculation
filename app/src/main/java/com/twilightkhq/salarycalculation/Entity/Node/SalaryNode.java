package com.twilightkhq.salarycalculation.Entity.Node;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import java.util.List;

public class SalaryNode extends BaseExpandNode {

    private String style;
    private String processID;
    private String number;
    private String price;

    public SalaryNode(String style, String processID, String number, String price) {
        this.style = style;
        this.processID = processID;
        this.number = number;
        this.price = price;
    }

    public String getStyle() {
        return style;
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
