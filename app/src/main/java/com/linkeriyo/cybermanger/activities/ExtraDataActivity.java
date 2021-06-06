package com.linkeriyo.cybermanger.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputFilter;

import androidx.annotation.Nullable;

import com.linkeriyo.cybermanger.databinding.ActivityExtraDataBinding;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

public class ExtraDataActivity extends Activity {

    ActivityExtraDataBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        binding = ActivityExtraDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InputFilter numberFilter = (source, start, end, dest, dstart, dend) -> {
            final String blockCharacterSet = "1234567890";

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return null;
            }
            return "";
        };

        binding.etPhono.setFilters(new InputFilter[]{numberFilter});
        binding.etZipCode.setFilters(new InputFilter[]{numberFilter});

        binding.btConfirm.setOnClickListener(v -> {
            UserRequests.setUserExtraData(
                    this,
                    Preferences.getToken(),
                    Preferences.getUsername(),
                    binding.etName.getText().toString(),
                    binding.etSurname.getText().toString(),
                    binding.etPhono.getText().toString(),
                    binding.etAddress.getText().toString(),
                    binding.etCity.getText().toString(),
                    binding.etProvince.getText().toString(),
                    binding.etZipCode.getText().toString());
        });
    }
}
