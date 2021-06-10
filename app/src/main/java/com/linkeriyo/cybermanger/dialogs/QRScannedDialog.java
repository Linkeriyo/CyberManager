package com.linkeriyo.cybermanger.dialogs;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.activities.ScanQRActivity;
import com.linkeriyo.cybermanger.databinding.DialogCheckQrBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.BusinessRequests;
import com.linkeriyo.cybermanger.requests.UserRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

public class QRScannedDialog extends Dialog {

    DialogCheckQrBinding binding;
    String qrValue;
    CyberCafe selectedCafe;
    ScanQRActivity activity;

    public QRScannedDialog(ScanQRActivity activity, String qrValue) {
        super(activity);
        this.qrValue = qrValue;
        this.activity = activity;
    }

    /**
     * Similar to <code>Activity.onCreate()</code>, you should initialize your dialog
     * in this method, including calling {@link #setContentView}.
     * @param savedInstanceState If this dialog is being reinitialized after a
     *     the hosting activity was previously shut down, holds the result from
     *     the most recent call to {@link #onSaveInstanceState}, or null if this
     *     is the first time.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        BusinessRequests.checkBusiness(this, Preferences.getToken(), qrValue);
    }

    /**
     * Initializes needed views.
     */
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

    /**
     * Sets the selected cafe and shows it if it is valid.
     *
     * @param cyberCafe {@link CyberCafe} to show in the dialog.
     */
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

    /**
     * Dismisses the dialog notifying the instance of {@link ScanQRActivity} to scan for QR
     * over again.
     */
    @Override
    public void dismiss() {
        activity.checkAgain();
        super.dismiss();
    }
}
