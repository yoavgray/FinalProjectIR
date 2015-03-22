package com.bgu.project.finalprojectir.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bgu.project.finalprojectir.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ACRemoteFragment extends Fragment {


    public ACRemoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_acremote_control, container, false);
        return rootView;
    }


}
