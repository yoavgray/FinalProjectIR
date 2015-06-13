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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.bgu.project.finalprojectir.MapsActivity;
import com.bgu.project.finalprojectir.R;
import com.bgu.project.finalprojectir.RestJobService;
import com.bgu.project.finalprojectir.classes.DeviceType;
import com.bgu.project.finalprojectir.classes.ParseTask;
import com.bgu.project.finalprojectir.classes.TaskType;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.bgu.project.finalprojectir.classes.TaskType.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {
    private String url;
    private String description;
    private String deviceType;
    private static EditText taskNameEditText;
    private static Button okButton, cancelButton;
    private static RadioButton timeRadioButton, locationRadioButton;
    private static final String ARG_PARAM1 = "URL";
    private static final String ARG_PARAM2 = "Description";
    private static final String ARG_PARAM3 = "Device Type";

    private static int jobID = 0;
    private TimeTaskFragment timeTaskFragment;
    private LocationTaskFragment locationTaskFragment;

    public static AddTaskFragment newInstance(String url, String description, DeviceType deviceType) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        args.putString(ARG_PARAM2, description);
        args.putString(ARG_PARAM3, deviceType.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
            description = getArguments().getString(ARG_PARAM2);
            deviceType = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_task, container,
                false);
        getDialog().setTitle("Add a task to device!");

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        timeTaskFragment = new TimeTaskFragment();
        ft.add(R.id.taskContainer, timeTaskFragment);
        ft.commit();

        taskNameEditText = (EditText) rootView.findViewById(R.id.taskNameEditText);

        taskNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                enableOkIfReady();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        okButton = (Button) rootView.findViewById(R.id.addArduinoOkButton);
        okButton.setEnabled(false);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskType type = timeRadioButton.isChecked() ? TIME : LOCATION;
                switch (type) {
                    case TIME:
                        int chosenHour, chosenMinute, chosenDay, chosenMonth, currentHour, currentMinute, currentDay, currentMonth;
                        long delayInMinutes;
                        TimePicker tp = (TimePicker) timeTaskFragment.getView().findViewById(R.id.timePicker);
                        chosenHour = tp.getCurrentHour();
                        chosenMinute = tp.getCurrentMinute();
                        DatePicker dp = (DatePicker) timeTaskFragment.getView().findViewById(R.id.datePicker);
                        chosenMonth = dp.getMonth();
                        chosenDay = dp.getDayOfMonth();

                        Date date = new Date();   // given date
                        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
                        calendar.setTime(date);   // assigns calendar to given date
                        currentHour = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
                        currentMinute = calendar.get(Calendar.MINUTE);     // gets month number, NOTE this is zero based!
                        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                        currentMonth = calendar.get(Calendar.MONTH);
                        delayInMinutes = (chosenMonth - currentMonth) * 43829 + (chosenDay - currentDay) * 1440 + ((chosenHour - currentHour) * 60) + (chosenMinute - currentMinute);
                        String taskName = taskNameEditText.getText().toString();
                        startNewTimeTask(url,taskName,deviceType,"" + delayInMinutes);

                        ParseTask task = new ParseTask();
                        task.setUser(ParseUser.getCurrentUser());
                        task.setTitle(taskName);
                        task.setType("time");
                        task.setInfo(calendar.getTime().toString());
                        task.setIcon("" + R.drawable.time_task_icon);
                        task.saveInBackground(new SaveCallback() {

                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.d("AddTimeTask","Error saving: " + e.getMessage());
                                }
                            }
                        });

                        break;
                    case LOCATION:
                        EditText radiusEt = (EditText) locationTaskFragment.getView().findViewById(R.id.radiusEditText);
                        float radius = Float.parseFloat(radiusEt.getText().toString());
                        RadioButton enteringRb = (RadioButton) locationTaskFragment.getView().findViewById(R.id.enteringRadioButton);
                        boolean isEntering = enteringRb.isChecked();
                        taskName = taskNameEditText.getText().toString();

                        ParseTask parseTask = new ParseTask();
                        parseTask.setUser(ParseUser.getCurrentUser());
                        parseTask.setTitle(taskName);
                        parseTask.setType("location");
                        parseTask.setInfo("Radius: " + radius + " from Home");
                        parseTask.setIcon("" + R.drawable.loc_task_icon);
                        // Save the meal and return
                        parseTask.saveInBackground(new SaveCallback() {

                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.d("AddLocationTask","Error saving: " + e.getMessage());
                                }
                            }
                        });

                        Intent intent = new Intent(getActivity(), MapsActivity.class);
                        intent.putExtra("url", url)
                                .putExtra("radius", radius)
                                .putExtra("isEntering", isEntering)
                                .putExtra("taskName", taskName)
                                .putExtra("device", deviceType);
                        startActivity(intent);
                        break;
                }
                dismiss();
            }
        });
        cancelButton = (Button) rootView.findViewById(R.id.addArduinoCancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        timeRadioButton = (RadioButton) rootView.findViewById(R.id.timeRadioButton);
        timeRadioButton.performClick();
        timeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                timeTaskFragment = new TimeTaskFragment();
                ft.replace(R.id.taskContainer, timeTaskFragment);
                ft.commit();
            }
        });

        locationRadioButton = (RadioButton) rootView.findViewById(R.id.locationRadioButton);
        locationRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                locationTaskFragment = new LocationTaskFragment();
                ft.replace(R.id.taskContainer, locationTaskFragment);
                ft.commit();
            }
        });

        return rootView;
    }

    private void enableOkIfReady() {
        boolean isReady = taskNameEditText.getText().toString().length() > 0;

        if (isReady) {
            okButton.setEnabled(true);
        } else {
            okButton.setEnabled(false);
        }
    }

    //All the parameters are string because they suppose to arrive from text fields (url is including the device and the action).
    private void startNewTimeTask(String url, String description, String deviceType, String delayInMinutes){
        ComponentName serviceName = new ComponentName(getActivity(), RestJobService.class);
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("description",description);
        persistableBundle.putString("url",url);
        persistableBundle.putString("deviceType",deviceType);
        JobInfo jobInfo = new JobInfo.Builder(jobID++, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setExtras(persistableBundle)
                .setMinimumLatency(Long.valueOf(delayInMinutes) * 1000 * 60)
                .setOverrideDeadline((Long.valueOf(delayInMinutes) * 1000 * 60) + 1)
//                .setPersisted(true)
                .build();
        JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result = scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS){
            Log.d("AddTask", "Job scheduled successfully!");
        }
    }

}
