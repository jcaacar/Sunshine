package com.example.jose.sunshine.app.model;

import java.util.Date;

/**
 * Created by jose on 27/01/2015.
 */
public class Forecast {

    private String json;

    private Date date;




    public Forecast(String j) {
        json = j;
    }

    @Override
    public String toString() {
        return json;
    }
}
