package com.example.jose.sunshine.app.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jose.sunshine.app.R;
import com.example.jose.sunshine.app.model.Forecast;
import com.example.jose.sunshine.app.util.DateUtil;

public class ForecastAdapter extends ArrayAdapter<Forecast> {

   Forecast[] data;
   int layoytResourceId;

    public ForecastAdapter(Context context, int resourceId, Forecast[] data) {
        super(context, resourceId, data);
        this.layoytResourceId = resourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        Forecast item = this.data[position];

        if(row == null) {
            int layoutId = getLayoutResourceId(position);
            row = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        ImageView imgIcon = (ImageView)row.findViewById(R.id.list_item_icon);
        imgIcon.setImageResource(R.drawable.ic_launcher);

        TextView txtDate = (TextView)row.findViewById(R.id.list_item_date_textview);
        txtDate.setText(DateUtil.getDateFormatted(item.getDate()));

        TextView txtDescription = (TextView)row.findViewById(R.id.list_item_forecast_textview);
        txtDescription.setText(item.getDescription());

        TextView txtMax = (TextView)row.findViewById(R.id.list_item_high_textview);
        txtMax.setText(String.valueOf(item.getMax()) + "º");

        TextView txtMin = (TextView)row.findViewById(R.id.list_item_low_textview);
        txtMin.setText(String.valueOf(item.getMin()) + "º");

        return row;
    }

    private int getLayoutResourceId(int position) {
        return position == 0 ? R.layout.list_item_forecast_today : this.layoytResourceId;
    }
}
