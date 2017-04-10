package com.example.concussionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private String data;
    Button Donebutton;


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


//        Intent intent = getIntent(); //get the intent from the mainActivity to link them
//        dataFromSurvey = intent.getStringExtra("surveyData");

        Donebutton = (Button) findViewById(R.id.DoneSetupButton);

       Donebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //linking the editText on the user interface to the one created here
               maxEditText = (EditText) findViewById(R.id.MaxEditText);
               String maxHR = maxEditText.getText().toString();
               if(!maxHR.matches("")) {
                   maxHeartRate = Integer.parseInt(maxHR);
               }

               minEditText = (EditText) findViewById(R.id.MinEditText);
               String minHR = minEditText.getText().toString();
               if(!minHR.matches("")) {
                   minHeartRate = Integer.parseInt(minHR);
               }


               durationEditText = (EditText) findViewById(R.id.exercise_duration);
               String duration = durationEditText.getText().toString();
               if(!duration.matches("")) {
                   timeEntered = Integer.parseInt(duration);
               }




               if(maxHR.matches("") || minHR.matches("")|| duration.matches("") )
               {
                   Toast.makeText(getApplicationContext(), "Cannot have Empty fields", Toast.LENGTH_LONG).show();
                   return;
               }

               Log.i(TAG, "Max heart rate as int : " + maxHeartRate);
               Log.i(TAG, "Min heart rate as int : " + minHeartRate);
               Log.i(TAG, "Time user entered is : " + timeEntered);


               if (minHeartRate >= maxHeartRate)
               {

                   Toast.makeText(getApplicationContext(), " Min must be lower than Max. ", Toast.LENGTH_LONG).show();
                   return;
               }
               if (maxHeartRate > 200)
               {

                   Toast.makeText(getApplicationContext(), " Max must be lower than 200. ", Toast.LENGTH_LONG).show();
                   return;
               }

                   //    Intent intent = new Intent (this,Chronometer_Heart_Rate_Activity.class);
                   Intent intent = new Intent(getApplicationContext(), Chronometer_Heart_Rate_Activity.class);
                   intent.putExtra("maxHeartRate", maxHeartRate);
                   intent.putExtra("minHeartRate", minHeartRate);
                   intent.putExtra("exerciseTime", timeEntered);

                    data = "Data from noCussion on user: " + SharedData.user.getUserName() + "\n";
                    data += SharedData.checkboxData;
                    data += "\n\nThe patient's Max heart rate setting:\n" + maxHR ;
                    data += "\nThe patient's Min heart rate setting:\n " + minHR;
                    data += "\nThe patient's duration of exercise time: " + duration + "\n\n";

                    SharedData.dataArray[0] = data;
                    Log.i(TAG, data);
                    Log.i(TAG, data);
                    Log.i(TAG, data);
                    Log.i(TAG, data);


                   maxEditText.setText("");
                   minEditText.setText("");
                   durationEditText.setText("");

                    startActivity(intent);

           }
       });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()){
            case R.id.Switch1:
                //what happens when menu item is pressed

                AlertDialog.Builder aboutAlert = new AlertDialog.Builder(this);
                aboutAlert.setMessage(" NoCussion is a tool designed for any member of the medical team who is treating athletes recovering from a concussion.  The application gives information on concussion and tips for a better recovery process. NoCussion guide the athletes throughout their recovery. The user will have her/his heart beat monitor during their training and will be asked to rate their concussion symptom after a physical effort. All data will be sent to the athlete’s care provider by email.  NoCussion facilitates the communication between the athlete and the care provider during the recovery process.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("About Us").create();
                aboutAlert.show();
                return true;
            case R.id.Switch2:

                AlertDialog.Builder helpAlert = new AlertDialog.Builder(this);
                helpAlert.setMessage("This is your setup page: In the first field, you should input the maximum heart rate at which you will be training today.\nIn the second field, you input the minimum heart rate at which you will train.\nIn the last field, you input the duration of your exercise.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("Help").create();
                helpAlert.show();
                return true;
            case R.id.Switch3:
                Intent logout = new Intent(Excercise_Setup_Activity.this, MainActivity.class);
                startActivity(logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}