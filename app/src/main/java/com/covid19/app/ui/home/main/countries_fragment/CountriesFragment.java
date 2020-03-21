package com.covid19.app.ui.home.main.countries_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.covid19.app.R;
import com.covid19.app.data.DataManager;
import com.covid19.app.data.network.model.CountryDataModel;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


import com.covid19.app.ui.base.BaseFragment;

public class CountriesFragment extends BaseFragment<CountriesViewModel> {
    private static final String TAG = "CountriesFragment";
    private CountriesViewModel viewModel;
    @BindView(R.id.country_recyclerview)
    RecyclerView mRecyclerView;
    private RecyclerAdapter recyclerAdapter;

    public CountriesFragment() {
    }

    @Override
    public CountriesViewModel getViewModel() {
        CountriesViewModelFactory factory = new CountriesViewModelFactory(DataManager.getInstance().getDataService());
        return ViewModelProviders.of(this, factory).get(CountriesViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_countries, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = getViewModel();
        setupRecyclerView();
        showCountries();
    }

    private void setupRecyclerView() {
        recyclerAdapter = new RecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration(5));
    }

    private void showCountries() {
        viewModel.getCountriesData().observe(this, new CountriesDataObserver());
        viewModel.loadCountriesData();
    }

    private class CountriesDataObserver implements Observer<List<CountryDataModel>> {
        @Override
        public void onChanged(List<CountryDataModel> countryDataModel) {
            if (countryDataModel == null) return;
            recyclerAdapter.addCountriesData(countryDataModel);
        }
    }

}
