package com.linkeriyo.cybermanger.fragments.payment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkeriyo.cybermanger.adapters.PaymentMethodsAdapter;
import com.linkeriyo.cybermanger.databinding.FragmentPaymentMethodsBinding;
import com.linkeriyo.cybermanger.models.CreditCard;
import com.linkeriyo.cybermanger.models.User;
import com.linkeriyo.cybermanger.requests.PaymentsRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

import java.util.ArrayList;

/**
 * Fragment which shows the payment methods the current user has added to the database. Payment
 * methods do not depend on the selected cafe.
 *
 * Also has a <code>Button</code> to add a new
 */
public class PaymentMethodsFragment extends Fragment {

    FragmentPaymentMethodsBinding binding;
    ArrayList<CreditCard> methods = new ArrayList<>();
    PaymentMethodsAdapter adapter;


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

    /**
     * Executed everytime the {@link PaymentMethodsFragment} is resumed. Gets the payment
     * methods for this {@link User} from the server.
     */
    @Override
    public void onResume() {
        super.onResume();
        PaymentsRequests.getMethodsByUser(this, Preferences.getToken());
    }

    /**
     * Overrides the {@link ArrayList<CreditCard>} for the {@link RecyclerView}
     * @param methodList List to get values from
     */
    public void setMethods(ArrayList<CreditCard> methodList) {
        methods.clear();
        methods.addAll(methodList);
        adapter.notifyDataSetChanged();
        refreshTextView();
    }

    /**
     * Refreshes the {@link TextView} showing that there is no items in the {@link RecyclerView}
     */
    private void refreshTextView() {
        if (methods.isEmpty()) {
            binding.tvNoMethods.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoMethods.setVisibility(View.INVISIBLE);
        }
    }
}