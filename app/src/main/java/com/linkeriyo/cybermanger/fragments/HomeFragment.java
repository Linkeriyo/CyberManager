package com.linkeriyo.cybermanger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.databinding.FragmentHomeBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    CyberCafe selectedCafe;
    MainActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        selectedCafe = activity.getSelectedCafe();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getContext())
                .load(selectedCafe.getImage())
                .centerCrop()
                .into(binding.civCafeImage);
        binding.tvCafeDesc.setText(selectedCafe.getDescription());
        binding.tvWelcome.setText(getString(R.string.welcome_to_cafe, selectedCafe.getName()));
        binding.llLogout.setOnClickListener(v -> {
            activity.logout();
        });
        binding.llChangeCafe.setOnClickListener(v -> {
            activity.startSelectCafeActivity();
        });
    }
}
