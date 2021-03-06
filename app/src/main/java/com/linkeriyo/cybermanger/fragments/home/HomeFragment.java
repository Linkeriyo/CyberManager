package com.linkeriyo.cybermanger.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.linkeriyo.cybermanger.R;
import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.adapters.PostsAdapter;
import com.linkeriyo.cybermanger.databinding.FragmentHomeBinding;
import com.linkeriyo.cybermanger.models.CyberCafe;
import com.linkeriyo.cybermanger.models.Post;
import com.linkeriyo.cybermanger.requests.BusinessRequests;
import com.linkeriyo.cybermanger.utilities.Preferences;

import java.util.ArrayList;

/**
 * {@link Fragment} showing a {@link CyberCafe}'s info.
 */
public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    CyberCafe selectedCafe;
    MainActivity activity;
    ArrayList<Post> posts = new ArrayList<>();
    PostsAdapter adapter;

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
     *
     * @see Fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        selectedCafe = activity.getSelectedCafe();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Initializes the views needed.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @see Fragment
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getContext())
                .load(selectedCafe.getImage())
                .centerCrop()
                .into(binding.civCafeImage);
        binding.tvPosts.setText(getString(R.string.cafes_posts, selectedCafe.getName()));
        binding.tvCafeDesc.setText(selectedCafe.getDescription());
        binding.tvWelcome.setText(getString(R.string.welcome_to_cafe, selectedCafe.getName()));
        binding.llLogout.setOnClickListener(v -> {
            activity.logout();
        });
        binding.llChangeCafe.setOnClickListener(v -> {
            activity.startSelectCafeActivity();
        });
        adapter = new PostsAdapter(activity, posts);
        binding.rvPosts.setAdapter(adapter);
        binding.rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.refreshPosts.setOnRefreshListener(() -> {
            BusinessRequests.getPostsByBusinessId(this, Preferences.getToken(), selectedCafe.getBusinessId());
        });
    }

    /**
     * Executed everytime the {@link HomeFragment} is resumed. Gets the posts by the
     * selected cafe from the server.
     *
     * @see Post
     * @see CyberCafe
     * @see Fragment
     */
    @Override
    public void onResume() {
        super.onResume();
        BusinessRequests.getPostsByBusinessId(this, Preferences.getToken(), selectedCafe.getBusinessId());
    }

    private void refreshTextView() {
        if (posts.isEmpty()) {
            binding.tvNoPosts.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoPosts.setVisibility(View.INVISIBLE);
        }
    }
    
    public void setPosts(ArrayList<Post> postList) {
        posts.clear();
        posts.addAll(postList);
        binding.rvPosts.getAdapter().notifyDataSetChanged();
        refreshTextView();
        binding.refreshPosts.setRefreshing(false);
    }
}
