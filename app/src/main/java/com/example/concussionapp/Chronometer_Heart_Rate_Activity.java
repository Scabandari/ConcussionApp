package com.example.concussionapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

//import android.view.View.OnClickListener;

public class Chronometer_Heart_Rate_Activity extends Activity {

    Chronometer mChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer__heart__rate_);

        Intent intent = getIntent(); //get the intent from the mainActivity to link them
        //int intValue=intent.getIntExtra("maxEditText", 0);

        Button StartButton;
        Button StopButton;
        Button ResetButton;

        mChronometer = (Chronometer) findViewById(R.id.chronometer);

        StartButton= (Button) findViewById(R.id.start_Button);
        StartButton.setOnClickListener(mStartListener);

        StopButton= (Button) findViewById(R.id.stop_Button);
        StopButton.setOnClickListener(mStopListener);

        ResetButton = (Button) findViewById(R.id.reset_Button);
        ResetButton.setOnClickListener(mResetListener);

    }

    View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {
            mChronometer.start();
        }
    };
    View.OnClickListener mStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            mChronometer.stop();
        }
    };
    View.OnClickListener mResetListener = new View.OnClickListener()
    {
        public void onClick(View v) {mChronometer.setBase(SystemClock.elapsedRealtime());}
    };


}