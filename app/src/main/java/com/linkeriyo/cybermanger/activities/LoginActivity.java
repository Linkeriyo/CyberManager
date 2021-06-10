package com.linkeriyo.cybermanger.activities;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.linkeriyo.cybermanger.R;

/**
 * Activity used when the token in {@link android.content.SharedPreferences} is null. Logs
 * the user in or signs up.
 * @see com.linkeriyo.cybermanger.fragments.login.LoginFragment
 * @see com.linkeriyo.cybermanger.fragments.login.SignUpFragment
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}