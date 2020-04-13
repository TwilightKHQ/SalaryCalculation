package com.twilightkhq.salarycalculation.Adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class AdapterArray<T> extends ArrayAdapter {


    public AdapterArray(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        return super.getCount() > 0 ? super.getCount() - 1 : super.getCount();
    }
}
