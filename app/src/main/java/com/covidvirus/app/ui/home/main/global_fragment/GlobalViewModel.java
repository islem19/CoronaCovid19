package com.covidvirus.app.ui.home.main.global_fragment;

import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.GlobalDataModel;
import com.covidvirus.app.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GlobalViewModel extends BaseViewModel {

    private static final String TAG = "GlobalFragmentViewModel";
    private DataManager dataManager;
    private MutableLiveData<GlobalDataModel> mGlobalData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();
    private Disposable disposable;

    GlobalViewModel(DataManager dataManager){
        this.dataManager = dataManager;
    }


    MutableLiveData<GlobalDataModel> getGlobalData(){
        return mGlobalData;
    }

    MutableLiveData<Boolean> getIsError(){
        return isError;
    }


    void loadGlobalData(){
        disposable = dataManager.getGlobalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        globalDataModel -> {
                            if(globalDataModel != null ) {
                                setGlobalData(globalDataModel);
                                isError.postValue(false);
                            }
                            }, error -> isError.postValue(true)
                );
    }

    private void setGlobalData(GlobalDataModel mGlobalData){
        this.mGlobalData.postValue(mGlobalData);
    }

}
