package com.bgu.project.finalprojectir.classes;

import com.bgu.project.finalprojectir.ListItem;

public class Arduino extends ListItem {

    private String ip;

    public Arduino(int icon, String title, String ip) {
        super(icon, title);
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }
}