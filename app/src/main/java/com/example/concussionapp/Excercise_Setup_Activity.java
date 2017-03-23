package com.example.concussionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Excercise_Setup_Activity extends AppCompatActivity {

    private final String TAG = "Exercise_Setup_Activity";
    protected EditText maxEditText;
    protected EditText minEditText;
    protected EditText durationEditText;
 //   protected Spinner timeSpinner;
    private int  maxHeartRate;
    private int  minHeartRate;

    //get value from edit text exercise_duration in exercise setup xml
    private int timeEntered;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise__setup_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



       // timeSpinner = (Spinner) findViewById(R.id.TimeSpinner);


        Button done_setup_button;

        //linking the button on the user interface to the one created here and making it listen
        done_setup_button = (Button) findViewById(R.id.DoneSetupButton);
        done_setup_button.setOnClickListener(OnClickSetupButton);

    }
/*
    public void sendMessage() //function to send the user to the next activity
    {
        Intent intent = new Intent (this,Chronometer_Heart_Rate_Activity.class);
        intent.putExtra("maxHeartRate", maxHeartRate);
        intent.putExtra("minHeartRate", minHeartRate);
        startActivity(intent);

    }
*/
    private Button.OnClickListener OnClickSetupButton = new Button.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            //linking the editText on the user interface to the one created here
            maxEditText = (EditText) findViewById(R.id.MaxEditText);
            String maxHR = maxEditText.getText().toString();
            maxHeartRate = Integer.parseInt(maxHR);


            minEditText = (EditText) findViewById(R.id.MinEditText);
            String minHR = minEditText.getText().toString();
            minHeartRate = Integer.parseInt(minHR);

            durationEditText = (EditText) findViewById(R.id.exercise_duration);
            String duration = durationEditText.getText().toString();
            timeEntered = Integer.parseInt(duration);

            Log.i(TAG, "Max heart rate as int : " + maxHeartRate);
            Log.i(TAG, "Min heart rate as int : " + minHeartRate);
            Log.i(TAG, "Time user entered is : " + timeEntered);
            // convert edit text to integer
 /*           String no = maxEditText.getText().toString();
            int no2 = Integer.parseInt(no);
            String n = minEditText.getText().toString();
            int n2 = Integer.parseInt(n);
*/
            if (minHeartRate >= maxHeartRate )
            {

                Toast.makeText(getApplicationContext(), " Min must be lower than Max. ", Toast.LENGTH_LONG).show();
                return;
            }

        //    Intent intent = new Intent (this,Chronometer_Heart_Rate_Activity.class);
            Intent intent = new Intent (getApplicationContext(),Chronometer_Heart_Rate_Activity.class);
            intent.putExtra("maxHeartRate", maxHeartRate);
            intent.putExtra("minHeartRate", minHeartRate);
            intent.putExtra("exerciseTime", timeEntered);
            startActivity(intent);
          //  sendMessage();

        }
    };
    //making the button listen for it to be clicked

}