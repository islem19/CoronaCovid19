package com.covidvirus.app.ui.home.main.countries_fragment;

import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class CountriesViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataManager dataManager;
    private MutableLiveData<List<CountryDataModel>> mCountriesData = new MutableLiveData<>();
    private MutableLiveData<CountryDataModel> mCountryData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();


    CountriesViewModel(DataManager dataManager){
        this.dataManager = dataManager;
    }

    MutableLiveData<List<CountryDataModel>> getCountriesData(){
        return mCountriesData;
    }

    MutableLiveData<CountryDataModel> getCountryData(){
        return mCountryData;
    }

    MutableLiveData<Boolean> getIsError() { return isError; }

    void loadCountryData(String country){
        dataManager.getDataByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<CountryDataModel>() {
                    @Override
                    public void onSuccess(CountryDataModel countryDataModel) {
                        if( countryDataModel != null ) {
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

    void loadCountriesData(){
        dataManager.getAllData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<CountryDataModel>>() {
                    @Override
                    public void onSuccess(List<CountryDataModel> countryDataModels) {
                        if (countryDataModels != null ) {
                            setCountriesData(countryDataModels);
                            isError.postValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isError.postValue(true);
                    }
                });
    }

    private void setCountriesData(List<CountryDataModel> mCountriesData){
        this.mCountriesData.postValue(mCountriesData);
    }

    private void setCountryData(CountryDataModel mCountryData){
        this.mCountryData.postValue(mCountryData);
    }

}

