package com.bgu.project.finalprojectir.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.classes.Arduino;

import java.util.List;

/**
     * This class represents a custom view to be held in the list including a name and an image
     * @author yoav
     *
     */
    public class ArduinoItemAdapter extends ArrayAdapter<Arduino> {

        Context context;
        int layoutResourceId;
        List<Arduino> data = null;

        public ArduinoItemAdapter(Context context, int layoutResourceId, List<Arduino> data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ItemHolder holder;

            if(row == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ItemHolder();
                holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
                holder.txtBigTitle = (TextView)row.findViewById(R.id.txtBigTitle);
                holder.txtSmallTitle = (TextView)row.findViewById(R.id.txtSmallTitle);

                row.setTag(holder);
            }
            else
            {
                holder = (ItemHolder)row.getTag();
            }

            Arduino listItem = data.get(position);
            holder.txtBigTitle.setText(listItem.getTitle());
            holder.txtSmallTitle.setText(listItem.getIp());
            holder.imgIcon.setImageResource(listItem.getIcon());

            return row;
        }

        static class ItemHolder
        {
            ImageView imgIcon;
            TextView txtBigTitle;
            TextView txtSmallTitle;
        }
    }
