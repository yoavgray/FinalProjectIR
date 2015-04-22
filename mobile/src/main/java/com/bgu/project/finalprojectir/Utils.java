package com.bgu.project.finalprojectir;

import android.util.Log;

import com.bgu.project.finalprojectir.classes.DeviceType;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Boaz on 10/04/2015.
 */
public class Utils {

    public static final String HTTP_PREFIX = "http://";
    public static final String URL_SEPARATOR = "/";

    public static URI getURIForAddOrRemoveDevice(String ip, String action, DeviceType deviceType, String brand) {
        String url = HTTP_PREFIX +ip+ URL_SEPARATOR +action+ URL_SEPARATOR +deviceType+ URL_SEPARATOR +brand;
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            Log.e("URIForAddOrRemoveDevice", "couldn't create uri for url: " + url);

        }
        return null;
    }

    public static URI getURIForAddOrRemoveTimeTask(String ip, String action, DeviceType deviceType, String brand) {
        //TODO Boaz: create a url to support remove or add a task
        String url = "kaki";
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            Log.e("URIForAdd/RemTimeTask", "cant create uri for url: " + url);

        }
        return null;
    }

    public static URI getURIForAddOrRemoveLocationTask(String ip, String action, DeviceType deviceType, String brand) {
        //TODO Boaz: create a url to support remove or add a location task
        String url = "kaki";
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            Log.e("URIForAdd/RemLocTask", "couldn't create uri for url: " + url);

        }
        return null;
    }

    public static URI getURIForGetDevices(String ip){
        String url = HTTP_PREFIX +ip+ URL_SEPARATOR +"devices";
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            Log.e("URIForGetDevices", "couldn't create uri for url: " + url);
        }
        return null;
    }

    public static URI getURIForGetLocationTasks(String userName, String password){
        //TODO Boaz: create URL for database to return all location tasks per user+password
        String url = null;
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            Log.e("URIForGetLocationTasks", "couldn't create uri for url: " + url);
        }
        return null;
    }

    public static URI getURIForGetTimeTasks(String userName, String password){
        //TODO Boaz: create URL for database to return all time tasks per user+password
        String url = null;
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            Log.e("getURIForGetTimeTasks", "couldn't create uri for url: " + url);
        }
        return null;
    }

    public static URI getUriForAction(String ip, String action, DeviceType deviceType, String brand) {
        String url = HTTP_PREFIX +ip+ URL_SEPARATOR + deviceType+ URL_SEPARATOR +brand+ URL_SEPARATOR +action;
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            Log.e("UriForAction", "couldn't create uri for url: "+ url);
        }
        return null;
    }
}
