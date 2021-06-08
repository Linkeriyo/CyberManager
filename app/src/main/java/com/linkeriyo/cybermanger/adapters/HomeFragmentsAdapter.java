package com.linkeriyo.cybermanger.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.linkeriyo.cybermanger.fragments.home.ComputersFragment;
import com.linkeriyo.cybermanger.fragments.home.HomeFragment;

public class HomeFragmentsAdapter extends FragmentStateAdapter {
    public HomeFragmentsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ComputersFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
