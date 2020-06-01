package com.covidvirus.app.ui.home;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.covidvirus.app.data.DataManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule public ActivityTestRule activityTestRule = new ActivityTestRule<>(MainActivity.class,true,false);
    @Rule public IntentsTestRule intentsTestRule = new IntentsTestRule<>(MainActivity.class,true,false);
    private Context mContext;
    private MockWebServer mockWebServer ;

    @Before
    public void setUp(){
        mContext = getInstrumentation().getTargetContext();
        startMockServer();
        Intents.init();
    }


    @Test
    public void initMain(){
        activityTestRule.launchActivity(new Intent());
        DataManager.getInstance().setDefaultCountry(null);
    }

    @After
    public void tearDown(){
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intents.release();
    }


    private void startMockServer(){
        mockWebServer = new MockWebServer();
        try {
            mockWebServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getMockWebServerUrl(){
        return mockWebServer.url("/").toString();
    }

    private void mockNetworkResponse(String fileName, int responseCode){
        mockWebServer.enqueue(new MockResponse().setResponseCode(responseCode).setBody(getJson(fileName)));
    }

    private String getJson(String fileName){
        String json = null;
        try {
            InputStream inputStream = mContext.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}