package dz.islem.covid19.data.network.services;

import java.util.List;

import dz.islem.covid19.data.network.model.CountryDataModel;
import dz.islem.covid19.data.network.model.GlobalDataModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataApi {

    @GET("/all")
    Call<GlobalDataModel> getGlobalData();

    @GET("/countries")
    Call<List<CountryDataModel>> getAllData();

    @GET("/countries/{country}")
    Call<CountryDataModel> getDataByCountry(@Path("country") String country);

}
