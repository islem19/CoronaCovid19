package com.covidvirus.app.ui.home;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.covidvirus.app.data.DataManager;
import com.covidvirus.app.data.network.model.Location;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dz.islem.covid19.RxImmediateSchedulerRule;
import io.reactivex.Single;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Rule
    public RxImmediateSchedulerRule rxImmediateSchedulerRule = new RxImmediateSchedulerRule();
    @Mock
    DataManager dataManager;
    private MainViewModel viewModel;
    @Mock Observer<String> locationObserver;
    @Mock Observer<Boolean> errorObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new MainViewModel(dataManager);
        viewModel.getLocationData().observeForever(locationObserver);
        viewModel.getIsError().observeForever(errorObserver);
    }

    @Test
    public void testNull(){
        when(dataManager.getLocation()).thenReturn(null);
        assertNotNull(viewModel.getLocationData());
        assertTrue(viewModel.getLocationData().hasObservers());
        assertTrue(viewModel.getIsError().hasObservers());
    }


    @Test
    public void testNewLocation(){
        //given
        Location mLocation = new Location("Algeria");
        given(dataManager.getLocation())
                .willReturn(Single.just(mLocation));
        // when
        viewModel.loadLocationData();

        //then
        then(locationObserver).should(times(1)).onChanged(mLocation.getCountry());
        then(errorObserver).should(times(1)).onChanged(false);
    }

    @Test
    public void testErrorLocation(){
        //given
        Throwable error = new Throwable("Error Response");
        given(dataManager.getLocation())
                .willReturn(Single.error(error));
        // when
        viewModel.loadLocationData();
        //then
        then(errorObserver).should(times(1)).onChanged(true);
        then(locationObserver).should(times(0)).onChanged(null);
    }

    @After
    public void tearDown(){
        dataManager = null;
    }



}