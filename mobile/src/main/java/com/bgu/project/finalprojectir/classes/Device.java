package com.bgu.project.finalprojectir.classes;

import com.bgu.project.finalprojectir.ListItem;

public class Device extends ListItem {

    private DeviceType deviceType;
    private String brand;

    public Device(int icon, DeviceType title, String brand) {
        super(icon, String.valueOf(title));
        this.deviceType = title;
        this.brand = brand.toUpperCase();
    }

    public String getBrand() {
        return brand;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }
}
