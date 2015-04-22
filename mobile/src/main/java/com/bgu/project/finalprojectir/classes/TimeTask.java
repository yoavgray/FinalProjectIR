package com.bgu.project.finalprojectir.classes;

import android.text.format.Time;
import java.util.Date;

public class TimeTask extends Task {

    private Date date;
    private Time time;

    public TimeTask(int icon, String ip, DeviceType deviceType, String brand, Date date, Time time, String action) {
        super(icon, ip, deviceType, brand, action);
        this.date = date;
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}