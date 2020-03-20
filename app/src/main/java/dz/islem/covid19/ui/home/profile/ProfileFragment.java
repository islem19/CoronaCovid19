package dz.islem.covid19.ui.home.profile;

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
import dz.islem.covid19.App;
import dz.islem.covid19.R;
import dz.islem.covid19.Utils;
import dz.islem.covid19.data.DataManager;
import dz.islem.covid19.data.network.model.CountryDataModel;
import dz.islem.covid19.ui.base.BaseFragment;

public class ProfileFragment extends BaseFragment<ProfileViewModel> {
    private static final String TAG = "ProfileFragment";
    private ProfileViewModel viewModel;
    @BindView(R.id.item_title)
    TextView countryName;
    @BindView(R.id.total_cases_value)
    TextView totalCases;
    @BindView(R.id.death_cases_value)
    TextView deathCases;
    @BindView(R.id.recover_cases_value)
    TextView recoverCases;

    public ProfileFragment() {
    }

    @Override
    public ProfileViewModel getViewModel() {
        ProfileViewModelFactory factory = new ProfileViewModelFactory(DataManager.getInstance().getDataService());
        return ViewModelProviders.of(this, factory).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = getViewModel();
        showProfile();
    }

    private void showProfile() {
        viewModel.getCountryData().observe(this, new CountryDataObserver());
        String currentCountry = Utils.getCountryName(getActivity().getApplicationContext());
        viewModel.loadCountryData(currentCountry);
    }

    private class CountryDataObserver implements Observer<CountryDataModel> {
        @Override
        public void onChanged(CountryDataModel country) {
            if (country == null) return;
            setCountryName(country.getCountry());
            setTotalCases(String.valueOf(country.getNbrCases()));
            setDeathCases(String.valueOf(country.getNbrDeath()));
            setRecoverCases(String.valueOf(country.getNbrRecovered()));
        }
    }

    private void setCountryName(String countryName) {
        this.countryName.setText(countryName);
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
