package com.linkeriyo.cybermanger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.fragments.payment.NewCardFragment;
import com.linkeriyo.cybermanger.fragments.payment.PaymentMethodsFragment;
import com.linkeriyo.cybermanger.models.CyberCafe;

/**
 * Activity used to hold the fragments in charge to add balance to the user for the selected
 * {@link CyberCafe}.
 * @see PaymentMethodsFragment
 * @see NewCardFragment
 */
public class AddBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance);
    }
}