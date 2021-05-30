package com.linkeriyo.cybermanger.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.databinding.DialogCheckQrBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.BusinessRequests;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

public class QRScannedDialog extends Dialog {

    DialogCheckQrBinding binding;
    String qrValue;
    CyberCafe selectedCafe;

    public QRScannedDialog(@NonNull Context context, String qrValue) {
        super(context);
        this.qrValue = qrValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        BusinessRequests.checkBusiness(this, qrValue);
    }

    private void initLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogCheckQrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btAccept.setOnClickListener(v -> {
            System.out.println("dialog accepted");

            if (selectedCafe != null) {
                UserRequests.addCybercafeToUser(this, Preferences.getToken(), selectedCafe);
            }
        });

        binding.btCancel.setOnClickListener(v -> {
            System.out.println("dialog canceled");
            dismiss();
        });
    }

    public void showCyberCafeIfMatched(CyberCafe cyberCafe) {
        binding.progressBar.setVisibility(View.GONE);

        if (cyberCafe == null) {
            binding.elementNoMatch.getRoot().setVisibility(View.VISIBLE);
        } else {
            selectedCafe = cyberCafe;

            int nightModeFlags = getContext().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                Glide.with(getContext()).
                        load(cyberCafe.getImage()).
                        centerCrop().
                        placeholder(R.drawable.ic_action_name_night).
                        into(binding.elementCybercafe.civImage);
            } else {
                Glide.with(getContext()).
                        load(cyberCafe.getImage()).
                        centerCrop().
                        placeholder(R.drawable.ic_action_name).
                        into(binding.elementCybercafe.civImage);
            }

            binding.elementCybercafe.tvName.setText(cyberCafe.getName());
            binding.elementCybercafe.getRoot().setVisibility(View.VISIBLE);
            binding.btAccept.setEnabled(true);
            binding.btAccept.setTextColor(getContext().getColor(R.color.colorTextPure));
        }
    }
}
