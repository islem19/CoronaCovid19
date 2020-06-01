package com.covidvirus.app.ui.home;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.covidvirus.app.data.network.model.Location;
import com.covidvirus.app.data.network.services.location.LocationApi;
import com.covidvirus.app.data.network.services.location.LocationService;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
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

    @Mock LocationApi locationApi;
    @InjectMocks LocationService locationService;
    private MainViewModel viewModel;
    @Mock Observer<Location> locationObserver;
    @Mock Observer<Boolean> errorObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        viewModel = new MainViewModel(locationService);
        viewModel.getLocationData().observeForever(locationObserver);
        viewModel.getIsError().observeForever(errorObserver);
    }

    @Test
    public void testNull(){
        //when(locationApi.getLocationData()).thenReturn(null);
        when(locationService.getLocationApi().getLocationData()).thenReturn(null);
        assertNotNull(viewModel.getLocationData());
        assertTrue(viewModel.getLocationData().hasObservers());
        assertTrue(viewModel.getIsError().hasObservers());
    }

    @Test
    public void testEmptyLocation(){
        //when(locationApi.getLocationData()).thenReturn(null);
        when(locationService.getLocationApi().getLocationData()).thenReturn( null);
        assertNotNull(viewModel.getLocationData());
        assertTrue(viewModel.getLocationData().hasObservers());
        assertTrue(viewModel.getIsError().hasObservers());
    }


    @Test
    public void testNewLocation(){
        //given
        Location mLocation = new Location("Algeria");
        given(locationService.getLocationApi().getLocationData())
                .willReturn(Single.just(mLocation));
        // when
        viewModel.loadLocationData();

        //then
        then(locationObserver).should().onChanged(mLocation);
        then(errorObserver).should().onChanged(false);
    }

    @Test
    public void testErrorLocation(){
        //given
        Throwable error = new Throwable("Error Response");
        given(locationService.getLocationApi().getLocationData())
                .willReturn(Single.error(error));
        // when
        viewModel.loadLocationData();
        //then
        then(errorObserver).should().onChanged(true);
        then(locationObserver).should(times(0)).onChanged(null);
    }

    @After
    public void tearDown(){
        locationApi = null;
        locationService = null;
    }



}