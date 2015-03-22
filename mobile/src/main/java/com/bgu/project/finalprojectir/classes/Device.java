package com.bgu.project.finalprojectir.classes;

import com.bgu.project.finalprojectir.ListItem;

public class Device extends ListItem {
    private String brand;

    public Device(int icon, String title, String brand) {
        super(icon, title);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}
