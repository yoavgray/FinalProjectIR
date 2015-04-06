package com.bgu.project.finalprojectir.fragments;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.classes.DeviceType;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {
    private static Button okButton, cancelButton;
    private static RadioButton timeRadioButton, locationRadioButton;
    final static int RESULT_OK = -1, RESULT_CANCELED = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_task, container,
                false);
        getDialog().setTitle("Add a task to device!");
        // Do something else

        okButton = (Button) rootView.findViewById(R.id.addArduinoOkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String type = timeRadioButton.isChecked() ? String.valueOf(DeviceType.TV) : String.valueOf(DeviceType.AC);
                intent.putExtra("typeResult", type);
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

        timeRadioButton = (RadioButton) rootView.findViewById(R.id.timeRadioButton);
        timeRadioButton.performClick();
        timeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
            }
        });

        locationRadioButton = (RadioButton) rootView.findViewById(R.id.locationRadioButton);
        locationRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
            }
        });

        //TODO Make sure that you cant press the OK button until you have valid parameters
        return rootView;
    }

}
