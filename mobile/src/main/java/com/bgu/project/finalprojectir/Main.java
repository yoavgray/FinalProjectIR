package com.bgu.project.finalprojectir;

import android.app.FragmentManager;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bgu.project.finalprojectir.classes.Arduino;
import com.bgu.project.finalprojectir.classes.ArduinoItemAdapter;
import com.bgu.project.finalprojectir.classes.DeviceType;
import com.bgu.project.finalprojectir.fragments.AddArduinoFragment;
import com.bgu.project.finalprojectir.fragments.TVRemoteFragment;
import com.bgu.project.finalprojectir.tasks.RestActionTask;

import java.util.ArrayList;
import java.util.List;


public class Main extends ActionBarActivity {
    private static final int RESULT_SETTINGS = 1;
    private static final int RESULT_ADD_ARDUINO = 2;
    private static final int RESULT_EDIT_TASK = 3;
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    FragmentManager fm = getFragmentManager();

    static ArduinoItemAdapter adapter;
    static List<Arduino> itemDataList;
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

        Intent starterIntent = getIntent();
        String starter = starterIntent.getStringExtra("starter");
        if (starter != null) {
            Log.d("Main", starter);
            if (starter.equals("watchTv")) {
                CharSequence cs = getMessageText(starterIntent);
                new RestActionTask("Tag","action",Utils.getUriForAction("192.168.1.13:8080/rest","power", DeviceType.TV,"LG")).execute();
            } else if (starter.equals("watchAc")) {
                CharSequence cs = getMessageText(starterIntent);
            } else {
                Toast.makeText(this, "fresh start", Toast.LENGTH_SHORT).show();
            }
        }

        // Adding notifications - Eventually for the Watch
        int notificationId = 1;
        // Build intent for notification content
        Intent watchTvIntent = new Intent(this, Main.class).putExtra("starter", "watchTv").setAction("Blah");
        Intent watchAcIntent = new Intent(this, Main.class).putExtra("starter", "watchAc").setAction("Blah");
        PendingIntent controlTvPendingIntent =
                PendingIntent.getActivity(this, 0, watchTvIntent, 0);
        PendingIntent controlAcPendingIntent =
                PendingIntent.getActivity(this, 0, watchAcIntent, 0);

        String tvLabel = "Control TV";
        String acLabel = "Control AC";
        String[] tvChoices = getResources().getStringArray(R.array.choices_tv);
        String[] acChoices = getResources().getStringArray(R.array.choices_ac);

        RemoteInput remoteInputTv = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(tvLabel)
                .setChoices(tvChoices)
                .build();

        RemoteInput remoteInputAc = new RemoteInput.Builder(EXTRA_VOICE_REPLY)
                .setLabel(acLabel)
                .setChoices(acChoices)
                .build();

        // Create the action for when pressing on Control TV
        NotificationCompat.Action actionTv =
                new NotificationCompat.Action.Builder(R.drawable.tv_icon,
                        "Control TV", controlTvPendingIntent)
                        .addRemoteInput(remoteInputTv)
                        .build();

        // Create the action for when pressing on Control TV
        NotificationCompat.Action actionAc =
                new NotificationCompat.Action.Builder(R.drawable.ac_icon,
                        "Control AC", controlAcPendingIntent)
                        .addRemoteInput(remoteInputAc)
                        .build();

        Notification notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(), R.drawable.ic_launcher))
                        .setContentTitle("Arduino Detected!")
                        .setContentText("Gray's Arduino")
                        .extend(new WearableExtender().addAction(actionTv).addAction(actionAc))
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
            case R.id.edit_tasks:
                startActivityForResult(new Intent(this, EditTasksActivity.class),
                        RESULT_EDIT_TASK);
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
                //SharedPreferences sharedPrefs = PreferenceManager
                //        .getDefaultSharedPreferences(this);
                //mIsPtt = sharedPrefs.getBoolean("ptt_checkbox", true);
                break;
        }
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(EXTRA_VOICE_REPLY);
        }
        return null;
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
                        itemDataList.add(new Arduino(R.drawable.bgu_logo, name, ip));
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case RESULT_EDIT_TASK:
                    //TODO Boaz: add or remove a task with asyncTask
                    break;
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            itemDataList = new ArrayList<>();
            itemDataList.add(new Arduino(R.drawable.boaz_icon, "Buzi's Arduino", "192.168.1.13:8080/rest"));
            itemDataList.add(new Arduino(R.drawable.yoav_icon, "Gray's Arduino", "127.0.0.2"));
            itemDataList.add(new Arduino(R.drawable.asi_icon, "Asi's Arduino", "127.0.0.3"));
            itemDataList.add(new Arduino(R.drawable.omri_icon, "Havivian's Arduino", "127.0.0.4"));

            adapter = new ArduinoItemAdapter(getActivity(),
                    R.layout.list_item_row, itemDataList);

            final ListView listView = (ListView) rootView
                    .findViewById(R.id.devicesListView);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View myView, int pos,
                                        long arg3) {
                    Intent intent = new Intent(getActivity(),
                            DeviceActivity.class)
                            .putExtra("deviceName", itemDataList.get(pos).getTitle())
                            .putExtra("deviceIp", itemDataList.get(pos).getIp());
                    startActivity(intent);

                }
            });

            return rootView;
        }
    }

}
