package com.example.jose.sunshine.app.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jose on 27/01/2015.
 */
public class Forecast {

    private int max;
    private int min;
    private Date date;
    private String description;

    public Forecast() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
