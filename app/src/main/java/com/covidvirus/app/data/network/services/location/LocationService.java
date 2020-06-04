package com.covidvirus.app.data.network.services.location;

import com.covidvirus.app.data.network.model.Location;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationService {
    private static final String BASE_URL = "http://ip-api.com";
    private LocationApi mLocationApi;

    private static LocationService mInstance;

    public static LocationService getInstance(){
        return mInstance == null ? mInstance = new LocationService() : mInstance;
    }

    private LocationService(){
        Retrofit mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mLocationApi = mRetrofit.create(LocationApi.class);
    }

    public Single<Location> getLocation() {
        return mLocationApi.getLocation();
    }
}
