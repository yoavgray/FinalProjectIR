package com.bgu.project.finalprojectir.classes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgu.project.finalprojectir.R;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by yoav on 26-May-15.
 */
public class ArduinoParseAdapter extends ParseQueryAdapter<ParseArduino> {
    public ArduinoParseAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseArduino>() {
            public ParseQuery<ParseArduino> create() {
                // Here we can configure a ParseQuery to display
                // only top-rated meals.
                ParseQuery query = new ParseQuery("ParseArduino");
                query.whereEqualTo("user", ParseUser.getCurrentUser());
                return query;
            }
        });
    }

    @Override
    public View getItemView(ParseArduino arduino, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.list_item_row, null);
        }

        super.getItemView(arduino, v, parent);

        ImageView imageView = (ImageView) v.findViewById(R.id.imgIcon);
        TextView titleTextView = (TextView) v.findViewById(R.id.txtBigTitle);
        TextView ipTextView = (TextView) v.findViewById(R.id.txtSmallTitle);

        titleTextView.setText(arduino.getTitle());
        ipTextView.setText(arduino.getIp());
        imageView.setImageResource(Integer.parseInt(arduino.getString("icon")));
        return v;
    }

}
