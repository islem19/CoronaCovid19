package com.covid19.app.ui.home;


import com.covid19.app.data.network.services.DataService;
import com.covid19.app.ui.base.BaseViewModel;


public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataService mDataService;


    MainViewModel(DataService mDataService){
        this.mDataService = mDataService;
    }

}

