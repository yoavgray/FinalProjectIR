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

/**
 * A simple {@link Fragment} subclass.
 */
public class TVRemoteFragment extends Fragment implements View.OnClickListener {
    static private ImageButton ibZero, ibOne, ibTwo, ibThree, ibFour, ibFive, ibSix, ibSeven, ibEight, ibNine;
    static private ImageButton ibPower, ibMute, ibChannelUp, ibChannelDown,ibVolumeUp, ibVolumeDown;

    public TVRemoteFragment() {
        // Required empty public constructor
    }

    //TODO: BOAZ: this function decides what happens when buttons are clicked. instead of Toast.makeText()...
    //TODO: you need to plug in a message to asi and omri (look at case R.id.no1ImageButton: for example)

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.no0ImageButton:
                //FlowCloud.message("buttonZero").Send();    :)
                Toast.makeText(getActivity(), "Sending 0's frequency to IR", Toast.LENGTH_SHORT).show();
                break;
            case R.id.no1ImageButton:
                Log.d("kaki", "button 0 was pressed");
                Toast.makeText(getActivity(),"Sending 1's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no2ImageButton:
                Toast.makeText(getActivity(),"Sending 2's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no3ImageButton:
                Toast.makeText(getActivity(),"Sending 3's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no4ImageButton:
                Toast.makeText(getActivity(),"Sending 4's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no5ImageButton:
                Toast.makeText(getActivity(),"Sending 5's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no6ImageButton:
                Toast.makeText(getActivity(),"Sending 6's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no7ImageButton:
                Toast.makeText(getActivity(),"Sending 7's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no8ImageButton:
                Toast.makeText(getActivity(),"Sending 8's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.no9ImageButton:
                Toast.makeText(getActivity(),"Sending 9's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.volumeUpImageButton:
                Toast.makeText(getActivity(),"Sending volume up frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.volumeDownImageButton:
                Toast.makeText(getActivity(),"Sending volume down frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.channelUpImageButton:
                Toast.makeText(getActivity(),"Sending channel up frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.channelDownImageButton:
                Toast.makeText(getActivity(),"Sending channel down frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.muteImageButton:
                Toast.makeText(getActivity(),"Sending mute's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
            case R.id.powerImageButton:
                Toast.makeText(getActivity(),"Sending power's frequency to IR",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tvremote_control, container, false);
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
}
