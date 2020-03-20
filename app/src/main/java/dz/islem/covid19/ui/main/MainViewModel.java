package dz.islem.covid19.ui.main;

import android.graphics.Movie;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.Collections;
import java.util.List;

import dz.islem.covid19.data.network.model.CountryDataModel;
import dz.islem.covid19.data.network.model.GlobalDataModel;
import dz.islem.covid19.data.network.services.DataService;
import dz.islem.covid19.ui.base.BaseViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataService mDataService;
    private MutableLiveData<List<CountryDataModel>> mCountriesData;
    private MutableLiveData<CountryDataModel> mCountryData;
    private MutableLiveData<GlobalDataModel> mGlobalData;

    MainViewModel(DataService mDataService){
        this.mDataService = mDataService;
        mGlobalData = new MutableLiveData<>();
        mCountriesData = new MutableLiveData<>();
        mCountryData = new MutableLiveData<>();
    }

    MutableLiveData<List<CountryDataModel>> getmCountriesData(){
        return mCountriesData;
    }

    MutableLiveData<CountryDataModel> getmCountryData(){
        return mCountryData;
    }

    MutableLiveData<GlobalDataModel> getmGlobalData(){
        return mGlobalData;
    }

    void loadGlobalData(){
        mDataService.getDataApi().getGlobalData().enqueue(new GlobalDataCallback());
    }

    private void setmGlobalData(GlobalDataModel mGlobalData){
        this.mGlobalData.postValue(mGlobalData);
    }

    void loadCountriesData(){
        mDataService.getDataApi().getAllData().enqueue(new CountriesDataCallback());
    }

    private void setmCountriesData(List<CountryDataModel> mCountriesData){
        this.mCountriesData.postValue(mCountriesData);
    }

    void loadCountryData(String country){
        mDataService.getDataApi().getDatabyCountry(country).enqueue(new CountryDataCallback());
    }

    private void setmCountryData(CountryDataModel mCountryData){
        this.mCountryData.postValue(mCountryData);
    }

    private class GlobalDataCallback implements Callback<GlobalDataModel> {
        @Override
        public void onResponse(@NonNull Call<GlobalDataModel> call, @NonNull Response<GlobalDataModel> response) {

            if (response.body() != null)
                setmGlobalData(response.body());
        }
        @Override
        public void onFailure(Call<GlobalDataModel> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t );
        }
    }

    private class CountriesDataCallback implements Callback<List<CountryDataModel>> {
        @Override
        public void onResponse(@NonNull Call<List<CountryDataModel>> call, @NonNull Response<List<CountryDataModel>> response) {

            if (response.body() != null)
                setmCountriesData(response.body());
        }
        @Override
        public void onFailure(Call<List<CountryDataModel>> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t );
        }
    }

    private class CountryDataCallback implements Callback<CountryDataModel> {
        @Override
        public void onResponse(@NonNull Call<CountryDataModel> call, @NonNull Response<CountryDataModel> response) {

            if (response.body() != null)
                setmCountryData(response.body());
        }
        @Override
        public void onFailure(Call<CountryDataModel> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t );
        }
    }
}

