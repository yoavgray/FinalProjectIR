package com.bgu.project.finalprojectir.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.Utils;
import com.bgu.project.finalprojectir.classes.DeviceType;
import com.bgu.project.finalprojectir.tasks.RestActionTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class ACRemoteFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    public static final String TAG = "AC Remote";
    private static Button setTemp;
    private static Spinner spinner;
    static private ImageButton ibPower;
    static String ip;
    static String brand;
    static final boolean useREST = DeviceFragment.useREST;

    public ACRemoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setTempButton:
                //new ACButtonAction("zero", "0").execute();
                break;
            case R.id.powerImageButton:
                new ACButtonAction("power", "power").execute();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_acremote_control, container, false);
        setHasOptionsMenu(true);

        setTemp = (Button) rootView.findViewById(R.id.setTempButton);
        setTemp.setOnClickListener(this);
        ibPower = (ImageButton) rootView.findViewById(R.id.powerImageButton);
        ibPower.setOnClickListener(this);
        ibPower.setOnLongClickListener(this);

        spinner = (Spinner) rootView.findViewById(R.id.tempSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.temperature_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.getItem(0).setVisible(false);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    private class ACButtonAction extends RestActionTask {

        private String description;

        public ACButtonAction(String action, String description) {
            super(TAG,description, Utils.getUriForAction(ip, action, DeviceType.TV, brand));
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
                Toast.makeText(getActivity(), "Response " + infoFromArduino, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Sending " + description + " frequency to IR", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
