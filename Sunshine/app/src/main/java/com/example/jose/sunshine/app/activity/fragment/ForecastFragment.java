package com.example.jose.sunshine.app.activity.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jose.sunshine.app.R;
import com.example.jose.sunshine.app.activity.DetailActivity;
import com.example.jose.sunshine.app.activity.SettingsActivity;
import com.example.jose.sunshine.app.bo.ForecastBO;

public class ForecastFragment extends Fragment {

    private static final String LOGTAG = ForecastFragment.class.getSimpleName();

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
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                updateWeather();
                break;
            case R.id.action_map:
                openPreferredLocationInMap();
                break;
            case R.id.action_settings:
                SettingsActivity.open(getActivity());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void openPreferredLocationInMap() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = sharedPrefs.getString(getString(R.string.pref_location_key),
                                                getString(R.string.pref_location_default));

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location).build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOGTAG, "Couldn't call " + location + " no have app Map.");
        }
    }

    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = prefs.getString(getString(R.string.pref_location_key), null);
        new FetchWeatherTask().execute(location);
    }

    public class FetchWeatherTask extends AsyncTask<String, String[], String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            if(params.length == 0)
                return null;

            String[] result = null;
            try {
                result = ForecastBO.getInstance().getWeekDays(params[0]);
            } catch (Exception e) {
                Log.e(LOGTAG, e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String[] ret) {

            final ArrayAdapter<String> forecastAdapter = new ArrayAdapter<>(
                    getActivity(), // The current context (this activity)
                    R.layout.list_item_forecast, // The name of the layout ID.
                    R.id.list_item_forecast_textview, // The ID of the textview to populate.
                    ret);

            final ListView listView = (ListView) getActivity().findViewById(R.id.listview_forecast);
            listView.setAdapter(forecastAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class).
                putExtra(Intent.EXTRA_TEXT, forecastAdapter.getItem(position));
                startActivity(intent);
                }
            });
        }
    }
}
