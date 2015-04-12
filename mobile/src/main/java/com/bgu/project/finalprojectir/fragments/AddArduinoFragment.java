package com.bgu.project.finalprojectir.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bgu.project.finalprojectir.R;

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
                System.out.println("1");
                intent.putExtra("nameResult", deviceNameEditText.getText().toString());
                intent.putExtra("ipResult", deviceIpEditText.getText().toString());
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
