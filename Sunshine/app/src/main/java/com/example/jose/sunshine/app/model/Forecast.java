package com.example.jose.sunshine.app.model;

/**
 * Created by jose on 27/01/2015.
 */
public class Forecast {

    private String json;

    public Forecast(String j) {
        json = j;
    }

    @Override
    public String toString() {
        return json;
    }
}
