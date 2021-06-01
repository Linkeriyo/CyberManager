package com.linkeriyo.cybermanger.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.adapters.ComputersAdapter;
import com.linkeriyo.cybermanger.databinding.FragmentComputersBinding;
import com.linkeriyo.cybermanger.models.Computer;
import com.linkeriyo.cybermanger.viewmodels.ComputerModel;

import java.util.ArrayList;
import java.util.List;

public class ComputersFragment extends Fragment {

    MainActivity mainActivity;
    FragmentComputersBinding binding;
    RecyclerView recyclerView;
    ComputerModel viewModel;
    ArrayList<Computer> computers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ComputerModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentComputersBinding.inflate(inflater, container, false);

        recyclerView = binding.rvComputers;

        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
        recyclerView.setAdapter(new ComputersAdapter(computers));

        return binding.getRoot();
    }

    public ComputerModel getViewModel() {
        return viewModel;
    }
}