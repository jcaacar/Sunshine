package com.example.jose.sunshine.app;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by jose on 27/01/2015.
 */
public final class App extends Application {

    private static Context context;
    //private BroadcastReceiver mReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        PreferenceManager.setDefaultValues(context, R.xml.pref_general, false);

        /*mReceiver = new Receiver();
          registerReceiver(
                    mReceiver,
                    new IntentFilter(Intent.ACTION_SCREEN_ON));*/
    }

    @Override
    public void onTerminate() {
        //unregisterReceiver(mReceiver);
        super.onTerminate();
    }

    public static Context getAppContext() {
        return context;
    }

}
