package com.bgu.project.finalprojectir.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.classes.DeviceType;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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
                new TVButton("zero","0").execute();
                break;
            case R.id.no1ImageButton:
                new TVButton("one","1").execute();
                break;
            case R.id.no2ImageButton:
                new TVButton("two","2").execute();
                break;
            case R.id.no3ImageButton:
                new TVButton("three","3").execute();
                break;
            case R.id.no4ImageButton:
                new TVButton("four","4").execute();
                break;
            case R.id.no5ImageButton:
                new TVButton("five","5").execute();
                break;
            case R.id.no6ImageButton:
                new TVButton("six","6").execute();
                break;
            case R.id.no7ImageButton:
                new TVButton("seven","7").execute();
                break;
            case R.id.no8ImageButton:
                new TVButton("eight","8").execute();
                break;
            case R.id.no9ImageButton:
                new TVButton("nine","9").execute();
                break;
            case R.id.volumeUpImageButton:
                new TVButton("vup","volume up").execute();
                break;
            case R.id.volumeDownImageButton:
                new TVButton("vdown","volume down").execute();
                break;
            case R.id.channelUpImageButton:
                new TVButton("cup","channel up").execute();
                break;
            case R.id.channelDownImageButton:
                new TVButton("cdown","channel down").execute();
                break;
            case R.id.muteImageButton:
                new TVButton("mute","mute").execute();
                break;
            case R.id.powerImageButton:
                new TVButton("power","power").execute();
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

    private class TVButton extends AsyncTask<Void, Void, String> {

        private String action;
        private String description;

        public TVButton(String action,String description) {
            this.action = action;
            this.description = description;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                URI uri = new URI("http://"+ip+"/"+DeviceType.TV+"/"+brand+"/"+action);
                String responseEntity= null;
                if(useREST) {
                    responseEntity = restTemplate.getForObject(uri, String.class);
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
            Log.d(TAG, "button " + description + " was pressed");
            if(useREST) {
                Toast.makeText(getActivity(), "Response "+infoFromArduino, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Sending " + description + " frequency to IR", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
