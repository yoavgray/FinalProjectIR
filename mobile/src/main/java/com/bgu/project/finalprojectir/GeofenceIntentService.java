package com.bgu.project.finalprojectir;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.util.Log;

import com.bgu.project.finalprojectir.tasks.RestActionTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boaz on 10/04/2015.
 */
public class GeofenceIntentService extends IntentService implements ResultCallback<Status> {

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
        GoogleApiClient mGoogleApiClient;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();

        ConnectionResult connectionResult = mGoogleApiClient.blockingConnect();
        if(connectionResult.isSuccess()) {

            // Get the transition type.
            int geofenceTransition = geofencingEvent.getGeofenceTransition();

            // Test that the reported transition was of interest.
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

                // Get the geofences that were triggered. A single event can trigger
                // multiple geofences.
                List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                int notificationCounter = 0;

                List<String> geofenceToDelete= new ArrayList<>();
                for (Geofence triggeringGeofence : triggeringGeofences) {
                    try {
                        String[] tmpString = triggeringGeofence.getRequestId().split(";");
                        new RestActionTask(TAG, "geofences action", new URI(tmpString[0])).execute();

                        geofenceToDelete.add(triggeringGeofence.getRequestId());

                        int taskTypeIcon = tmpString[2].equals("ac") ? R.drawable.ac_icon : R.drawable.tv_icon;
                        Notification n = new Notification.Builder(this)
                                .setContentTitle("Geo Task Executed")
                                .setContentText("name:" + tmpString[1] + " ip: " + tmpString[0])
                                .setSmallIcon(taskTypeIcon)
                                .build();

                        ParseQuery<ParseObject> query = ParseQuery.getQuery("ParseTask");
                        query.whereEqualTo("user", ParseUser.getCurrentUser());
                        query.whereEqualTo("title", tmpString[1]);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                                if (e == null) {
                                    for (ParseObject i:parseObjects) {
                                        i.deleteInBackground();
                                    }
                                } else {
                                    Log.d("score", "Error: " + e.getMessage());
                                }
                            }
                        });


                        notificationManager.notify(notificationCounter, n);
                        notificationCounter++;

                    } catch (URISyntaxException e) {
                        Log.e(TAG, "couldn't create uri for url: " + triggeringGeofence.getRequestId(), e);
                    }
                }

                LocationServices.GeofencingApi.removeGeofences(
                        mGoogleApiClient,
                        geofenceToDelete
                ).setResultCallback(this); // Result processed in onResult().


            } else {
                Log.e(TAG, "Invalid transition type: " + geofenceTransition);
            }
            mGoogleApiClient.disconnect();
        }else{
            Log.e(TAG, "Failed to connect google api client");
        }
    }

    @Override
    public void onResult(Status status) {
        Log.i(TAG, "geofences was deleted");
        //TODO Boaz remove

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification n = new Notification.Builder(this)
                .setContentTitle("Geo Deleted")
                .setContentText("Is success: "+status.isSuccess())
                .setSmallIcon(R.drawable.ac_icon)
                .build();

        notificationManager.notify(55454587, n);

    }
}
