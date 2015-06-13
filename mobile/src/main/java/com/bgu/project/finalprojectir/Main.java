package com.bgu.project.finalprojectir;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bgu.project.finalprojectir.classes.ArduinoParseAdapter;
import com.bgu.project.finalprojectir.classes.DeviceType;
import com.bgu.project.finalprojectir.classes.ParseArduino;
import com.bgu.project.finalprojectir.fragments.AddArduinoFragment;
import com.bgu.project.finalprojectir.tasks.RestActionTask;
import com.parse.ParseUser;

public class Main extends ActionBarActivity {
    private static final int RESULT_SETTINGS = 1;
    private static final int RESULT_ADD_ARDUINO = 2;
    private static final int RESULT_EDIT_TASK = 3;
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    FragmentManager fm = getFragmentManager();

    static ArduinoParseAdapter adapter;
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
                String cs = getMessageText(starterIntent).toString().toLowerCase();
                ReceiveActionFromWatch(DeviceType.TV, cs);
            } else if (starter.equals("watchAc")) {
                String cs = getMessageText(starterIntent).toString().toLowerCase();
                ReceiveActionFromWatch(DeviceType.TV, cs);
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

    private void ReceiveActionFromWatch(DeviceType deviceType, String action) {

        switch (deviceType) {
            case AC:
                break;
            case TV:
                if (action.equals("power") || action.equals("turn tv on") || action.equals("turn tv off") || action.equals("turn television on") || action.equals("turn television off")) {
                    new RestActionTask("Watch", "power", Utils.getUriForAction("132.72.48.46", "w", DeviceType.TV, "LG")).execute();
                    Toast.makeText(this, "Received 'Power' command from watch!", Toast.LENGTH_SHORT).show();
                } else if (action.equals("turn volume up") || action.equals("turn the volume up") || action.equals("volume up") || action.equals("turn up the volume")) {
                    new RestActionTask("Watch", "volume up", Utils.getUriForAction("132.72.48.46", "u", DeviceType.TV, "LG")).execute();
                    Toast.makeText(this, "Received 'Volume Up' command from watch!", Toast.LENGTH_SHORT).show();
                } else if (action.equals("turn volume down") || action.equals("turn the volume down") || action.equals("volume down") || action.equals("turn down the volume")) {
                    new RestActionTask("Watch", "volume down", Utils.getUriForAction("132.72.48.46", "d", DeviceType.TV, "LG")).execute();
                    Toast.makeText(this, "Received 'Volume Down' command from watch!", Toast.LENGTH_SHORT).show();
                } else if (action.equals("channel up")) {
                    new RestActionTask("Watch", "channel up", Utils.getUriForAction("132.72.48.46", "p", DeviceType.TV, "LG")).execute();
                    Toast.makeText(this, "Received 'Channel Up' command from watch!", Toast.LENGTH_SHORT).show();
                } else if (action.equals("channel down")) {
                    new RestActionTask("Watch", "channel down", Utils.getUriForAction("132.72.48.46", "n", DeviceType.TV, "LG")).execute();
                    Toast.makeText(this, "Received 'Channel Down' command from watch!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
            case R.id.action_logout:
                ParseUser.logOut();
                Intent i = new Intent(this, LoginSignupActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
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
        Log.d("msg", "" + requestCode);
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
                        adapter.loadObjects();
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

            String userName = ParseUser.getCurrentUser().getUsername();
            TextView helloUserTextView = (TextView) rootView.findViewById(R.id.helloUserTextView);
            helloUserTextView.setText("Hello " + userName + ", please choose an Arduino to controll");

            adapter = new ArduinoParseAdapter(getActivity());

            final ListView listView = (ListView) rootView
                    .findViewById(R.id.devicesListView);
            listView.setAdapter(adapter);
            registerForContextMenu(listView);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> arg0, View myView, int pos,
                                        long arg3) {
                    Intent intent = new Intent(getActivity(),
                            DeviceActivity.class)
                            .putExtra("deviceName", adapter.getItem(pos).getTitle())
                            .putExtra("deviceIp", adapter.getItem(pos).getIp());
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu_main_context, menu);
    }

    @Override
    //What happens when you press long on a list item
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        switch (item.getItemId()) {
            case R.id.remove_arduino:
                Log.d("Hey!", "Remove Device!");
                new AlertDialog.Builder(this)
                .setTitle("Warning")
                    .setMessage("Are you sure you want to remove arduino?")
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    ParseArduino arduinoToDelete = adapter.getItem(info.position);
                                    arduinoToDelete.deleteInBackground();
                                    adapter.loadObjects();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    Toast.makeText(getApplicationContext(),
                                            "Coward..",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
