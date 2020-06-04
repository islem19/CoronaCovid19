package com.covidvirus.app;

import com.covidvirus.app.data.network.model.CountryDataModel;

public class Utils {

    public static CountryDataModel generateCountryData(String country){
        return new CountryDataModel(country
                ,1
                ,1
                ,1
                ,1
                ,1
                ,1
                ,1
                ,1);
    }
}
