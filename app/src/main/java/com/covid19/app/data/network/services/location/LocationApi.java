package com.covid19.app.data.network.services.location;

import com.covid19.app.data.network.model.Location;
import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationApi {

    @GET("/json")
    Call<Location> getLocationData();


}
