package com.bgu.project.finalprojectir.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.bgu.project.finalprojectir.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeTaskFragment extends Fragment {
    private TimePicker timePicker;
    private DatePicker datePicker;
    private int hour;
    private int minute;
    private int month;
    private int day;

    static final int TIME_DIALOG_ID = 999;

    public TimeTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_time_task, container, false);
        timePicker = (TimePicker) rootView.findViewById(R.id.timePicker);
        datePicker = (DatePicker) rootView.findViewById(R.id.datePicker);
        timePicker.setVisibility(View.VISIBLE);

        setCurrentTimeOnView();
        return rootView;
    }

    // display current time
    public void setCurrentTimeOnView() {
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        // set current time into timepicker
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int selectedHour,
                                  int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;

                // set current time into timepicker
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
            }
        };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            month = monthOfYear;
            day = dayOfMonth;
        }
    };
}
