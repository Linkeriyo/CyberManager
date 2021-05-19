package com.linkeriyo.cybermanger.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.databinding.ActivitySelectCafeBinding;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

public class SelectCafeActivity extends Activity {

    private static final String TAG = "SelectCafeActivity";
    ActivitySelectCafeBinding binding;

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
                startActivityForResult(new Intent(SelectCafeActivity.this, ScanQRActivity.class), Tags.RQ_SCAN_QR);
            } else if (item.getItemId() == R.id.item_logout) {
                setResult(Tags.RS_LOGOUT);
                finish();
            }
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Tags.RQ_SCAN_QR) {
            Log.v(TAG, "code scanned");
        }
    }
}
