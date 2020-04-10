package com.twilightkhq.salarycalculation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.twilightkhq.salarycalculation.Adapter.AdapterFragment;
import com.twilightkhq.salarycalculation.InformationList.FragmentEmployee;
import com.twilightkhq.salarycalculation.InformationList.FragmentStyle;
import com.twilightkhq.salarycalculation.databinding.ActivityInformationListBinding;

import java.util.ArrayList;
import java.util.List;

public class InformationListActivity extends AppCompatActivity {

    private ActivityInformationListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformationListBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        initLayout();
    }

    private void initLayout() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        fragmentList.add(new FragmentEmployee());
        fragmentList.add(new FragmentStyle());
        titleList.add(getResources().getString(R.string.employee));
        titleList.add(getResources().getString(R.string.style));
        binding.viewPager.setAdapter(new AdapterFragment(getSupportFragmentManager(),
                InformationListActivity.this,fragmentList,titleList));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }
}