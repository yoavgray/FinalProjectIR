package com.bgu.project.finalprojectir;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.bgu.project.finalprojectir.tasks.RestActionTask;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boaz on 10/04/2015.
 */
public class GeofenceIntentService extends IntentService {

    private static final String TAG = "GeofenceService";

    public GeofenceIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.e(TAG, "Geofencing Event Error: "+geofencingEvent.getErrorCode());
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            List<RestActionTask> actionList = new ArrayList();
            for (Geofence triggeringGeofence : triggeringGeofences) {
                try {
                    actionList.add(new RestActionTask(TAG,"geofences action",new URI(triggeringGeofence.getRequestId())));
                } catch (URISyntaxException e) {
                    Log.e(TAG, "couldn't create uri for url: "+ triggeringGeofence.getRequestId(), e);
                }
            }

            for (RestActionTask restActionTask : actionList) {
                restActionTask.execute();
            }


        } else {
            // Log the error.
            Log.e(TAG, "Invalid transition type: "+ geofenceTransition);
        }
    }
}
