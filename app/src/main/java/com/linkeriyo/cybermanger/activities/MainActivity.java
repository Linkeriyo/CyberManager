package com.linkeriyo.cybermanger.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.linkeriyo.cybermanger.databinding.ActivityMainBinding;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

public class MainActivity extends AppCompatActivity {

    boolean userLoggingIn = false;
    boolean userFillingExtraData = false;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
                case Tags.RQ_EXTRA_DATA:
                    userFillingExtraData = false;
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
            startLoginActivity();
        } else {
            checkUser();
        }
    }

    private void checkUser() {
        UserRequests.checkUserExtraData(this, Preferences.getToken(), Preferences.getUsername());
    }

    public void initLayout() {
        setContentView(binding.getRoot());

        binding.tvLogout.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
            UserRequests.logout(this, Preferences.getToken());
        });
    }

    public void startLoginActivity() {
        startActivityForResult(new Intent(this, LoginActivity.class), Tags.RQ_LOGIN);
    }

    public void startExtraDataActivity() {
        startActivityForResult(new Intent(this, ExtraDataActivity.class), Tags.RQ_EXTRA_DATA);
    }
}