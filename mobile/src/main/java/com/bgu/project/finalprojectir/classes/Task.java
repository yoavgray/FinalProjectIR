package com.bgu.project.finalprojectir.classes;

import com.bgu.project.finalprojectir.ListItem;

public class Task extends ListItem {

    private String ip;
    private DeviceType deviceType;
    private String brand;
    private String action;

    public Task(int icon, String ip, DeviceType deviceType, String brand, String action) {
        super(icon, ip);
        this.deviceType = deviceType;
        this.brand = brand;
        this.action = action;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
