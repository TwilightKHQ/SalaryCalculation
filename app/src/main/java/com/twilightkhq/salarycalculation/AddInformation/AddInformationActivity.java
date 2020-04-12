package com.twilightkhq.salarycalculation.AddInformation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.twilightkhq.salarycalculation.Adapter.AdapterFragment;
import com.twilightkhq.salarycalculation.InformationList.FragmentEmployee;
import com.twilightkhq.salarycalculation.InformationList.FragmentStyle;
import com.twilightkhq.salarycalculation.InformationList.InformationListActivity;
import com.twilightkhq.salarycalculation.MainActivity;
import com.twilightkhq.salarycalculation.R;

import java.util.ArrayList;
import java.util.List;

public class AddInformationActivity extends AppCompatActivity {

    private static boolean DEBUG = true;
    private static String TAG = "--zzq--debug";

    private static int currentViewPagePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initView();
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        Button btChange = (Button) findViewById(R.id.bt_change);

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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentViewPagePosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                currentViewPagePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DEBUG) {
                    Log.d(TAG, "onClick: currentViewPagePosition " + currentViewPagePosition);
                }
            }
        });
    }
}