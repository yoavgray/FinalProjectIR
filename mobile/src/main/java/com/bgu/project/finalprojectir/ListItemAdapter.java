package com.bgu.project.finalprojectir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

    /**
     * This class represents a custom view to be held in the list including a name and an image
     * @author yoav
     *
     */
    public class ListItemAdapter extends ArrayAdapter<ListItem> {

        Context context;
        int layoutResourceId;
        ListItem data[] = null;

        public ListItemAdapter(Context context, int layoutResourceId, ListItem[] data) {
            super(context, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.data = data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ItemHolder holder = null;

            if(row == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new ItemHolder();
                holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
                holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);

                row.setTag(holder);
            }
            else
            {
                holder = (ItemHolder)row.getTag();
            }

            ListItem listItem = data[position];
            holder.txtTitle.setText(listItem.title);
            holder.imgIcon.setImageResource(listItem.icon);

            return row;
        }

        static class ItemHolder
        {
            ImageView imgIcon;
            TextView txtTitle;
        }
    }
