package com.bgu.project.finalprojectir.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.classes.ParseArduino;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class AddArduinoFragment extends DialogFragment {
    private static Button okButton, cancelButton;
    private static EditText deviceNameEditText, deviceIpEditText;
    final static int RESULT_OK = -1, RESULT_CANCELED = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_arduino, container,
                false);
        getDialog().setTitle("Add an Arduino to user!");
        // Do something else

        okButton = (Button) rootView.findViewById(R.id.addArduinoOkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ParseArduino arduino = new ParseArduino();
                arduino.setUser(ParseUser.getCurrentUser());
                arduino.setTitle(deviceNameEditText.getText().toString());
                arduino.setIp(deviceIpEditText.getText().toString());
                arduino.setIcon("" + R.drawable.bgu_logo);
                // Save the meal and return
                arduino.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            getTargetFragment().onActivityResult(
                                    getTargetRequestCode(), RESULT_OK, new Intent());
                            dismiss();
                        } else {
                            Log.d("AddArduino","Error saving: " + e.getMessage());
                        }
                    }
                });

                getTargetFragment().onActivityResult(
                        getTargetRequestCode(), RESULT_OK, intent);
                dismiss();
            }
        });
        cancelButton = (Button) rootView.findViewById(R.id.addArduinoCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTargetFragment().onActivityResult(
                        getTargetRequestCode(), RESULT_CANCELED, null);
                dismiss();
            }
        });
        deviceNameEditText = (EditText) rootView.findViewById(R.id.addArduinoNameEditText);
        deviceIpEditText = (EditText) rootView.findViewById(R.id.addArduinoIpEditText);

        //TODO Make sure that you cant press the OK button until you have valid parameters
        return rootView;
    }
}
