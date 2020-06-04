package com.covidvirus.app.ui.home;


import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.Location;
import com.covidvirus.app.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private MutableLiveData<String> location = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private DataManager dataManager;
    private Disposable disposable;


    MainViewModel(DataManager dataManager){
        this.dataManager = dataManager;
    }

    MutableLiveData<String> getLocationData(){
        return location;
    }

    MutableLiveData<Boolean> getIsError(){
        return isError;
    }


    void loadLocationData(){
        disposable = dataManager.getLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(Location::getCountry)
                .subscribe( country -> {
                    setLocationData(country);
                    isError.postValue(false);
                }, error -> isError.postValue(true));
    }

    private void setLocationData(String country){
        this.location.postValue(country);
    }

    public void onClear(){
        if (disposable != null ) disposable.dispose();
    }

}

