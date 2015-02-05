package com.example.jose.sunshine.app.bo;

import android.accounts.NetworkErrorException;
import android.util.Log;

import org.json.JSONException;
import java.io.IOException;
import java.util.List;

import com.example.jose.sunshine.app.model.Forecast;
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

    public List<Forecast> getWeekDays(String city) throws IOException, JSONException {
        List<Forecast> result = null;
        try {
            String requestResult = NetworkUtil.getRequestString(String.format(API_URL, city));
            result = WeatherDataParser.getForecastListFromJson(requestResult, 7);
        } catch (IOException e) {
            Log.e(LOGTAG, e.getMessage());
        } catch (NetworkErrorException e) {
            Log.e(LOGTAG, e.getMessage());
        }
        return result;
    }
}
