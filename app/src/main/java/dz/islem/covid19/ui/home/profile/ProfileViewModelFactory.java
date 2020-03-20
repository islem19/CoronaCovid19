package dz.islem.covid19.ui.home.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dz.islem.covid19.data.network.services.DataService;


public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private final DataService mDataService;

    public ProfileViewModelFactory(DataService mDataService){
        this.mDataService = mDataService;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(mDataService);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
