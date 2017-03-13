package com.example.concussionapp;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.SystemClock;
        import android.view.View;
        //import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.Chronometer;

public class Chronometer_Heart_Rate_Activity extends Activity {

    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer__heart__rate_);

        Intent intent = getIntent(); //get the intent from the mainActivity to link them
        String MaxValue=intent.getStringExtra("maxEditText");
        int Max=Integer.parseInt(MaxValue);
        String MinValue=intent.getStringExtra("maxEditText");
        int Min=Integer.parseInt(MinValue);

        Button StartButton;
        Button StopButton;
        Button ResetButton;

        chronometer = (Chronometer) findViewById(R.id.chronometer);

        StartButton= (Button) findViewById(R.id.start_Button);
        StartButton.setOnClickListener(mStartListener);

        StopButton= (Button) findViewById(R.id.stop_Button);
        StopButton.setOnClickListener(mStopListener);

        ResetButton = (Button) findViewById(R.id.reset_Button);
        ResetButton.setOnClickListener(mResetListener);

    }

    View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {
            chronometer.start();
        }
    };
    View.OnClickListener mStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            chronometer.stop();
        }
    };
    View.OnClickListener mResetListener = new View.OnClickListener()
    {
        public void onClick(View v) {chronometer.setBase(SystemClock.elapsedRealtime());}
    };


}
