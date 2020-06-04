package com.covidvirus.app.ui.home.profile;

import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataManager dataManager;
    private MutableLiveData<CountryDataModel> mCountryData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private Disposable disposable;


    ProfileViewModel(DataManager dataManager){
        this.dataManager = dataManager;
    }


    MutableLiveData<CountryDataModel> getCountryData(){
        return mCountryData;
    }

    MutableLiveData<Boolean> getIsError(){
        return isError;
    }

    void loadCountryData(String country) {
        disposable = dataManager.getDataByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        countryDataModel -> {
                            if (countryDataModel != null ) {
                                setCountryData(countryDataModel);
                                isError.postValue(false);
                            }
                            },
                        error -> isError.postValue(true)
                );
    }

    private void setCountryData(CountryDataModel mCountryData){
        this.mCountryData.postValue(mCountryData);
    }
}

