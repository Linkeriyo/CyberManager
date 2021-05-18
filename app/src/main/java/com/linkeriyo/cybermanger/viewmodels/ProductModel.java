package com.linkeriyo.cybermanger.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.linkeriyo.cybermanger.models.Product;

import java.util.ArrayList;

public class ProductModel extends ViewModel {

    private final MutableLiveData<ArrayList<Product>> productsLiveData = new MutableLiveData<>();

    public ProductModel() {
    }

    public LiveData<ArrayList<Product>> getProducts() {
        return productsLiveData;
    }

}
