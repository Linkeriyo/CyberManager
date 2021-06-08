package com.linkeriyo.cybermanger.fragments.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.linkeriyo.cybermanger.adapters.PaymentMethodsAdapter;
import com.linkeriyo.cybermanger.databinding.FragmentPaymentMethodsBinding;
import com.linkeriyo.cybermanger.models.CreditCard;
import com.linkeriyo.cybermanger.requests.PaymentsRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

import java.util.ArrayList;

public class PaymentMethodsFragment extends Fragment {

    FragmentPaymentMethodsBinding binding;
    ArrayList<CreditCard> methods = new ArrayList<>();
    PaymentMethodsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentMethodsBinding.inflate(inflater, container, false);

        binding.btAddMethod.setOnClickListener(v -> {
            NavDirections action = PaymentMethodsFragmentDirections.actionPaymentMethodsFragmentToLoginFragment();
            Navigation.findNavController(binding.getRoot()).navigate(action);
        });

        adapter = new PaymentMethodsAdapter(this, methods);
        binding.rvMethods.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvMethods.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        PaymentsRequests.getMethodsByUser(this, Preferences.getToken());
    }

    public void setMethods(ArrayList<CreditCard> methodList) {
        methods.clear();
        methods.addAll(methodList);
        adapter.notifyDataSetChanged();
        refreshTextView();
    }

    private void refreshTextView() {
        if (methods.isEmpty()) {
            binding.tvNoMethods.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoMethods.setVisibility(View.INVISIBLE);
        }
    }
}