package dz.islem.covid19.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dz.islem.covid19.data.network.services.DataService;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    private final DataService mDataService;

    public MainViewModelFactory(DataService mDataService){
        this.mDataService = mDataService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(mDataService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
