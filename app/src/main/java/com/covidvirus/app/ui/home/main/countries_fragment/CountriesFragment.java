package com.covidvirus.app.ui.home.main.countries_fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.covidvirus.app.R;
import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.CountryDataModel;
import com.covidvirus.app.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountriesFragment extends BaseFragment<CountriesViewModel> {
    private static final String TAG = "CountriesFragment";
    private CountriesViewModel viewModel;
    @BindView(R.id.country_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.error_layout)
    ConstraintLayout errorLayout;
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
        recyclerAdapter = new RecyclerAdapter(new ItemOnClick());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(recyclerAdapter);
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration(5));

    }

    private void showCountries() {
        viewModel.getCountriesData().observe(this, new CountriesDataObserver());
        viewModel.getIsError().observe(this, new ErrorObserver());
        viewModel.loadCountriesData();
    }

    private class CountriesDataObserver implements Observer<List<CountryDataModel>> {
        @Override
        public void onChanged(List<CountryDataModel> countries) {
            if (countries == null) return;
            recyclerAdapter.addCountriesData(countries);
        }
    }

    private class ErrorObserver implements Observer<Boolean> {

        @Override
        public void onChanged(Boolean isError) {
            if (isError){
                errorLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else{
                errorLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
    }

    private class ItemOnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            CountryDataModel item = recyclerAdapter.getCountryData(itemPosition);
            showDetailDialog(item.getCountry());
        }
    }

    private void showDetailDialog(String country){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View dialogView = layoutInflater.inflate(R.layout.detail_dialog,null);
        cleanDialogView(dialogView);
        AlertDialog alertDialog = new  AlertDialog.Builder(getActivity()).create();
        alertDialog.setView(dialogView);
        alertDialog.setCancelable(true);
        viewModel.loadCountryData(country);

        viewModel.getCountryData().observe(this, countryDataModel -> setupDialogView(dialogView, countryDataModel ));
        viewModel.getIsError().observe(this, isError -> {
            if(isError) erroDialogView(dialogView);
        });

        dialogView.findViewById(R.id.aboutCancelBtn).setOnClickListener(view -> {
            alertDialog.cancel();
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    private void setupDialogView(View dialogView, CountryDataModel country){
        if (country == null) return;
        dialogView.findViewById(R.id.linearLayout).setVisibility(View.VISIBLE);
        dialogView.findViewById(R.id.progressBarDetail).setVisibility(View.GONE);
        dialogView.findViewById(R.id.error_layout).setVisibility(View.GONE);

        Glide.with(this).load(country.getCountryInfo().getFlag()).into((ImageView)dialogView.findViewById(R.id.flag_img));
        ((TextView)dialogView.findViewById(R.id.countryTitle)).setText(country.getCountry());
        ((TextView)dialogView.findViewById(R.id.totalCaseValue)).setText(String.valueOf(country.getNbrCases()));
        ((TextView)dialogView.findViewById(R.id.activeCaseValue)).setText(String.valueOf(country.getNbrActiveCases()));
        ((TextView)dialogView.findViewById(R.id.todayCaseValue)).setText(String.valueOf(country.getTodayCases()));
        ((TextView)dialogView.findViewById(R.id.totalDeathValue)).setText(String.valueOf(country.getNbrDeath()));
        ((TextView)dialogView.findViewById(R.id.todayDeathValue)).setText(String.valueOf(country.getTodayDeaths()));
        ((TextView)dialogView.findViewById(R.id.recoverValue)).setText(String.valueOf(country.getNbrRecovered()));
    }


    private void cleanDialogView(View dialogView){
        dialogView.findViewById(R.id.error_layout).setVisibility(View.GONE);
        dialogView.findViewById(R.id.linearLayout).setVisibility(View.GONE);
        dialogView.findViewById(R.id.progressBarDetail).setVisibility(View.VISIBLE);
    }

    private void erroDialogView(View dialogView) {
        dialogView.findViewById(R.id.linearLayout).setVisibility(View.GONE);
        dialogView.findViewById(R.id.progressBarDetail).setVisibility(View.GONE);
        dialogView.findViewById(R.id.error_layout).setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.text_error)
    protected void loadProfile(){
        Log.e(TAG, "loadProfile: " );
        viewModel.loadCountriesData();
    }


}
