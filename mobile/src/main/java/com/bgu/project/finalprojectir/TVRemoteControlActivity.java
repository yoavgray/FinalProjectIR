package com.bgu.project.finalprojectir;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageButton;
import android.widget.Toast;


public class TVRemoteControlActivity extends ActionBarActivity implements View.OnClickListener {
    static private ImageButton ibZero, ibOne, ibTwo, ibThree, ibFour, ibFive, ibSix, ibSeven, ibEight, ibNine;
    static private ImageButton ibPower, ibMute, ibChannelUp, ibChannelDown,ibVolumeUp, ibVolumeDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvremote_control);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tvremote_control, menu);
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

    @Override
    //TODO: this function decides what happens when buttons are clicked. instead of Toast.makeText()...
    //TODO: you need to plug in a message to asi and omri (look at case R.id.no1ImageButton: for example)

    public void onClick(View v) {
        Log.d("kaki","" + v.getId());
        switch (v.getId()) {
            case R.id.no0ImageButton:
                //FlowCloud.message("buttonZero").Send();    :)
                Toast.makeText(TVRemoteControlActivity.this,"Sending 0's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no1ImageButton:
                Log.d("kaki","button 0 was pressed");
                Toast.makeText(this,"Sending 1's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no2ImageButton:
                Toast.makeText(this,"Sending 2's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no3ImageButton:
                Toast.makeText(this,"Sending 3's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no4ImageButton:
                Toast.makeText(this,"Sending 4's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no5ImageButton:
                Toast.makeText(this,"Sending 5's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no6ImageButton:
                Toast.makeText(this,"Sending 6's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no7ImageButton:
                Toast.makeText(this,"Sending 7's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no8ImageButton:
                Toast.makeText(this,"Sending 8's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no9ImageButton:
                Toast.makeText(this,"Sending 9's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.volumeUpImageButton:
                Toast.makeText(this,"Sending volume up frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.volumeDownImageButton:
                Toast.makeText(this,"Sending volume down frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.channelUpImageButton:
                Toast.makeText(this,"Sending channel up frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.channelDownImageButton:
                Toast.makeText(this,"Sending channel down frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.muteImageButton:
                Toast.makeText(this,"Sending mute's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.powerImageButton:
                Toast.makeText(this,"Sending power's frequency to IR",Toast.LENGTH_SHORT).show();
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tvremote_control, container, false);
            ibZero = (ImageButton)rootView.findViewById(R.id.no0ImageButton);
            ibOne = (ImageButton)rootView.findViewById(R.id.no1ImageButton);
            ibTwo = (ImageButton)rootView.findViewById(R.id.no2ImageButton);
            ibThree = (ImageButton)rootView.findViewById(R.id.no3ImageButton);
            ibFour = (ImageButton)rootView.findViewById(R.id.no4ImageButton);
            ibFive = (ImageButton)rootView.findViewById(R.id.no5ImageButton);
            ibSix = (ImageButton)rootView.findViewById(R.id.no6ImageButton);
            ibSeven = (ImageButton)rootView.findViewById(R.id.no7ImageButton);
            ibEight = (ImageButton)rootView.findViewById(R.id.no8ImageButton);
            ibNine = (ImageButton)rootView.findViewById(R.id.no9ImageButton);
            ibVolumeUp = (ImageButton)rootView.findViewById(R.id.volumeUpImageButton);
            ibVolumeDown = (ImageButton)rootView.findViewById(R.id.volumeDownImageButton);
            ibChannelUp = (ImageButton)rootView.findViewById(R.id.channelUpImageButton);
            ibChannelDown = (ImageButton)rootView.findViewById(R.id.channelDownImageButton);
            ibPower = (ImageButton)rootView.findViewById(R.id.powerImageButton);
            ibMute = (ImageButton)rootView.findViewById(R.id.muteImageButton);

            return rootView;
        }

    }
}
