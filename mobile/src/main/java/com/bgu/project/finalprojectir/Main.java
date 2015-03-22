package com.bgu.project.finalprojectir;

import android.app.FragmentManager;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bgu.project.finalprojectir.classes.Arduino;
import com.bgu.project.finalprojectir.fragments.AddArduinoFragment;

import java.util.ArrayList;
import java.util.List;


public class Main extends ActionBarActivity {
    private static final int RESULT_SETTINGS = 1;
    private static final int RESULT_ADD_ARDUINO = 2;
    FragmentManager fm = getFragmentManager();

    static ArduinoItemAdapter adapter;
    static List<Arduino> listItem_data;
    static PlaceholderFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            mainFragment = new PlaceholderFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.main_container, mainFragment)
                    .commit();
        }

        // Adding notifications - Eventually for the Watch
        int notificationId = 1;
        // Build intent for notification content
        Intent viewIntent = new Intent(this, DeviceActivity.class);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0);

        // Build an intent for an action to view a map
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        Uri geoUri = Uri.parse("geo:0,0?q=" + Uri.encode(null));
        mapIntent.setData(geoUri);
        PendingIntent mapPendingIntent =
                PendingIntent.getActivity(this, 0, mapIntent, 0);

        Notification notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.bgu_logo)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.yoav_icon))
                        .setContentTitle("Arduino Detected!")
                        .setContentText("Gray's Arduino")
                        .setContentIntent(viewPendingIntent)
                        .build();

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.add_device:
                AddArduinoFragment aaFragment = new AddArduinoFragment();
                // Show DialogFragment
                aaFragment.setTargetFragment(mainFragment, RESULT_ADD_ARDUINO);
                aaFragment.show(fm, "Dialog Fragment");
                return true;
            case R.id.action_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class),
                        RESULT_SETTINGS);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.d("msg","" + requestCode);
        switch (requestCode) {
            case RESULT_SETTINGS:
                SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(this);
                //mIsPtt = sharedPrefs.getBoolean("ptt_checkbox", true);
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case RESULT_ADD_ARDUINO:
                    if (resultCode == RESULT_OK) {
                        // The user added a device
                        String name = data.getStringExtra("nameResult");
                        String ip = data.getStringExtra("ipResult");
                        listItem_data.add(new Arduino(R.drawable.bgu_logo,name,ip));
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            listItem_data = new ArrayList<>();
            listItem_data.add(new Arduino(R.drawable.boaz_icon, "Buzi's Arduino", "127.0.0.1"));
            listItem_data.add(new Arduino(R.drawable.yoav_icon, "Gray's Arduino", "127.0.0.2"));
            listItem_data.add(new Arduino(R.drawable.asi_icon, "Asi's Arduino", "127.0.0.3"));
            listItem_data.add(new Arduino(R.drawable.omri_icon, "Havivian's Arduino", "127.0.0.4"));

            adapter = new ArduinoItemAdapter(getActivity(),
                    R.layout.list_item_row, listItem_data);

            final ListView listView = (ListView) rootView
                    .findViewById(R.id.devicesListView);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View myView, int pos,
                                        long arg3) {
                    Intent intent = new Intent(getActivity(),
                            DeviceActivity.class)
                            .putExtra("deviceName", listItem_data.get(pos).title)
                            .putExtra("deviceIp", listItem_data.get(pos).getIp());
                    startActivity(intent);

                }
            });

            return rootView;
        }
    }

}
