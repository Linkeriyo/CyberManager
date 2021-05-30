package com.linkeriyo.cybermanger.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.databinding.ActivitySelectCafeBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class SelectCafeActivity extends Activity {

    private static final String TAG = "SelectCafeActivity";
    ActivitySelectCafeBinding binding;
    ArrayList<CyberCafe> cafes = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        binding = ActivitySelectCafeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> {
            setResult(Tags.RS_ERROR);
            finish();
        });

        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.item_add_cafe) {

                if (ContextCompat.checkSelfPermission(SelectCafeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, Tags.RQ_CAM_PERMISSION);
                } else {
                    startActivityForResult(new Intent(SelectCafeActivity.this, ScanQRActivity.class), Tags.RQ_SCAN_QR);
                }

            } else if (item.getItemId() == R.id.item_logout) {
                setResult(Tags.RS_LOGOUT);
                finish();
            }
            return true;
        });

        refreshTextView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Tags.RQ_SCAN_QR) {
            Log.v(TAG, "code scanned");
        } else if (requestCode == Tags.RQ_CAM_PERMISSION) {
            System.out.println(resultCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println(requestCode + "\n" + Arrays.toString(permissions) + "\n" + Arrays.toString(grantResults));
        if (requestCode == Tags.RQ_CAM_PERMISSION) {
            if (grantResults[0] == 0) {
                startActivityForResult(new Intent(SelectCafeActivity.this, ScanQRActivity.class), Tags.RQ_SCAN_QR);
            }
        }
    }

    private void refreshTextView() {
        if (cafes.isEmpty()) {
            binding.tvNoCafes.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoCafes.setVisibility(View.INVISIBLE);
        }
    }
}
