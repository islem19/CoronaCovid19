package com.covidvirus.app.data.network.services.location;

import com.covidvirus.app.data.network.model.Location;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface LocationApi {

    @GET("/json")
    Single<Location> getLocation();


}
