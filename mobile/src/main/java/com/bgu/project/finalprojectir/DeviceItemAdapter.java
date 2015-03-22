package com.bgu.project.finalprojectir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgu.project.finalprojectir.classes.Device;

import java.util.List;

/**
     * This class represents a custom view to be held in the list including a name and an image
     * @author yoav
     *
     */
    public class DeviceItemAdapter extends ArrayAdapter<Device> {

        Context context;
        int layoutResourceId;
        List<Device> data = null;

        public DeviceItemAdapter(Context context, int layoutResourceId, List<Device> data) {
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

            Device listItem = data.get(position);
            holder.txtBigTitle.setText(listItem.title);
            holder.txtSmallTitle.setText(listItem.getBrand());
            holder.imgIcon.setImageResource(listItem.icon);

            return row;
        }

        static class ItemHolder
        {
            ImageView imgIcon;
            TextView txtBigTitle;
            TextView txtSmallTitle;
        }
    }
