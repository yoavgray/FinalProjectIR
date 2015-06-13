package com.bgu.project.finalprojectir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bgu.project.finalprojectir.classes.ParseArduino;
import com.bgu.project.finalprojectir.classes.ParseTask;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseLoginActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add your initialization code here
        ParseObject.registerSubclass(ParseArduino.class);
        ParseObject.registerSubclass(ParseTask.class);

        Parse.initialize(this, "jjM5NNmAjfxLDFvJxsqa0MnJ4rtghHHTvGjuXshX", "PfMtyx7w4qYm8jfayBqZnBLBl5qnwfyxr3uOYERM");

        ParseUser.enableAutomaticUser();
        ParseUser.enableRevocableSessionInBackground();

        ParseACL defaultACL = new ParseACL();

// If you would like all objects to be private by default, remove this

// line.

        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(ParseLoginActivity.this,
                    LoginSignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                Intent intent = new Intent(ParseLoginActivity.this, Main.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(ParseLoginActivity.this,
                        LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
}