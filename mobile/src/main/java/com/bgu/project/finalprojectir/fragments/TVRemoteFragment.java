package com.bgu.project.finalprojectir.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.Utils;
import com.bgu.project.finalprojectir.classes.DeviceType;
import com.bgu.project.finalprojectir.tasks.RestActionTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVRemoteFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    public static final String TAG = "TV Remote";
    static private ImageButton ibZero, ibOne, ibTwo, ibThree, ibFour, ibFive, ibSix, ibSeven, ibEight, ibNine;
    static private ImageButton ibPower, ibMute, ibChannelUp, ibChannelDown,ibVolumeUp, ibVolumeDown;
    static String ip;
    static String brand;
    static final boolean useREST = DeviceFragment.useREST;

    public TVRemoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no0ImageButton:
                new TVButtonAction("0", "0").execute();
                break;
            case R.id.no1ImageButton:
                new TVButtonAction("1", "1").execute();
                break;
            case R.id.no2ImageButton:
                new TVButtonAction("2", "2").execute();
                break;
            case R.id.no3ImageButton:
                new TVButtonAction("3", "3").execute();
                break;
            case R.id.no4ImageButton:
                new TVButtonAction("4", "4").execute();
                break;
            case R.id.no5ImageButton:
                new TVButtonAction("5", "5").execute();
                break;
            case R.id.no6ImageButton:
                new TVButtonAction("6", "6").execute();
                break;
            case R.id.no7ImageButton:
                new TVButtonAction("7", "7").execute();
                break;
            case R.id.no8ImageButton:
                new TVButtonAction("8", "8").execute();
                break;
            case R.id.no9ImageButton:
                new TVButtonAction("9", "9").execute();
                break;
            case R.id.volumeUpImageButton:
                new TVButtonAction("u", "volume up").execute();
                break;
            case R.id.volumeDownImageButton:
                new TVButtonAction("d", "volume down").execute();
                break;
            case R.id.channelUpImageButton:
                new TVButtonAction("p", "channel up").execute();
                break;
            case R.id.channelDownImageButton:
                new TVButtonAction("n", "channel down").execute();
                break;
            case R.id.muteImageButton:
                new TVButtonAction("m", "mute").execute();
                break;
            case R.id.powerImageButton:
                new TVButtonAction("w", "power").execute();
                break;
        }
    }

    @Override
    public boolean onLongClick (View v) {
        String url = null;
        String description = null;
        switch (v.getId()) {
            case R.id.no0ImageButton:
                url = Utils.getUriForAction(ip,"0",DeviceType.TV,brand).toString();
                description = "0";
                break;
            case R.id.no1ImageButton:
                url = Utils.getUriForAction(ip,"1",DeviceType.TV,brand).toString();
                description = "1";
                break;
            case R.id.no2ImageButton:
                url = Utils.getUriForAction(ip,"2",DeviceType.TV,brand).toString();
                description = "2";
                break;
            case R.id.no3ImageButton:
                url = Utils.getUriForAction(ip,"3",DeviceType.TV,brand).toString();
                description = "3";
                break;
            case R.id.no4ImageButton:
                url = Utils.getUriForAction(ip,"4",DeviceType.TV,brand).toString();
                description = "4";
                break;
            case R.id.no5ImageButton:
                url = Utils.getUriForAction(ip,"5",DeviceType.TV,brand).toString();
                description = "5";
                break;
            case R.id.no6ImageButton:
                url = Utils.getUriForAction(ip,"6",DeviceType.TV,brand).toString();
                description = "6";
                break;
            case R.id.no7ImageButton:
                url = Utils.getUriForAction(ip,"7",DeviceType.TV,brand).toString();
                description = "7";
                break;
            case R.id.no8ImageButton:
                url = Utils.getUriForAction(ip,"8",DeviceType.TV,brand).toString();
                description = "8";
                break;
            case R.id.no9ImageButton:
                url = Utils.getUriForAction(ip,"9",DeviceType.TV,brand).toString();
                description = "9";
                break;
            case R.id.volumeUpImageButton:
                url = Utils.getUriForAction(ip,"u",DeviceType.TV,brand).toString();
                description = "volume up";
                break;
            case R.id.volumeDownImageButton:
                url = Utils.getUriForAction(ip,"d",DeviceType.TV,brand).toString();
                description = "volume down";
                break;
            case R.id.channelUpImageButton:
                url = Utils.getUriForAction(ip,"p",DeviceType.TV,brand).toString();
                description = "channel up";
                break;
            case R.id.channelDownImageButton:
                url = Utils.getUriForAction(ip,"n",DeviceType.TV,brand).toString();
                description = "channel down";
                break;
            case R.id.muteImageButton:
                url = Utils.getUriForAction(ip,"m",DeviceType.TV,brand).toString();
                description = "mute";
                break;
            case R.id.powerImageButton:
                url = Utils.getUriForAction(ip,"w",DeviceType.TV,brand).toString();
                description = "power";
                break;
        }
        DialogFragment newFragment = AddTaskFragment.newInstance(url, description, DeviceType.TV);
        newFragment.show(getFragmentManager(), "dialog");
        return true;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_tvremote_control, container, false);
        setHasOptionsMenu(true);
        ip = getArguments().getString("ip");
        brand = getArguments().getString("brand");
        ibZero = (ImageButton) rootView.findViewById(R.id.no0ImageButton);
        ibZero.setOnClickListener(this);
        ibZero.setOnLongClickListener(this);
        ibOne = (ImageButton) rootView.findViewById(R.id.no1ImageButton);
        ibOne.setOnClickListener(this);
        ibOne.setOnLongClickListener(this);
        ibTwo = (ImageButton) rootView.findViewById(R.id.no2ImageButton);
        ibTwo.setOnClickListener(this);
        ibTwo.setOnLongClickListener(this);
        ibThree = (ImageButton) rootView.findViewById(R.id.no3ImageButton);
        ibThree.setOnClickListener(this);
        ibThree.setOnLongClickListener(this);
        ibFour = (ImageButton) rootView.findViewById(R.id.no4ImageButton);
        ibFour.setOnClickListener(this);
        ibFour.setOnLongClickListener(this);
        ibFive = (ImageButton) rootView.findViewById(R.id.no5ImageButton);
        ibFive.setOnClickListener(this);
        ibFive.setOnLongClickListener(this);
        ibSix = (ImageButton) rootView.findViewById(R.id.no6ImageButton);
        ibSix.setOnClickListener(this);
        ibSix.setOnLongClickListener(this);
        ibSeven = (ImageButton) rootView.findViewById(R.id.no7ImageButton);
        ibSeven.setOnClickListener(this);
        ibSeven.setOnLongClickListener(this);
        ibEight = (ImageButton) rootView.findViewById(R.id.no8ImageButton);
        ibEight.setOnClickListener(this);
        ibEight.setOnLongClickListener(this);
        ibNine = (ImageButton) rootView.findViewById(R.id.no9ImageButton);
        ibNine.setOnClickListener(this);
        ibNine.setOnLongClickListener(this);
        ibVolumeUp = (ImageButton) rootView.findViewById(R.id.volumeUpImageButton);
        ibVolumeUp.setOnClickListener(this);
        ibVolumeUp.setOnLongClickListener(this);
        ibVolumeDown = (ImageButton) rootView.findViewById(R.id.volumeDownImageButton);
        ibVolumeDown.setOnClickListener(this);
        ibVolumeDown.setOnLongClickListener(this);
        ibChannelUp = (ImageButton) rootView.findViewById(R.id.channelUpImageButton);
        ibChannelUp.setOnClickListener(this);
        ibChannelUp.setOnLongClickListener(this);
        ibChannelDown = (ImageButton) rootView.findViewById(R.id.channelDownImageButton);
        ibChannelDown.setOnClickListener(this);
        ibChannelDown.setOnLongClickListener(this);
        ibPower = (ImageButton) rootView.findViewById(R.id.powerImageButton);
        ibPower.setOnClickListener(this);
        ibPower.setOnLongClickListener(this);
        ibMute = (ImageButton) rootView.findViewById(R.id.muteImageButton);
        ibMute.setOnClickListener(this);
        ibMute.setOnLongClickListener(this);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setVisible(false);
    }

    public class TVButtonAction extends RestActionTask {

        private String description;

        public TVButtonAction(String action, String description) {
            super(TAG,description, Utils.getUriForAction(ip,action,DeviceType.TV,brand));
            this.description = description;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String responseEntity = null;
                if(useREST) {
                    responseEntity = super.doInBackground();
                }
                return responseEntity;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }



        @Override
        protected void onPostExecute(String infoFromArduino) {
            super.onPostExecute(infoFromArduino);
            if(useREST) {
                Toast.makeText(getActivity(), "Response "+infoFromArduino, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Sending " + description + " frequency to IR", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
