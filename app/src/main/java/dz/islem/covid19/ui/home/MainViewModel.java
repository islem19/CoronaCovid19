package dz.islem.covid19.ui.home;


import dz.islem.covid19.data.network.services.DataService;
import dz.islem.covid19.ui.base.BaseViewModel;


public class MainViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataService mDataService;


    MainViewModel(DataService mDataService){
        this.mDataService = mDataService;
    }

}

