package com.twilightkhq.salarycalculation.AddInformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.twilightkhq.salarycalculation.Adapter.AdapterFragment;
import com.twilightkhq.salarycalculation.InformationList.FragmentEmployee;
import com.twilightkhq.salarycalculation.InformationList.FragmentStyle;
import com.twilightkhq.salarycalculation.InformationList.InformationListActivity;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.List;

public class AddInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initView();
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        fragmentList.add(new FragmentAddEmployee());
        fragmentList.add(new FragmentAddStyle());
        fragmentList.add(new FragmentAddProcess());
        fragmentList.add(new FragmentAddCircuit());
        titleList.add(getResources().getString(R.string.add_employee));
        titleList.add(getResources().getString(R.string.add_style));
        titleList.add(getResources().getString(R.string.add_process));
        titleList.add(getResources().getString(R.string.add_circuit));
        viewPager.setAdapter(new AdapterFragment(getSupportFragmentManager(),
                AddInformationActivity.this, fragmentList, titleList));
        tabLayout.setupWithViewPager(viewPager);
    }
}