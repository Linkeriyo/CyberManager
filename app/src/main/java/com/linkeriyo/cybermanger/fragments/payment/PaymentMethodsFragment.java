package com.linkeriyo.cybermanger.fragments.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.databinding.FragmentLoginBinding;
import com.linkeriyo.cybermanger.databinding.FragmentPaymentMethodsBinding;

public class PaymentMethodsFragment extends Fragment {

    FragmentPaymentMethodsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentMethodsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}