package com.covid19.app.data;

import com.covid19.app.data.network.services.DataService;
import com.covid19.app.data.network.services.location.LocationService;

public class DataManager {

    private static DataManager mInstance;

    private DataManager(){

    }

    public static synchronized DataManager getInstance(){
        return mInstance == null ? new DataManager() : mInstance;
    }

    public DataService getDataService(){
        return DataService.getInstance();
    }

    public LocationService getLocationService(){
        return LocationService.getInstance();
    }

}
