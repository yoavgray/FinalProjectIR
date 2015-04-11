package com.bgu.project.finalprojectir.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
public class TVRemoteFragment extends Fragment implements View.OnClickListener {
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
                new TVButtonAction("zero","0").execute();
                break;
            case R.id.no1ImageButton:
                new TVButtonAction("one","1").execute();
                break;
            case R.id.no2ImageButton:
                new TVButtonAction("two","2").execute();
                break;
            case R.id.no3ImageButton:
                new TVButtonAction("three","3").execute();
                break;
            case R.id.no4ImageButton:
                new TVButtonAction("four","4").execute();
                break;
            case R.id.no5ImageButton:
                new TVButtonAction("five","5").execute();
                break;
            case R.id.no6ImageButton:
                new TVButtonAction("six","6").execute();
                break;
            case R.id.no7ImageButton:
                new TVButtonAction("seven","7").execute();
                break;
            case R.id.no8ImageButton:
                new TVButtonAction("eight","8").execute();
                break;
            case R.id.no9ImageButton:
                new TVButtonAction("nine","9").execute();
                break;
            case R.id.volumeUpImageButton:
                new TVButtonAction("vup","volume up").execute();
                break;
            case R.id.volumeDownImageButton:
                new TVButtonAction("vdown","volume down").execute();
                break;
            case R.id.channelUpImageButton:
                new TVButtonAction("cup","channel up").execute();
                break;
            case R.id.channelDownImageButton:
                new TVButtonAction("cdown","channel down").execute();
                break;
            case R.id.muteImageButton:
                new TVButtonAction("mute","mute").execute();
                break;
            case R.id.powerImageButton:
                new TVButtonAction("power","power").execute();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tvremote_control, container, false);
        ip = getArguments().getString("ip");
        brand = getArguments().getString("brand");
        ibZero = (ImageButton)rootView.findViewById(R.id.no0ImageButton);
        ibZero.setOnClickListener(this);
        ibOne = (ImageButton)rootView.findViewById(R.id.no1ImageButton);
        ibOne.setOnClickListener(this);
        ibTwo = (ImageButton)rootView.findViewById(R.id.no2ImageButton);
        ibTwo.setOnClickListener(this);
        ibThree = (ImageButton)rootView.findViewById(R.id.no3ImageButton);
        ibThree.setOnClickListener(this);
        ibFour = (ImageButton)rootView.findViewById(R.id.no4ImageButton);
        ibFour.setOnClickListener(this);
        ibFive = (ImageButton)rootView.findViewById(R.id.no5ImageButton);
        ibFive.setOnClickListener(this);
        ibSix = (ImageButton)rootView.findViewById(R.id.no6ImageButton);
        ibSix.setOnClickListener(this);
        ibSeven = (ImageButton)rootView.findViewById(R.id.no7ImageButton);
        ibSeven.setOnClickListener(this);
        ibEight = (ImageButton)rootView.findViewById(R.id.no8ImageButton);
        ibEight.setOnClickListener(this);
        ibNine = (ImageButton)rootView.findViewById(R.id.no9ImageButton);
        ibNine.setOnClickListener(this);
        ibVolumeUp = (ImageButton)rootView.findViewById(R.id.volumeUpImageButton);
        ibVolumeUp.setOnClickListener(this);
        ibVolumeDown = (ImageButton)rootView.findViewById(R.id.volumeDownImageButton);
        ibVolumeDown.setOnClickListener(this);
        ibChannelUp = (ImageButton)rootView.findViewById(R.id.channelUpImageButton);
        ibChannelUp.setOnClickListener(this);
        ibChannelDown = (ImageButton)rootView.findViewById(R.id.channelDownImageButton);
        ibChannelDown.setOnClickListener(this);
        ibPower = (ImageButton)rootView.findViewById(R.id.powerImageButton);
        ibPower.setOnClickListener(this);
        ibMute = (ImageButton)rootView.findViewById(R.id.muteImageButton);
        ibMute.setOnClickListener(this);

        return rootView;
    }

    private class TVButtonAction extends RestActionTask{

        private String description;

        public TVButtonAction(String action, String description) {
            super(TAG,description, Utils.getUriForAction(ip,action,DeviceType.TV,brand));
            this.description = description;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                String responseEntity;
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
