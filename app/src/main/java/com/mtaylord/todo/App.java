package com.mtaylord.todo;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by taylor on 12/4/16.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
