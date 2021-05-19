package com.linkeriyo.cybermanger.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.linkeriyo.cybermanger.databinding.ActivityScanQrBinding;

public class ScanQRActivity extends Activity {

    ActivityScanQrBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    public void initLayout() {
        binding = ActivityScanQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
