package com.bgu.project.finalprojectir.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bgu.project.finalprojectir.R;

public class AddDeviceFragment extends DialogFragment {
    private static TextView mutagTitle;
    private static Button okButton, cancelButton;
    private static RadioButton acRadioButton, tvRadioButton;
    private static Spinner spinner;
    final static int RESULT_OK = -1, RESULT_CANCELED = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_device, container,
                false);
        getDialog().setTitle("Add a device to Arduino!");
        // Do something else

        spinner = (Spinner) rootView.findViewById(R.id.addDeviceSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> tvAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.tv_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        tvAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(tvAdapter);

        final ArrayAdapter<CharSequence> acAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.ac_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        acAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        okButton = (Button) rootView.findViewById(R.id.addArduinoOkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String type = tvRadioButton.isChecked() ? "Television" : "A/C Unit";
                String brand = spinner.getSelectedItem().toString();
                intent.putExtra("typeResult", type).putExtra("brandResult", brand);
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

        mutagTitle = (TextView) rootView.findViewById(R.id.textViewMutag);
        mutagTitle.setText("Choose TV brand:");
        tvRadioButton = (RadioButton) rootView.findViewById(R.id.tvRadioButton);
        tvRadioButton.performClick();
        tvRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mutagTitle.setText("Choose TV brand:");
                spinner.setAdapter(tvAdapter);
            }
        });

        acRadioButton = (RadioButton) rootView.findViewById(R.id.acRadioButton);
        acRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mutagTitle.setText("Choose AC brand:");
                spinner.setAdapter(acAdapter);
            }
        });

        //TODO Make sure that you cant press the OK button until you have valid parameters
        return rootView;
    }


}
