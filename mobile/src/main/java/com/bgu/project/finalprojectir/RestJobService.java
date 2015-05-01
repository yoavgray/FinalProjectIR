package com.bgu.project.finalprojectir;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;

import com.bgu.project.finalprojectir.tasks.RestActionTask;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Boaz on 10/04/2015.
 */
public class RestJobService extends JobService {

    public static final String TAG = "JobService";

    @Override
    public boolean onStartJob(JobParameters params) {
        PersistableBundle extras = params.getExtras();
        URI uri = null;
        try {
            uri = new URI(extras.getString("url"));
        } catch (URISyntaxException e) {
            Log.e(TAG, "Job Service couldn't create uri for url: "+ extras.getString("url"));
            return false;
        }
        String description = extras.getString("description");
        RestActionTask restActionTask = new RestActionTask(TAG, description, uri);
        restActionTask.execute();

        // build notification
        Notification n  = new Notification.Builder(this)
                .setContentTitle("Time Task Executed")
                .setContentText(description)
                .setSmallIcon(R.drawable.ac_icon)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
