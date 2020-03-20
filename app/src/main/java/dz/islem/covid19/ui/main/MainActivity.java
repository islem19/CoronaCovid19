package dz.islem.covid19.ui.main;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dz.islem.covid19.R;
import dz.islem.covid19.Utils;
import dz.islem.covid19.data.DataManager;
import dz.islem.covid19.data.network.model.CountryDataModel;
import dz.islem.covid19.data.network.model.GlobalDataModel;
import dz.islem.covid19.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<MainViewModel> {

    private final String TAG ="MainActivity";

    @BindView(R.id.country_recyclerview)
    RecyclerView mRecyclerView;
    private MainAdapter mainAdapter;

    @Override
    public MainViewModel createViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(DataManager.getInstance().getDataService());
        return ViewModelProviders.of(this,factory).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainAdapter = new MainAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mainAdapter);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(5));


        viewModel.getmGlobalData().observe(this,new GlobalDataObserver());
        viewModel.getmCountriesData().observe(this, new CountriesDataObserver());
        viewModel.getmCountryData().observe(this, new CountryDataObserver());

        viewModel.loadGlobalData();
        viewModel.loadCountriesData();
        viewModel.loadCountryData("Algeria");
    }

    private class GlobalDataObserver implements Observer<GlobalDataModel> {
        @Override
        public void onChanged(GlobalDataModel globalDataModel) {
            if (globalDataModel == null ) return;
            Log.e(TAG, "onChanged: "+ globalDataModel.toString() );
        }
    }

    private class CountryDataObserver implements Observer<CountryDataModel> {
        @Override
        public void onChanged(CountryDataModel countryDataModel) {
            if (countryDataModel == null ) return;
            mainAdapter.addCountryData(countryDataModel,0);
        }
    }

    private class CountriesDataObserver implements Observer<List<CountryDataModel>> {
        @Override
        public void onChanged(List<CountryDataModel> countryDataModel) {
            if (countryDataModel == null ) return;
            mainAdapter.addCountriesData(countryDataModel);
        }
    }


}
