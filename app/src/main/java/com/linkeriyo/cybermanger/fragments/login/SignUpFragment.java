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
import com.linkeriyo.cybermanger.databinding.FragmentSignUpBinding;
import com.linkeriyo.cybermanger.requests.UserRequests;

import static com.linkeriyo.cybermanger.utilities.Functions.stringIsNullOrEmpty;

public class SignUpFragment extends Fragment {

    LoginActivity activity;
    FragmentSignUpBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (LoginActivity) getActivity();
    }

    /**
     * Initializes the views needed.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
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
        binding.btSignUp.setOnClickListener(v -> signUp());
        binding.tvNotAMember2.setOnClickListener(v -> navigateToLoginFragment());
    }

    private void signUp() {
        String username = binding.etUsername.getText().toString();
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        String passwordAgain = binding.etPasswordAgain.getText().toString();

        if (stringIsNullOrEmpty(username) && stringIsNullOrEmpty(password)) {
            Toast.makeText(activity, R.string.username_or_password_empty, Toast.LENGTH_SHORT).show();
        } else if (stringIsNullOrEmpty(username)) {
            Toast.makeText(activity, R.string.username_empty, Toast.LENGTH_SHORT).show();
        } else if (stringIsNullOrEmpty(password)) {
            Toast.makeText(activity, R.string.password_empty, Toast.LENGTH_SHORT).show();
        } else if (stringIsNullOrEmpty(passwordAgain)) {
            Toast.makeText(activity, R.string.passwordagain_empty, Toast.LENGTH_SHORT).show();
        } else if (stringIsNullOrEmpty(email)) {
            Toast.makeText(activity, R.string.email_empty, Toast.LENGTH_SHORT).show();
        } else if (!password.equals(passwordAgain)) {
            Toast.makeText(activity, R.string.passwords_dont_match, Toast.LENGTH_SHORT).show();
        } else {
            UserRequests.signup(this, username, email, password);
        }
    }

    public void navigateToLoginFragment() {
        NavDirections action = SignUpFragmentDirections.actionRegisterFragmentToMainLoginFragment2();
        Navigation.findNavController(binding.getRoot()).navigate(action);
    }
}