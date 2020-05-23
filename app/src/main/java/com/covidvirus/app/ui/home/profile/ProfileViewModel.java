package com.covidvirus.app.ui.home.profile;

import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.data.network.services.DataService;
import com.covidvirus.app.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataService mDataService;
    private MutableLiveData<CountryDataModel> mCountryData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();


    ProfileViewModel(DataService mDataService){
        this.mDataService = mDataService;
    }


    MutableLiveData<CountryDataModel> getCountryData(){
        return mCountryData;
    }

    MutableLiveData<Boolean> getIsError(){
        return isError;
    }

    void loadCountryData(String country) {
        mDataService.getDataApi().getDataByCountry(country).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<CountryDataModel>() {
                    @Override
                    public void onSuccess(CountryDataModel countryDataModel) {
                        if (countryDataModel != null ) {
                            setCountryData(countryDataModel);
                            isError.postValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                            isError.postValue(true);
                    }
                });
    }

    private void setCountryData(CountryDataModel mCountryData){
        this.mCountryData.postValue(mCountryData);
    }
}

