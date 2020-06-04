package com.covidvirus.app.ui.splash;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.covidvirus.app.ui.home.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {

    @Rule
    public ActivityTestRule activityTestRule = new ActivityTestRule<>(SplashActivity.class,true,false);
    @Rule
    public IntentsTestRule intentsTestRule = new IntentsTestRule<>(SplashActivity.class, true,false);
    private Context mContext;

    @Before
    public void setUp(){
        mContext = getInstrumentation().getTargetContext();
        Intents.init();
    }

    @After
    public void tearDown(){
        Intents.release();
    }

    @Test
    public void testLaunchActivity(){
        activityTestRule.launchActivity(new Intent());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        intended(hasComponent(MainActivity.class.getName()));
    }



}