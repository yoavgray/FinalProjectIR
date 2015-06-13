package com.bgu.project.finalprojectir.classes;

import com.parse.ParseUser;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/*
 * An extension of ParseObject that makes
 * it more convenient to access information
 * about a given Arduino
 */

@ParseClassName("ParseArduino")
public class ParseArduino extends ParseObject {

    public ParseArduino() {
        // A default constructor is required.
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public String getIp() {
        return getString("ip");
    }

    public void setIp(String ip) {
        put("ip", ip);
    }

    public String getIcon() {
        return getString("icon");
    }

    public void setIcon(String icon) {
        put("icon", icon);
    }

}
