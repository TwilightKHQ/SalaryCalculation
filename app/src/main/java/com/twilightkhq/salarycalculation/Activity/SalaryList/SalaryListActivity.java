package com.twilightkhq.salarycalculation.Activity.SalaryList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.twilightkhq.salarycalculation.Adapter.AdapterFragment;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.List;

public class SalaryListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_list);

        initView();
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        fragmentList.add(new FragmentEmployeeSalary());
        fragmentList.add(new FragmentStyleSalary());
        titleList.add(getResources().getString(R.string.employee));
        titleList.add(getResources().getString(R.string.style));
        viewPager.setAdapter(new AdapterFragment(getSupportFragmentManager(),
                SalaryListActivity.this, fragmentList, titleList));
        tabLayout.setupWithViewPager(viewPager);
    }
}