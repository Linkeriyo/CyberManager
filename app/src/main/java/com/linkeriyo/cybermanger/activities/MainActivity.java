package com.linkeriyo.cybermanger.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.linkeriyo.cybermanger.databinding.ActivityMainBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

public class MainActivity extends AppCompatActivity {

    boolean userLoggingIn = false;
    boolean userFillingExtraData = false;
    boolean userSeletingCafe = false;
    ActivityMainBinding binding;

    CyberCafe selectedCafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
            case Tags.RQ_SELECT_CAFE:
                userSeletingCafe = false;
                switch (resultCode) {
                    case Tags.RS_LOGOUT:
                        logout();
                        break;
                    case Tags.RS_ERROR:
                        finish();
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!userLoggingIn) {
            checkToken();
        } else {
            checkUser();
        }
    }

    private void checkToken() {
        if (Preferences.getToken() == null) {
            startLoginActivity();
        } else {
            checkUser();
        }
    }

    private void checkUser() {
        UserRequests.checkUserExtraData(this, Preferences.getToken(), Preferences.getUsername());
    }

    public void checkSelectedCafe() {
        String selectedCafeId = Preferences.getSelectedCafe();

        if (selectedCafeId == null) {
            startActivityForResult(new Intent(this, SelectCafeActivity.class), Tags.RQ_SELECT_CAFE);
            userSeletingCafe = true;
        } else {
            // pedir cibercafe según selectedCafeId
            initLayout();
        }
    }

    public void initLayout() {
        setContentView(binding.getRoot());
    }

    public void startLoginActivity() {
        startActivityForResult(new Intent(this, LoginActivity.class), Tags.RQ_LOGIN);
        userLoggingIn = true;
    }

    public void startExtraDataActivity() {
        startActivityForResult(new Intent(this, ExtraDataActivity.class), Tags.RQ_EXTRA_DATA);
        userFillingExtraData = true;
    }

    public void logout() {
        UserRequests.logout(this, Preferences.getToken());
    }
}