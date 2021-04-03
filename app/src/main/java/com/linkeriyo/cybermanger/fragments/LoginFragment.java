package com.linkeriyo.cybermanger.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.requests.UserRequests;

import static com.linkeriyo.cybermanger.utilities.Functions.stringIsNullOrEmpty;

public class LoginFragment extends Fragment {

    Button bt_login;
    TextInputEditText et_username, et_password;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        bt_login = activity.findViewById(R.id.bt_login);
        et_username = activity.findViewById(R.id.et_username);
        et_password = activity.findViewById(R.id.et_password);

        bt_login.setOnClickListener(v -> login());
    }

    private void login() {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        if (!stringIsNullOrEmpty(username) && !stringIsNullOrEmpty(password)) {
            UserRequests.login(activity, username, password);
        } else if (!stringIsNullOrEmpty(username)) {
            Toast.makeText(activity, R.string.password_empty, Toast.LENGTH_SHORT).show();
        } else if (!stringIsNullOrEmpty(password)) {
            Toast.makeText(activity, R.string.username_empty, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, R.string.user_or_password_empty, Toast.LENGTH_SHORT).show();
        }
    }
}