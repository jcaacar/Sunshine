package com.example.jose.sunshine.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by jose on 27/01/2015.
 */
public final class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
