package com.linkeriyo.cybermanger.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linkeriyo.cybermanger.activities.MainActivity;
import com.linkeriyo.cybermanger.adapters.ProductsAdapter;
import com.linkeriyo.cybermanger.databinding.FragmentProductsBinding;
import com.linkeriyo.cybermanger.models.Product;
import com.linkeriyo.cybermanger.viewmodels.ProductModel;

import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    MainActivity mainActivity;
    FragmentProductsBinding binding;
    RecyclerView recyclerView;
    ProductModel viewModel;
    ArrayList<Product> products = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ProductModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductsBinding.inflate(inflater, container, false);

        recyclerView = binding.rvProducts;

        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
        recyclerView.setAdapter(new ProductsAdapter(products));

        return binding.getRoot();
    }

    public ProductModel getViewModel() {
        return viewModel;
    }
}