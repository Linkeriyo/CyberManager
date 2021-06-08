package com.linkeriyo.cybermanger.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.databinding.DialogPaymentBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.PaymentsRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

public class WriteIdDialog extends Dialog {

    private static final String TAG = "PaymentDialog";
    private final Activity activity;
    DialogPaymentBinding binding;
    CyberCafe selectedCafe;

    public WriteIdDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DialogPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btCancel.setOnClickListener(v -> {
            System.out.println("dialog canceled");
            dismiss();
        });

        binding.btAccept.setOnClickListener(v -> {

        });

        binding.etCybergold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (binding.etCybergold.getText().length() >= 8) {
                        //TODO: comprobar negocio

                        binding.btAccept.setEnabled(true);
                        binding.btAccept.setTextColor(getContext().getColor(R.color.colorTextPure));
                    } else {
                        binding.btAccept.setEnabled(false);
                        binding.btAccept.setTextColor(getContext().getColor(R.color.colorTextTransparent));
                    }
                } catch (NumberFormatException e) {
                    Log.v(TAG, "Couldn't parse cybergold");
                    binding.btAccept.setEnabled(false);
                    binding.btAccept.setTextColor(getContext().getColor(R.color.colorTextTransparent));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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
