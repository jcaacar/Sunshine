package com.example.jose.sunshine.app.activity.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jose.sunshine.app.R;
import com.example.jose.sunshine.app.bo.ForecastBO;
import com.example.jose.sunshine.app.model.Forecast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForecastFragment extends Fragment {

    private static final String LOGTAG = ForecastFragment.class.getSimpleName();

    private static final String CITY = "52040,BRA"; //Recife

    public ForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                new FetchWeatherTask().execute(CITY);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class FetchWeatherTask extends AsyncTask<String, String[], String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            if(params.length == 0)
                return null;

            String[] result = null;
            try {
                result = ForecastBO.getInstance().getWeekDays(params[0]);
            } catch (IOException e) {
                Log.e(LOGTAG, e.getMessage());
            } catch (JSONException e) {
                Log.e(LOGTAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String[] ret) {
            ArrayAdapter<String> forecastAdapter = new ArrayAdapter<String>(
                    getActivity(), // The current context (this activity)
                    R.layout.list_item_forecast, // The name of the layout ID.
                    R.id.list_item_forecast_textview, // The ID of the textview to populate.
                    ret);

            ListView listView = (ListView) getView().findViewById(R.id.listview_forecast);
            listView.setAdapter(forecastAdapter);
        }
    }
}
