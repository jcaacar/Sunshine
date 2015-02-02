package com.example.jose.sunshine.app.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.jose.sunshine.app.App;

/**
 * Created by jose on 02/02/2015.
 */
public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "POWER ON", Toast.LENGTH_LONG).show();
            Log.w("RECEIVER", "POWERRRRRR!!!!");
        } else {
            Toast.makeText(context, "SCREEN ON", Toast.LENGTH_LONG).show();
            Log.w("RECEIVER", "SCREEENNNN!!!!");
        }

    }
}
