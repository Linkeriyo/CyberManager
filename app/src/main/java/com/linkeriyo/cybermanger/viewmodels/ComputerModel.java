package com.linkeriyo.cybermanger.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.linkeriyo.cybermanger.models.Computer;

import java.util.ArrayList;

public class ComputerModel extends ViewModel {

    private final MutableLiveData<ArrayList<Computer>> computersLiveData = new MutableLiveData<>();

    public ComputerModel() {
    }

    public LiveData<ArrayList<Computer>> getComputers() {
        return computersLiveData;
    }
}
