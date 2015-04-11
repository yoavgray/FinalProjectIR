package com.bgu.project.finalprojectir.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bgu.project.finalprojectir.classes.DeviceType;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by Boaz on 10/04/2015.
 */
public abstract class AbstractRestTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected final RestTemplate restTemplate;
    String tag;
    private String description;

    public AbstractRestTask(String tag, String description) {
        this.tag = tag;
        this.description = description;
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    protected void onPostExecute(Result infoFromArduino) {
        super.onPostExecute(infoFromArduino);
        Log.d(tag, "task " + description + " finished with result: "+infoFromArduino);
    }
}
