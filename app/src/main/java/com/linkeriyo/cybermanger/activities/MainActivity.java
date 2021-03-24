package com.linkeriyo.cybermanger.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

public class MainActivity extends AppCompatActivity {

    boolean userLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkToken();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Tags.RS_OK) {
            switch (requestCode) {
                case Tags.RQ_LOGIN:
                    userLoggedIn = true;
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkToken();
    }

    private void checkToken() {
        if (Preferences.getToken() == null) {
            startActivityForResult(new Intent(this, LoginActivity.class), Tags.RQ_LOGIN);
            userLoggedIn = false;
        } else {
            userLoggedIn = true;
        }
    }
}