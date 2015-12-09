package com.himes.games.floodit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class FloodItSplashScreen extends Activity {

    private static final int ABOUT_MESSAGE_INDEX = 1;
    protected static final int NUM_SECONDS_FOR_SPLASH = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread splashThread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    int maxWait = 1000 * NUM_SECONDS_FOR_SPLASH;
                    while (waited < maxWait) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    Intent mainActivity = new Intent();
                    mainActivity.setClassName("com.himes.games.floodit", "com.himes.games.floodit.FloodItMainActivity");
//                    mainActivity.setClassName("com.himes.games.floodit", "com.himes.games.floodit.FloodItMenuButtons");   // when I add puzzle mode, I'll bring this back
                    startActivity(mainActivity);
                    if (userNeedsToSeeAboutMessage()) {
                        Intent aboutIntent = new Intent();
                        aboutIntent.setClassName("com.himes.games.floodit", "com.himes.games.floodit.preferences.About");
                        startActivity(aboutIntent);
                    }
                }
            }
        };
        splashThread.start();
    }

    private boolean userNeedsToSeeAboutMessage() {
        boolean needsToSee = false;
        
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String instructionsKey = getString(R.string.instructions_idx);
        
        if (sharedPreferences.getInt(instructionsKey, 0) < ABOUT_MESSAGE_INDEX) {
            needsToSee = true;
            sharedPreferences.edit().putInt(instructionsKey, ABOUT_MESSAGE_INDEX).commit();
        }
        
        return needsToSee;
    }
}
