package com.covidvirus.app.ui.home.main.global_fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.services.data.DataService;

public class GlobalViewModelFactory implements ViewModelProvider.Factory {

    private final DataManager dataManager;

    public GlobalViewModelFactory(DataManager dataManager){
        this.dataManager = dataManager;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GlobalViewModel.class)) {
            return (T) new GlobalViewModel(dataManager);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
