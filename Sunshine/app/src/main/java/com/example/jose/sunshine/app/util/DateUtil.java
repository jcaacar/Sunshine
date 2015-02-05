package com.example.jose.sunshine.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jose on 05/02/2015.
 */
public final class DateUtil {

    public static String getDateFormatted(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd");
        return sdf.format(date);
    }
}
