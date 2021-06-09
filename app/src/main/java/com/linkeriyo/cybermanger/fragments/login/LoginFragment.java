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

/**
 *  Fragment used by the user to login into the app with their credentials.
 */
public class LoginFragment extends Fragment {

    LoginActivity loginActivity;
    FragmentLoginBinding binding;

    /**
     * Just setups context for the activity.
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = (LoginActivity) getActivity();
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