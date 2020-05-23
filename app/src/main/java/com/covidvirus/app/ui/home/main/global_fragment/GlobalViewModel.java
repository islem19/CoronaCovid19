package com.covidvirus.app.ui.home.main.global_fragment;

import androidx.lifecycle.MutableLiveData;

import com.covidvirus.app.data.network.model.GlobalDataModel;
import com.covidvirus.app.data.network.services.DataService;
import com.covidvirus.app.ui.base.BaseViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GlobalViewModel extends BaseViewModel {

    private static final String TAG = "GlobalFragmentViewModel";
    private DataService mDataService;
    private MutableLiveData<GlobalDataModel> mGlobalData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isError = new MutableLiveData<>();

    GlobalViewModel(DataService mDataService){
        this.mDataService = mDataService;
    }


    MutableLiveData<GlobalDataModel> getGlobalData(){
        return mGlobalData;
    }

    MutableLiveData<Boolean> getIsError(){
        return isError;
    }


    void loadGlobalData(){
        mDataService.getDataApi().getGlobalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<GlobalDataModel>() {
                    @Override
                    public void onSuccess(GlobalDataModel globalDataModel) {
                        if (globalDataModel != null ){
                            setGlobalData(globalDataModel);
                            isError.postValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        isError.postValue(true);
                    }
                });
    }

    private void setGlobalData(GlobalDataModel mGlobalData){
        this.mGlobalData.postValue(mGlobalData);
    }

}
