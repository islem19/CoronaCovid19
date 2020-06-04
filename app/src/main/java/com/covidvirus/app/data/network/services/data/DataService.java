package com.covidvirus.app.data.network.services.data;

import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.data.network.model.GlobalDataModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataService {
    private static final String BASE_URL = "https://corona.lmao.ninja";
    private DataApi mDataApi;

    private static DataService mInstance;

    public static DataService getInstance(){
        return mInstance == null ? mInstance = new DataService() : mInstance;
    }

    private DataService(){
        Retrofit mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        mDataApi = mRetrofit.create(DataApi.class);
    }

    public Single<GlobalDataModel> getGlobalData(){
        return mDataApi.getGlobalData();
    }

    public Single<List<CountryDataModel>> getAllData(){
        return mDataApi.getAllData();
    }

    public Single<CountryDataModel> getDataByCountry(String country){
        return mDataApi.getDataByCountry(country);
    }
}
