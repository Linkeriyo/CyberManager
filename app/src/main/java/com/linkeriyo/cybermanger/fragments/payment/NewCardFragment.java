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

/**
 * Fragment which shows the fields required to register a new CreditCard for this user
 * into the remote database.
 */
public class NewCardFragment extends Fragment {

    FragmentNewCardBinding binding;

    /**
     * Initializes the views needed.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
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
}
