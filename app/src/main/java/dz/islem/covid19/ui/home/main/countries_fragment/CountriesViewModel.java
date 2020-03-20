package dz.islem.covid19.ui.home.main.countries_fragment;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import java.util.List;
import dz.islem.covid19.data.network.model.CountryDataModel;
import dz.islem.covid19.data.network.services.DataService;
import dz.islem.covid19.ui.base.BaseViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesViewModel extends BaseViewModel {

    private static final String TAG = "MainViewModel";
    private DataService mDataService;
    private MutableLiveData<List<CountryDataModel>> mCountriesData;


    CountriesViewModel(DataService mDataService){
        this.mDataService = mDataService;
        mCountriesData = new MutableLiveData<>();
    }

    MutableLiveData<List<CountryDataModel>> getCountriesData(){
        return mCountriesData;
    }


    void loadCountriesData(){
        mDataService.getDataApi().getAllData().enqueue(new CountriesDataCallback());
    }

    private void setCountriesData(List<CountryDataModel> mCountriesData){
        this.mCountriesData.postValue(mCountriesData);
    }

    private class CountriesDataCallback implements Callback<List<CountryDataModel>> {
        @Override
        public void onResponse(@NonNull Call<List<CountryDataModel>> call, @NonNull Response<List<CountryDataModel>> response) {

            if (response.body() != null)
                setCountriesData(response.body());
        }
        @Override
        public void onFailure(Call<List<CountryDataModel>> call, Throwable t) {
            Log.e(TAG, "onFailure: "+ t );
        }
    }


}

