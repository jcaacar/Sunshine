package com.example.jose.sunshine.app.bo;

import android.util.Log;
import org.json.JSONObject;
import java.io.IOException;

import com.example.jose.sunshine.app.model.Forecast;
import com.example.jose.sunshine.app.util.NetworkUtil;


/**
 * Created by jose on 27/01/2015.
 */
public class ForecastBO {

    private static final String LOGTAG = "ForecastBO";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&mode=json&units=metric&cnt=7";

    private static ForecastBO instance = null;

    private ForecastBO() {}
    public static ForecastBO getInstance() {
        if(instance == null)
            instance = new ForecastBO();
        return instance;
    }

    public Forecast getByCity(String name) {
        Forecast result = null;
        try {
            JSONObject json = NetworkUtil.getRequestJson(String.format(API_URL, name));
            result = new Forecast(json.toString());
        } catch (IOException e) {
            Log.e(LOGTAG, e.getMessage());
        }
        return result;
    }
}
