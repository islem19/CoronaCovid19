package com.covidvirus.app.ui.home.main.countries_fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.services.data.DataService;


public class CountriesViewModelFactory implements ViewModelProvider.Factory {

    private final DataManager dataManager;

    public CountriesViewModelFactory(DataManager dataManager){
        this.dataManager = dataManager;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CountriesViewModel.class)) {
            return (T) new CountriesViewModel(dataManager);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
