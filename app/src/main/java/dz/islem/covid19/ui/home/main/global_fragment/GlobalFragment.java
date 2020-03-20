package dz.islem.covid19.ui.home.main.global_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import dz.islem.covid19.R;
import dz.islem.covid19.data.DataManager;
import dz.islem.covid19.data.network.model.GlobalDataModel;
import dz.islem.covid19.ui.base.BaseFragment;


public class GlobalFragment extends BaseFragment<GlobalViewModel> {
    private static final String TAG = "GlobalFragment";
    private GlobalViewModel viewModel;
    @BindView(R.id.total_cases_value)
    TextView totalCases;
    @BindView(R.id.death_cases_value)
    TextView deathCases;
    @BindView(R.id.recover_cases_value)
    TextView recoverCases;

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
       viewModel = getViewModel();
       showGlobal();
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


    private void setTotalCases(String totalCases) {
        this.totalCases.setText(totalCases);
    }

    private void setDeathCases(String deathCases) {
        this.deathCases.setText(deathCases);
    }

    private void setRecoverCases(String recoverCases) {
        this.recoverCases.setText(recoverCases);
    }

}
