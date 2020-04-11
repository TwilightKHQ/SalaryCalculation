package com.twilightkhq.salarycalculation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.twilightkhq.salarycalculation.Adapter.AdapterFragment;
import com.twilightkhq.salarycalculation.InformationList.FragmentEmployee;
import com.twilightkhq.salarycalculation.InformationList.FragmentStyle;

import java.util.ArrayList;
import java.util.List;

public class InformationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_list);

        initView();
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        fragmentList.add(new FragmentEmployee());
        fragmentList.add(new FragmentStyle());
        titleList.add(getResources().getString(R.string.employee));
        titleList.add(getResources().getString(R.string.style));
        viewPager.setAdapter(new AdapterFragment(getSupportFragmentManager(),
                InformationListActivity.this, fragmentList, titleList));
        tabLayout.setupWithViewPager(viewPager);
    }
}