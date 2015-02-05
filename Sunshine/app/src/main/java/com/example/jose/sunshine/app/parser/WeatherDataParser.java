package com.example.jose.sunshine.app.parser;

import com.example.jose.sunshine.app.model.Forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jose on 28/01/2015.
 */
public final class WeatherDataParser {

    private static final String TEMP = "temp";
    private static final String MAX_TEMP = "max";
    private static final String MIN_TEMP = "min";
    private static final String DAYS_LIST = "list";
    private static final String DATETIME = "dt";
    private static final String DESCRIPTION = "main";
    private static final String WEATHER = "weather";

    /**
     * Given a string of the form returned by the api call:
     * http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
     * retrieve the maximum temperature for the day indicated by dayIndex
     * (Note: 0-indexed, so 0 would refer to the first day).
     */
    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex) throws JSONException {
        JSONObject jsonObj = new JSONObject(weatherJsonStr);
        JSONArray list = jsonObj.getJSONArray(DAYS_LIST);

        return list.getJSONObject(dayIndex).getJSONObject(TEMP).getDouble(MAX_TEMP);
    }

    /* The date/time conversion code is going to be moved outside the asynctask later,
 * so for convenience we're breaking it out into its own method now.
 */
    private static String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date).toString();
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private static String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    public static String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(DAYS_LIST);

        String[] result = new String[numDays];
        for(int i = 0; i < weatherArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            String day;
            String description;
            String highAndLow;

            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".
            long dateTime = dayForecast.getLong(DATETIME);
            day = getReadableDateString(dateTime);

            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(WEATHER).getJSONObject(0);
            description = weatherObject.getString(DESCRIPTION);

            // Temperatures are in a child object called "temp".  Try not to name variables
            // "temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(TEMP);
            double high = temperatureObject.getDouble(MAX_TEMP);
            double low = temperatureObject.getDouble(MIN_TEMP);

            highAndLow = formatHighLows(high, low);
            result[i] = day + " - " + description + " - " + highAndLow;
        }

        return result;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    public static List<Forecast> getForecastListFromJson(String forecastJsonStr, int numDays) throws JSONException {

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(DAYS_LIST);
        List<Forecast> result = new ArrayList(weatherArray.length());
        
        for(int i = 0; i < weatherArray.length(); i++) {
            Forecast item = new Forecast();

            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".
            long dateTime = dayForecast.getLong(DATETIME);
            item.setDate(new Date(dateTime * 1000));

            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(WEATHER).getJSONObject(0);
            item.setDescription(weatherObject.getString(DESCRIPTION));

            // Temperatures are in a child object called "temp".  Try not to name variables
            // "temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(TEMP);
            item.setMax((int)Math.round(temperatureObject.getDouble(MAX_TEMP)));
            item.setMin((int)Math.round(temperatureObject.getDouble(MIN_TEMP)));

            result.add(item);
        }
        return result;
    }
}
