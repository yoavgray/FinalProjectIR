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

    public static URI getURIForGetDevices(String ip){
        String url = HTTP_PREFIX +ip+ URL_SEPARATOR +"devices";
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            Log.e("URIForGetDevices", "couldn't create uri for url: " + url);
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
