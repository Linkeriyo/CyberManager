package com.linkeriyo.cybermanger.fragments.payment;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.linkeriyo.cybermanger.databinding.FragmentNewCardBinding;
import com.linkeriyo.cybermanger.models.CreditCard;
import com.linkeriyo.cybermanger.requests.PaymentsRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

public class NewCardFragment extends Fragment {

    FragmentNewCardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewCardBinding.inflate(inflater, container, false);

        binding.etHolderName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.etCardNumber.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});
        binding.etMonth.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        binding.etYear.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        binding.etCvv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});

        binding.btAddCard.setOnClickListener(v -> {
            CreditCard creditCard = new CreditCard(
                    binding.etHolderName.getText().toString(),
                    binding.etCardNumber.getText().toString(),
                    binding.etMonth.getText().toString(),
                    binding.etYear.getText().toString(),
                    binding.etCvv.getText().toString()
            );
            PaymentsRequests.registerNewCard(this, Preferences.getToken(), creditCard);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
