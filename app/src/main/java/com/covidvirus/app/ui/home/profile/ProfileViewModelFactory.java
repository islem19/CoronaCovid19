package com.covidvirus.app.ui.home.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.covidvirus.app.data.DataManager;


public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private final DataManager dataManager;

    public ProfileViewModelFactory(DataManager dataManager){
        this.dataManager = dataManager;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(dataManager);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
