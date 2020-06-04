package com.covidvirus.app.ui.home.profile;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.covidvirus.app.Utils;
import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.CountryDataModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dz.islem.covid19.RxImmediateSchedulerRule;
import io.reactivex.Single;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@RunWith(JUnit4.class)
public class ProfileViewModelTest {

    @Rule public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Rule public RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();

    @Mock
    DataManager dataManager;
    private ProfileViewModel viewModel;
    private CountryDataModel countryDataModel;
    @Mock Observer<CountryDataModel> countryDataModelObserver;
    @Mock Observer<Boolean> errorObserver;
    @Captor ArgumentCaptor<CountryDataModel> arg;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        viewModel = new ProfileViewModel(dataManager);
        viewModel.getCountryData().observeForever(countryDataModelObserver);
        viewModel.getIsError().observeForever(errorObserver);
        countryDataModel = Utils.generateCountryData("Algeria");
    }

    @Test
    public void testNull(){
        assertNotNull(viewModel.getCountryData());
        assertNotNull(viewModel.getIsError());
        assertTrue(viewModel.getCountryData().hasObservers());
        assertTrue(viewModel.getIsError().hasObservers());
    }

    @Test
    public void testLoadingAnyData(){
        //given
        given(dataManager.getDataByCountry(Mockito.anyString())).willReturn(Single.just(countryDataModel));
        //act
        viewModel.loadCountryData(anyString());
        //verify
        then(countryDataModelObserver).should(times(1)).onChanged(countryDataModel);
        then(errorObserver).should(times(1)).onChanged(false);

    }

    @Test
    public void testLoadingCountryData(){
        //given
        given(dataManager.getDataByCountry("Algeria")).willReturn(Single.just(countryDataModel));
        //act
        viewModel.loadCountryData("Algeria");
        //verify
        then(countryDataModelObserver).should(times(1)).onChanged(arg.capture());
        CountryDataModel countryCaptured = arg.getAllValues().get(0);

        assertEquals(countryCaptured.getCountry(), countryDataModel.getCountry());
        then(errorObserver).should(times(1)).onChanged(false);
    }

    @Test
    public void testVerifyData(){
        //given
        given(dataManager.getDataByCountry("Algeria"))
                .willReturn(Single.just(countryDataModel));
        //act
        viewModel.loadCountryData("Algeria");
        //verify
        assertThat(viewModel.getCountryData().getValue().getCountry(), equalTo("Algeria"));
    }

    @Test
    public void testErrorState(){
        //given
        Throwable error = new Throwable("error response");
        given(dataManager.getDataByCountry(Mockito.anyString()))
                .willReturn(Single.error(error));
        //act
        viewModel.loadCountryData(Mockito.anyString());
        //verify
        then(countryDataModelObserver).should(times(0)).onChanged(null);
        then(errorObserver).should(times(1)).onChanged(true);

    }

}