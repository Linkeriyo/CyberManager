package com.linkeriyo.cybermanger.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.databinding.ActivityMainBinding;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

public class MainActivity extends AppCompatActivity {

    boolean userLoggingIn = false;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        checkToken();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Tags.RS_OK) {
            switch (requestCode) {
                case Tags.RQ_LOGIN:
                    if (Preferences.getToken() == null) {
                        finish();
                    } else {
                        userLoggingIn = false;
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!userLoggingIn) {
            checkToken();
        }
    }

    private void checkToken() {
        if (Preferences.getToken() == null) {
            userLoggingIn = true;
            startActivityForResult(new Intent(this, LoginActivity.class), Tags.RQ_LOGIN);
        } else {
            initLayout();
        }
    }

    private void initLayout() {
        setContentView(binding.getRoot());

        binding.tvLogout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
        });
    }
}