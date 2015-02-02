package com.example.jose.sunshine.app.bo;

import android.accounts.NetworkErrorException;
import android.util.Log;

import org.json.JSONException;
import java.io.IOException;

import com.example.jose.sunshine.app.parser.WeatherDataParser;
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

    public String[] getWeekDays(String city) throws IOException, JSONException {
        String[] result = new String[0];
        try {
            String requestResult = NetworkUtil.getRequestString(String.format(API_URL, city));
            result = WeatherDataParser.getWeatherDataFromJson(requestResult, 7);
        } catch (IOException e) {
            Log.e(LOGTAG, e.getMessage());
        } catch (NetworkErrorException e) {
            Log.e(LOGTAG, e.getMessage());
        }
        return result;
    }
}
