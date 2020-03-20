package dz.islem.covid19.ui.home.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import dz.islem.covid19.R;
import dz.islem.covid19.Utils;
import dz.islem.covid19.data.DataManager;
import dz.islem.covid19.data.network.model.CountryDataModel;
import dz.islem.covid19.ui.base.BaseFragment;

public class ProfileFragment extends BaseFragment<ProfileViewModel> {
    private static final String TAG = "ProfileFragment";
    private ProfileViewModel viewModel;
    @BindView(R.id.countryTitle)
    TextView countryTitle;
    @BindView(R.id.caseItem)
    LinearLayout caseItem;
    @BindView(R.id.deathItem)
    LinearLayout deathItem;
    @BindView(R.id.recoverItem)
    LinearLayout recoverItem;


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
            setActiveCases(String.valueOf(country.getNbrActiveCases()));
            setTodayCases(String.valueOf(country.getTodayCases()));
            setTotalDeathCases(String.valueOf(country.getNbrDeath()));
            setTodayDeathCases(String.valueOf(country.getTodayDeaths()));
            setRecoverCases(String.valueOf(country.getNbrRecovered()));
        }
    }

    private void setCountryName(String countryName) {
        this.countryTitle.setText(countryName);
    }

    private void setTotalCases(String totalCases) {
        ( (TextView) this.caseItem.findViewById(R.id.totalCaseValue)).setText(totalCases);
    }

    private void setActiveCases(String totalCases) {
        ( (TextView) this.caseItem.findViewById(R.id.activeCaseValue)).setText(totalCases);
    }

    private void setTodayCases(String totalCases) {
        ( (TextView) this.caseItem.findViewById(R.id.todayCaseValue)).setText(totalCases);
    }

    private void setTotalDeathCases(String deathCases) {

        ( (TextView) this.deathItem.findViewById(R.id.totalDeathValue)).setText(deathCases);
    }

    private void setTodayDeathCases(String deathCases) {

        ( (TextView) this.deathItem.findViewById(R.id.todayDeathValue)).setText(deathCases);
    }

    private void setRecoverCases(String recoverCases) {

        ( (TextView) this.recoverItem.findViewById(R.id.recoverValue)).setText(recoverCases);
    }

}
