package com.twilightkhq.salarycalculation.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class AdapterFragment extends FragmentPagerAdapter {

    private Context context;
    private List<String> titleList;
    private List<Fragment> fragmentList;

    public AdapterFragment(@NonNull FragmentManager fm, Context context,
                           List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
