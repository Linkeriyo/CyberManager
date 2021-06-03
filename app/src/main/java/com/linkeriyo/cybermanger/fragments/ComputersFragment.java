package com.linkeriyo.cybermanger.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.adapters.ComputersAdapter;
import com.linkeriyo.cybermanger.databinding.FragmentComputersBinding;
import com.linkeriyo.cybermanger.models.Computer;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.viewmodels.ComputerModel;

import java.util.ArrayList;

public class ComputersFragment extends Fragment {

    MainActivity activity;
    FragmentComputersBinding binding;
    CyberCafe selectedCafe;
    ArrayList<Computer> computers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        selectedCafe = activity.getSelectedCafe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComputersBinding.inflate(inflater, container, false);

        binding.toolbar.setSubtitle(selectedCafe.getName());

        binding.tvBalance.setText(getString(R.string.balance, selectedCafe.getBalance()));

        binding.rvComputers.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvComputers.setAdapter(new ComputersAdapter(computers));

        return binding.getRoot();
    }
}