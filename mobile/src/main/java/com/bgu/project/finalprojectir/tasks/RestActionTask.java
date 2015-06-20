package com.bgu.project.finalprojectir.tasks;

import android.util.Log;

import com.bgu.project.finalprojectir.classes.DeviceType;

import java.net.URI;

/**
 * Created by Boaz on 10/04/2015.
 */
public class RestActionTask extends AbstractRestTask<Void, Void, String> {

    private final URI uri;

    public RestActionTask(String tag, String description, URI uri) {
        super(tag, description);
        this.uri = uri;
    }

    @Override
    protected String doInBackground(Void... params) {
        String responseEntity = null;
        try {
            responseEntity = restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            Log.e(tag, e.getMessage(), e);
        }
        return responseEntity;
    }

}
