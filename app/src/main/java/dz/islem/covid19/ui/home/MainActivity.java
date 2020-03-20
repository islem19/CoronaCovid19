package dz.islem.covid19.ui.home;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dz.islem.covid19.R;
import dz.islem.covid19.data.DataManager;
import dz.islem.covid19.ui.base.BaseActivity;
import dz.islem.covid19.ui.home.main.countries_fragment.CountriesFragment;
import dz.islem.covid19.ui.home.main.global_fragment.GlobalFragment;
import dz.islem.covid19.ui.home.profile.ProfileFragment;


public class MainActivity extends BaseActivity<MainViewModel> {

    private final String TAG ="MainActivity";
    @BindView(R.id.mainPager)
    ViewPager mainPager;
    @BindView(R.id.mainTabs)
    TabLayout mainTabLayout;
    private MainPagerAdapter mainAdapter;

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
        setMainPagerAdapter();
        mainTabLayout.setupWithViewPager(mainPager, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFragment(R.id.profileContainer, new ProfileFragment());
    }

    private void setMainPagerAdapter() {
        mainAdapter = new MainPagerAdapter(getSupportFragmentManager(), 0);
        mainAdapter.cleanFragments();
        mainAdapter.addFragment(new GlobalFragment());
        mainAdapter.addFragment(new CountriesFragment());
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Global");
        titles.add("Countries");
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



}
