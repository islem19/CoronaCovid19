package com.covidvirus.app.data.local;

import android.content.SharedPreferences;

import com.covidvirus.app.App;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesHelper {

    private static SharedPreferencesHelper mInstance;
    private SharedPreferences sharedPreferences;

    public static final String COUNTRY_KEY = "county";
    public static final String MY_PREFS_NAME = "covid_pref";
    public static final String RUN_COUNT_KEY = "run_count";


    private SharedPreferencesHelper(){
        sharedPreferences = App.getInstance().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesHelper getInstance(){
        return mInstance == null ? mInstance = new SharedPreferencesHelper() : mInstance;
    }

    public void setDefaultCountry(String country){
        sharedPreferences.edit()
                .putString(COUNTRY_KEY, country)
                .apply();
    }

    public String getDefaultCountry(){
        return sharedPreferences.getString(COUNTRY_KEY, null);
    }

    public void setRunCount(int count){
        sharedPreferences.edit()
                .putInt(RUN_COUNT_KEY, count)
                .apply();
    }

    public int getRunCount(){
        return sharedPreferences.getInt(RUN_COUNT_KEY, 0);
    }


}
