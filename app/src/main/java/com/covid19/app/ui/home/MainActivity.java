package com.covid19.app.ui.home;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.covid19.app.R;
import com.covid19.app.data.network.model.Location;
import com.google.android.material.tabs.TabLayout;
import com.covid19.app.data.DataManager;
import com.covid19.app.ui.base.BaseActivity;
import com.covid19.app.ui.home.main.countries_fragment.CountriesFragment;
import com.covid19.app.ui.home.main.global_fragment.GlobalFragment;
import com.covid19.app.ui.home.profile.ProfileFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity<MainViewModel> {

    public static final String MY_PREFS_NAME = "current_country";
    private final String TAG = "MainActivity";
    @BindView(R.id.mainPager)
    ViewPager mainPager;
    @BindView(R.id.mainTabs)
    TabLayout mainTabLayout;
    private MainPagerAdapter mainAdapter;
    private MainViewModel viewModel;
    private SharedPreferences.Editor editor;
    private String country;

    @Override
    public MainViewModel createViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(DataManager.getInstance().getLocationService());
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SharedPreferences prefs = getSharedPreferences(MainActivity.MY_PREFS_NAME, MODE_PRIVATE);
        country = prefs.getString("country", null);
        if (country == null) {
            editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            viewModel.loadLocationData();
            viewModel.getLocationData().observe(this, new LocationDataObserver());
        } else
            loadFragment(R.id.profileContainer, new ProfileFragment());
        setMainPagerAdapter();
        mainTabLayout.setupWithViewPager(mainPager, true);
    }


    private void setMainPagerAdapter() {
        mainAdapter = new MainPagerAdapter(getSupportFragmentManager(), 0);
        mainAdapter.cleanFragments();
        mainAdapter.addFragment(new GlobalFragment());
        mainAdapter.addFragment(new CountriesFragment());
        ArrayList<String> titles = new ArrayList<>();
        titles.add(this.getResources().getString(R.string.global_fragment));
        titles.add(this.getResources().getString(R.string.countries_fragment));
        mainAdapter.addTitles(titles);
        mainPager.setAdapter(mainAdapter);
    }

    private void loadFragment(int viewId, Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(viewId, fragment)
                    .commit();
        }
    }

    private class LocationDataObserver implements Observer<Location> {
        @Override
        public void onChanged(Location location) {
            if (location == null) return;
            Log.d(TAG, "onChanged: location " + location.getCountry());
            editor.putString("country", location.getCountry());
            editor.apply();
            country = location.getCountry();
            loadFragment(R.id.profileContainer, new ProfileFragment());
        }
    }

    public String getCountry() {
        return country;
    }
}
