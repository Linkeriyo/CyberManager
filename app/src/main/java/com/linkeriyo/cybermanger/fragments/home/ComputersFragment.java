package com.linkeriyo.cybermanger.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.adapters.ComputersAdapter;
import com.linkeriyo.cybermanger.databinding.FragmentComputersBinding;
import com.linkeriyo.cybermanger.models.Computer;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.requests.BusinessRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

import java.util.ArrayList;

public class ComputersFragment extends Fragment {

    MainActivity activity;
    FragmentComputersBinding binding;
    CyberCafe selectedCafe;
    ArrayList<Computer> computers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        selectedCafe = activity.getSelectedCafe();
    }

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
        binding = FragmentComputersBinding.inflate(inflater, container, false);

        binding.toolbar.setSubtitle(selectedCafe.getName());

        binding.tvBalance.setText(getString(R.string.balance, Preferences.getBalance()));

        binding.rvComputers.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvComputers.setAdapter(new ComputersAdapter(computers));

        binding.btBalance.setOnClickListener(v -> {
            activity.startAddBalanceActivity();
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        BusinessRequests.getComputersByBusinessId(this, Preferences.getToken(), selectedCafe.getBusinessId());
        binding.tvBalance.setText(getString(R.string.balance, Preferences.getBalance()));
    }

    public void setComputers(ArrayList<Computer> computerList) {
        computers.clear();
        computers.addAll(computerList);
        binding.rvComputers.getAdapter().notifyDataSetChanged();
        refreshTextView();
    }

    private void refreshTextView() {
        if (computers.isEmpty()) {
            binding.tvNoComputers.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoComputers.setVisibility(View.INVISIBLE);
        }
    }

}