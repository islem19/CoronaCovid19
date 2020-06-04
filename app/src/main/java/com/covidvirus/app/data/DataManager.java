package com.covidvirus.app.data;

import com.covidvirus.app.data.local.SharedPreferencesHelper;
import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.data.network.model.GlobalDataModel;
import com.covidvirus.app.data.network.model.Location;
import com.covidvirus.app.data.network.services.data.DataService;
import com.covidvirus.app.data.network.services.location.LocationService;

import java.util.List;

import io.reactivex.Single;

public class DataManager {
    public static final int MAX_COUNT = 5;

    private static DataManager mInstance;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private LocationService locationService;
    private DataService dataService;

    private DataManager(){
        sharedPreferencesHelper = SharedPreferencesHelper.getInstance();
        locationService = LocationService.getInstance();
        dataService = DataService.getInstance();
    }

    public static synchronized DataManager getInstance(){
        return mInstance == null ? mInstance = new DataManager() : mInstance;
    }

    public void setDefaultCountry(String country){
        sharedPreferencesHelper.setDefaultCountry(country);
    }

    public String getDefaultCountry(){
        return sharedPreferencesHelper.getDefaultCountry();
    }

    public void setRunCount(){
        int count = getRunCount();
        if (count == MAX_COUNT || count > MAX_COUNT) count = 0; else count++;
        sharedPreferencesHelper.setRunCount(count);
    }

    public int getRunCount(){
        return sharedPreferencesHelper.getRunCount();
    }

    public Single<GlobalDataModel> getGlobalData(){
        return dataService.getGlobalData();
    }

    public Single<List<CountryDataModel>> getAllData(){
        return dataService.getAllData();
    }

    public Single<CountryDataModel> getDataByCountry(String country){
        return dataService.getDataByCountry(country);
    }

    public Single<Location> getLocation() {
        return locationService.getLocation();
    }
}
