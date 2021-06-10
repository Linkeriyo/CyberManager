package com.linkeriyo.cybermanger.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.adapters.CybercafesAdapter;
import com.linkeriyo.cybermanger.databinding.ActivitySelectCafeBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.BusinessRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * {@link Activity} used to select the {@link CyberCafe} to use for the rest of the app.
 */
public class SelectCafeActivity extends Activity {

    private static final String TAG = "SelectCafeActivity";
    ActivitySelectCafeBinding binding;
    ArrayList<CyberCafe> cafes = new ArrayList<>();
    CybercafesAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    /**
     * Initializes needed views.
     */
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

        adapter = new CybercafesAdapter(this, cafes);
        binding.rvCybercafes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCybercafes.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(() -> {
            BusinessRequests.getBusinessesByUser(this, Preferences.getToken());
        });
        refreshTextView();
    }

    /**
     * If permissions have been granted, launch {@link ScanQRActivity}
     * @param requestCode The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *     which is either {@link android.content.pm.PackageManager#PERMISSION_GRANTED}
     *     or {@link android.content.pm.PackageManager#PERMISSION_DENIED}. Never null.
     */
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

    /**
     * Refreshes the {@link TextView} showing that there is no items in the {@link RecyclerView}
     */
    private void refreshTextView() {
        if (cafes.isEmpty()) {
            binding.tvNoCafes.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoCafes.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Gets {@link CyberCafe}s for the user everytime the {@link Activity} is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        BusinessRequests.getBusinessesByUser(this, Preferences.getToken());
    }

    public void setCafes(ArrayList<CyberCafe> cafesList) {
        cafes.clear();
        cafes.addAll(cafesList);
        binding.rvCybercafes.getAdapter().notifyDataSetChanged();
        refreshTextView();
        binding.swipeRefresh.setRefreshing(false);
    }
}
