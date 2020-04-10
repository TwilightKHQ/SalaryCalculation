package com.twilightkhq.salarycalculation.InformationList;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.twilightkhq.salarycalculation.databinding.FragmentEmployeeBinding;

public class FragmentEmployee extends Fragment {

    private FragmentEmployeeBinding binding;

    private String[] data = {"Sunny","Cloudy","Few Clouds","Partly Cloudy","Overcast","Windy","Calm","Light Breeze",
            "Moderate","Fresh Breeze","Dense fog","Strong fog","Moderate haze","Heavy haze","Severe haze","Heavy fog","Extra heavy fog",
            "Hot","Cold","Unknown"};

    public FragmentEmployee() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeBinding.inflate(inflater, container, false);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, data);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) adapter.getItem(position);
                Toast.makeText(getActivity(), item, Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}