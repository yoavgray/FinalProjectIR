package com.bgu.project.finalprojectir.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.bgu.project.finalprojectir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationTaskFragment extends Fragment {
    private static EditText radiusEditText;
    private static RadioButton enteringRadioButton, leavingRadioButton;

    public LocationTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_location_task, container, false);
        radiusEditText = (EditText) rootView.findViewById(R.id.radiusEditText);
        enteringRadioButton = (RadioButton) rootView.findViewById(R.id.enteringRadioButton);
        leavingRadioButton = (RadioButton) rootView.findViewById(R.id.leavingRadioButton);
        enteringRadioButton.setChecked(true);

        return rootView;
    }


}
