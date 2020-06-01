package com.covidvirus.app.ui.home;


import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.Location;
import com.covidvirus.app.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private MutableLiveData<Location> location = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private DataManager dataManager;


    MainViewModel(DataManager dataManager){
        this.dataManager = dataManager;
    }

    MutableLiveData<Location> getLocationData(){
        return location;
    }

    MutableLiveData<Boolean> getIsError(){
        return isError;
    }


    void loadLocationData(){
        dataManager.getLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null ) {
                            setLocationData(location);
                            isError.postValue(false);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        isError.postValue(true);
                    }
                });
    }

    private void setLocationData(Location location){
        this.location.postValue(location);
    }


}

