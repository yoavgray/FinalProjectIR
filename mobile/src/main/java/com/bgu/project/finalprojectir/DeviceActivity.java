package com.bgu.project.finalprojectir;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.bgu.project.finalprojectir.fragments.*;

public class DeviceActivity extends ActionBarActivity  {
    private static Intent incomingIntent;
    private static String title;
    private static String deviceIp;
    static DeviceFragment deviceFragment;
    FragmentManager fm = getFragmentManager();

    private static final int RESULT_ADD_DEVICE = 1;
    private static final int RESULT_EDIT_TASK = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device);
        deviceFragment = new DeviceFragment();
        if (savedInstanceState == null) {

            incomingIntent = getIntent();
            title = incomingIntent.getStringExtra("deviceName");
            setTitle(title);

            ((TextView) findViewById(R.id.deviceIpTextView)).setText(incomingIntent.getStringExtra("deviceIp"));
            deviceIp = incomingIntent.getStringExtra("deviceIp");
            Bundle bundle=new Bundle();
            bundle.putString("ip",deviceIp);
            deviceFragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, deviceFragment)
                    .commit();
        }else{
            ((TextView) findViewById(R.id.deviceIpTextView)).setText(incomingIntent.getStringExtra("deviceIp"));
        }

    }

    /**
     * When handling fragments, this overridden method makes sure that the backstack handles the
     * back button press
     */
    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
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
        switch(item.getItemId()) {
            case R.id.add_device:
                AddDeviceFragment adFragment = new AddDeviceFragment();
                // Show DialogFragment
                adFragment.setTargetFragment(deviceFragment, RESULT_ADD_DEVICE);
                adFragment.show(fm, "Dialog Fragment");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
