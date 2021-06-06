package com.linkeriyo.cybermanger.fragments.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.activities.LoginActivity;
import com.linkeriyo.cybermanger.databinding.FragmentLoginBinding;
import com.linkeriyo.cybermanger.requests.UserRequests;

import static com.linkeriyo.cybermanger.utilities.Functions.stringIsNullOrEmpty;

public class LoginFragment extends Fragment {

    LoginActivity loginActivity;
    FragmentLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = (LoginActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        binding.btLogin.setOnClickListener(v -> login());

        binding.tvNotAMember2.setOnClickListener(v -> {
            NavDirections action = LoginFragmentDirections.actionMainLoginFragmentToRegisterFragment();
            Navigation.findNavController(v).navigate(action);
        });
    }

    private void login() {
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (!stringIsNullOrEmpty(username) && !stringIsNullOrEmpty(password)) {
            UserRequests.login(loginActivity, username, password);
        } else if (!stringIsNullOrEmpty(username)) {
            Toast.makeText(loginActivity, R.string.password_empty, Toast.LENGTH_SHORT).show();
        } else if (!stringIsNullOrEmpty(password)) {
            Toast.makeText(loginActivity, R.string.username_empty, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(loginActivity, R.string.username_or_password_empty, Toast.LENGTH_SHORT).show();
        }
    }
}