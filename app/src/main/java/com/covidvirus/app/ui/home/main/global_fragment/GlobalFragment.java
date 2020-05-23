package com.covidvirus.app.ui.home.main.global_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.covidvirus.app.R;
import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.GlobalDataModel;
import com.covidvirus.app.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GlobalFragment extends BaseFragment<GlobalViewModel> {
    private static final String TAG = "GlobalFragment";
    private GlobalViewModel viewModel;
    @BindView(R.id.caseValue)
    TextView totalCases;
    @BindView(R.id.deathValue)
    TextView deathCases;
    @BindView(R.id.recoverValue)
    TextView recoverCases;
    @BindView(R.id.error_layout)
    ConstraintLayout errorLayout;
    @BindView(R.id.global_data_layout)
    ConstraintLayout globalLayout;

    public GlobalFragment() {
    }

    @Override
    public GlobalViewModel getViewModel() {
        GlobalViewModelFactory factory = new GlobalViewModelFactory(DataManager.getInstance().getDataService());
        return ViewModelProviders.of(this,factory).get(GlobalViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
       View view = inflater.inflate(R.layout.fragment_global, container, false);
        ButterKnife.bind(this, view);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = getViewModel();
        showGlobal();
    }

    private void showGlobal() {
        viewModel.getGlobalData().observe(this,new GlobalDataObserver());
        viewModel.getIsError().observe(this,new ErrorObserver());
        viewModel.loadGlobalData();
    }

    private class GlobalDataObserver implements Observer<GlobalDataModel> {
        @Override
        public void onChanged(GlobalDataModel global) {
            if (global == null ) return;
            setTotalCases(String.valueOf(global.getNbrCases()));
            setDeathCases(String.valueOf(global.getNbrDeaths()));
            setRecoverCases(String.valueOf(global.getNbrRecovered()));
        }
    }

    private class ErrorObserver implements Observer<Boolean> {

        @Override
        public void onChanged(Boolean isError) {
            if(isError){
                errorLayout.setVisibility(View.VISIBLE);
                globalLayout.setVisibility(View.GONE);

            }else {
                errorLayout.setVisibility(View.GONE);
                globalLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setTotalCases(String totalCases) {
        this.totalCases.setText(totalCases);
    }

    private void setDeathCases(String deathCases) {
        this.deathCases.setText(deathCases);
    }

    private void setRecoverCases(String recoverCases) {
        this.recoverCases.setText(recoverCases);
    }

    @OnClick(R.id.text_error)
    protected void loadProfile(){
        Log.e(TAG, "loadProfile: " );
        viewModel.loadGlobalData();
    }

}
