package com.linkeriyo.cybermanger.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.adapters.HomeFragmentsAdapter;
import com.linkeriyo.cybermanger.databinding.ActivityMainBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.BusinessRequests;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;
import com.linkeriyo.cybermanger.utilities.Tags;

public class MainActivity extends AppCompatActivity {

    boolean userLoggingIn = false;
    boolean userFillingExtraData = false;
    boolean userSeletingCafe = false;
    boolean layoutInitialized = false;
    boolean userAddingBalance = false;

    ActivityMainBinding binding;

    CyberCafe selectedCafe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // The user comes from LoginActivity, if it is still not logged in, finish activity.
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

            // The user comes from SelectCafeActivity, could have decided to change cafe or logout.
            case Tags.RQ_SELECT_CAFE:
                userSeletingCafe = false;
                switch (resultCode) {
                    case Tags.RS_CAFE_CHANGED:
                        finish();
                        startActivity(getIntent());
                        break;
                    case Tags.RS_LOGOUT:
                        logout();
                        break;
                    case Tags.RS_ERROR:
                        finish();
                }
                break;
            case Tags.RQ_ADD_BALANCE:
                userAddingBalance = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If the user comes from logging in check their UserExtraData
        if (!userLoggingIn) {
            checkToken();
        } else {
            checkUser();
        }
    }

    /**
     * Checks if the token is registered in {@link android.content.SharedPreferences}
     */
    private void checkToken() {
        if (Preferences.getToken() == null) {
            startLoginActivity();
        } else {
            checkUser();
        }
    }

    /**
     * Checks if the user has UserExtraData.
     */
    private void checkUser() {
        UserRequests.checkUserExtraData(this, Preferences.getToken(), Preferences.getUsername());
    }

    /**
     * Checks if there is a selected cafe.
     */
    public void checkSelectedCafe() {
        String selectedCafeId = Preferences.getSelectedCafe();

        if (selectedCafeId == null) {
            startSelectCafeActivity();
        } else {
            BusinessRequests.getBusinessIntoMainActivity(this, Preferences.getToken(), selectedCafeId);
        }
    }

    /**
     * Initializes needed views.
     */
    public void initLayout() {
        if (!layoutInitialized) {
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            binding.bottomNavView.setSelectedItemId(R.id.item_home);
            binding.bottomNavView.setOnNavigationItemSelectedListener(item -> {
                if (item.getItemId() == R.id.item_computers) {
                    binding.viewPager.setCurrentItem(1);
                } else if (item.getItemId() == R.id.item_home) {
                    binding.viewPager.setCurrentItem(0);
                }
                return true;
            });
            binding.viewPager.setAdapter(new HomeFragmentsAdapter(this));
            binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    if (layoutInitialized) {
                        switch (position) {
                            case 0:
                                binding.bottomNavView.setSelectedItemId(R.id.item_home);
                                break;
                            case 1:
                                binding.bottomNavView.setSelectedItemId(R.id.item_computers);
                                break;
                        }
                    }
                }
            });
            layoutInitialized = true;
        }
    }

    public void startLoginActivity() {
        startActivityForResult(new Intent(this, LoginActivity.class), Tags.RQ_LOGIN);
        userLoggingIn = true;
    }

    public void startExtraDataActivity() {
        startActivityForResult(new Intent(this, ExtraDataActivity.class), Tags.RQ_EXTRA_DATA);
        userFillingExtraData = true;
    }

    public void startSelectCafeActivity() {
        startActivityForResult(new Intent(this, SelectCafeActivity.class), Tags.RQ_SELECT_CAFE);
        userSeletingCafe = true;
    }

    public void startAddBalanceActivity() {
        startActivityForResult(new Intent(this, AddBalanceActivity.class), Tags.RQ_ADD_BALANCE);
        userAddingBalance = true;
    }

    public void logout() {
        UserRequests.logout(this, Preferences.getToken());
    }

    public void setSelectedCafe(CyberCafe selectedCafe) {
        this.selectedCafe = selectedCafe;
    }

    public CyberCafe getSelectedCafe() {
        return selectedCafe;
    }
}