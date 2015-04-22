package com.bgu.project.finalprojectir.classes;

public class LocationTask extends Task {

    private boolean entering;
    private String place;

    public LocationTask(int icon, String ip, DeviceType deviceType, String brand, boolean entering, String place, String action) {
        super(icon, ip, deviceType, brand, action);
        this.entering = entering;
        this.place = place;
    }

    public boolean isEntering() {
        return entering;
    }

    public void setEntering(boolean entering) {
        this.entering = entering;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}