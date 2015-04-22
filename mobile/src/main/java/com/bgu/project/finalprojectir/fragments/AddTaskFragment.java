package com.bgu.project.finalprojectir.fragments;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.bgu.project.finalprojectir.MapsActivity;
import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.RestJobService;
import com.bgu.project.finalprojectir.classes.TaskType;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment{
    private static Button okButton, cancelButton;
    private static RadioButton timeRadioButton, locationRadioButton;
    final static int RESULT_OK = -1, RESULT_CANCELED = 0;
    private static int jobID = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_task, container,
                false);
        getDialog().setTitle("Add a task to device!");

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.taskContainer, new TimeTaskFragment());
        ft.commit();

        okButton = (Button) rootView.findViewById(R.id.addArduinoOkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                String type = timeRadioButton.isChecked() ? String.valueOf(TaskType.TIME) : String.valueOf(TaskType.LOCATION);
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
                //TODO Yoav - get all the parameters for sampleForNewTimeTask method below
            }
        });

        locationRadioButton = (RadioButton) rootView.findViewById(R.id.locationRadioButton);
        locationRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), MapsActivity.class);
                startActivityForResult(in, 1);
            }
        });

        //TODO Make sure that you cant press the OK button until you have valid parameters
        return rootView;
    }

    //TODO Yoav - connect this to the GUI dialog (Tine based Task)
    //All the parameters are string because they suppose to arrive from text fields (url is including the device and the action).
    private void sampleForNewTimeTask(String url, String description, String delayInMinutes, String deadlineInMinutes){
        ComponentName serviceName = new ComponentName(getActivity(), RestJobService.class);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("description",description);
        persistableBundle.putString("url",url);
        JobInfo jobInfo = new JobInfo.Builder(jobID++, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setExtras(persistableBundle)
                .setMinimumLatency(Long.valueOf(delayInMinutes) * 1000 * 60)
                .setOverrideDeadline(Long.valueOf(deadlineInMinutes) * 1000 * 60)
                .setPersisted(true)
                .build();
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS){
            Log.d("AddTask", "Job scheduled successfully!");
        }
    }

}
