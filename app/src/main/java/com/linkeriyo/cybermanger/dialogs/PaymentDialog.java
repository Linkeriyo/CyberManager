package com.linkeriyo.cybermanger.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;

import androidx.annotation.NonNull;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.databinding.DialogGenerateQrBinding;
import com.linkeriyo.cybermanger.databinding.DialogPaymentBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.utilities.Preferences;

import java.text.ParseException;

public class PaymentDialog extends Dialog {

    private static final String TAG = "PaymentDialog";
    DialogPaymentBinding binding;

    public PaymentDialog(@NonNull Context context) {
        super(context);
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

        binding.tvCafe.setText(getContext().getString(R.string.cafe_confirmation, Preferences.getSelectedCafeName()));
        binding.tvRealprice.setText(getContext().getString(R.string.real_price, "0"));

        binding.btCancel.setOnClickListener(v -> {
            System.out.println("dialog canceled");
            dismiss();
        });

        binding.etCybergold.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int cyberGold = Integer.parseInt(binding.etCybergold.getText().toString());
                    binding.tvRealprice.setText(getContext().getString(R.string.real_price, getPriceFromCybergold(cyberGold)));
                    if (cyberGold > 0) {
                        binding.btAccept.setEnabled(true);
                        binding.btAccept.setTextColor(getContext().getColor(R.color.colorTextPure));
                    } else {
                        binding.btAccept.setEnabled(false);
                        binding.btAccept.setTextColor(getContext().getColor(R.color.colorTextTransparent));
                    }
                } catch (NumberFormatException e) {
                    Log.v(TAG, "Couldn't parse cybergold");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String getPriceFromCybergold(int cg) {
        return (cg / 100.0) + "€";
    }
}
