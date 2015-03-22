package com.bgu.project.finalprojectir;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AddTaskActivity extends ActionBarActivity {
    private static TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        mTextView = (TextView) findViewById(R.id.addTaskTextView);
        Intent incomingIntent = getIntent();
        if (incomingIntent.getIntExtra("device", 0) == 0) {
            mTextView.setText("Add a task for A/C");
        } else {
            mTextView.setText("Add a task for TV");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
