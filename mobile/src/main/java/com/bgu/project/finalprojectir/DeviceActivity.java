package com.bgu.project.finalprojectir;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class DeviceActivity extends ActionBarActivity {
    private static Intent incomingIntent;
    private static String mDeviceName;
    private static int    mDeviceImageId;

    private static ImageView mImageView;
    private static TextView mDeviceTextView;

    static ListItemAdapter adapter;
    static ListItem[] listItem_data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        incomingIntent = getIntent();
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        mDeviceName = incomingIntent.getStringExtra("deviceName");
        mDeviceImageId= incomingIntent.getIntExtra("image", 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_device, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_device, container, false);
            mImageView = (ImageView) rootView.findViewById(R.id.deviceImageView);
            mDeviceTextView = (TextView) rootView.findViewById(R.id.deviceNameTextView);

            mImageView.setImageResource(mDeviceImageId);
            mDeviceTextView.setText(mDeviceName);

            listItem_data = new ListItem[] {
                    new ListItem(R.drawable.ac_icon, "A/C Unit"),
                    new ListItem(R.drawable.tv_icon, "Television"),
                    new ListItem(R.drawable.water_heater_icon, "Water Heater"),
                    new ListItem(R.drawable.rasp_icon, "Raspberry Pi")
            };

            adapter = new ListItemAdapter(getActivity(),
                    R.layout.list_item_row, listItem_data);

            final ListView listView = (ListView) rootView
                    .findViewById(R.id.devicePropertiesListView);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View myView, int pos,
                                        long arg3) {
                    Intent intent;
                    switch (pos) {
                        case 0:
                            intent = new Intent(getActivity(),
                                    ACRemoteControlActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(getActivity(),
                                    TVRemoteControlActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            Toast.makeText(getActivity(),"Water Heater is not implemented yet",Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(getActivity(),"Raspberry Pi is not implemented yet",Toast.LENGTH_SHORT).show();
                            break;
                    }


                }
            });

            return rootView;
        }
    }
}
