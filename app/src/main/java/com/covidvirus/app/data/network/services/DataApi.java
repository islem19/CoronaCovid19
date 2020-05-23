package com.covidvirus.app.data.network.services;

import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.data.network.model.GlobalDataModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataApi {

    @GET("v2/all")
    Single<GlobalDataModel> getGlobalData();

    @GET("v2/countries?sort=cases")
    Single<List<CountryDataModel>> getAllData();

    @GET("v2/countries/{country}")
    Single<CountryDataModel> getDataByCountry(@Path("country") String country);

}
