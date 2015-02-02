package com.example.jose.sunshine.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by jose on 27/01/2015.
 */
public final class StreamUtil {

    public static String readToString(InputStream is) throws IOException {
        BufferedReader reader = null;
        try {
            String line;
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            if(reader != null)
                reader.close();
        }
    }
}
