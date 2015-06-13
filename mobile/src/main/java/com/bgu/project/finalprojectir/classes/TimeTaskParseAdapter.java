package com.bgu.project.finalprojectir.classes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgu.project.finalprojectir.R;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.Arrays;

/**
 * Created by yoav on 27-May-15.
 */
public class TimeTaskParseAdapter extends ParseQueryAdapter<ParseTask>{

    public TimeTaskParseAdapter(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<ParseTask>() {
            public ParseQuery<ParseTask> create() {
                // Here we can configure a ParseQuery to display
                // only top-rated meals.
                ParseQuery query = new ParseQuery("ParseTask");
                query.whereEqualTo("type","time");
                query.whereEqualTo("user", ParseUser.getCurrentUser());
                return query;
            }
        });
    }

    @Override
    public View getItemView(ParseTask task, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.list_item_row, null);
        }

        super.getItemView(task, v, parent);

        ImageView imageView = (ImageView) v.findViewById(R.id.imgIcon);
        TextView titleTextView = (TextView) v.findViewById(R.id.txtBigTitle);
        TextView smallTextView = (TextView) v.findViewById(R.id.txtSmallTitle);

        titleTextView.setText(task.getTitle());
        smallTextView.setText(task.getInfo());
        imageView.setImageResource(Integer.parseInt(task.getString("icon")));
        return v;
    }

}
