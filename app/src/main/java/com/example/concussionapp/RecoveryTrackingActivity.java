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
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class RecoveryTrackingActivity extends AppCompatActivity
{
  //  private CheckBox step1, step2, step3, step4, step5, step6;
  //  private Button submit;

    private final String TAG = "RecoveryTrackingAct";
    ArrayList<String> selection;
    private int max;

    private final String[] checkBoxes = {"Step 1: 24 hour of rest",
                                        "Step 2: Light aerobic exercises",
                                        "Step 3: Sport specified exercises",
                                        "Step 4: Non-contact training drills",
                                        "Step 5: Full contact practice",
                                        "Step 6: Return to play"};

    private boolean oneChecked;
    private boolean twoChecked;
    private boolean threeChecked;
    private boolean fourChecked;
    private boolean fiveChecked;
    private boolean sixChecked;

    private boolean boxesEmpty;

    private CheckBox one;
    private CheckBox two;
    private CheckBox three;
    private CheckBox four;
    private CheckBox five;
    private CheckBox six;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_tracking);

        one = (CheckBox) findViewById( R.id.step1);
        two = (CheckBox) findViewById( R.id.step2);
        three = (CheckBox) findViewById( R.id.step3);
        four = (CheckBox) findViewById( R.id.step4);
        five = (CheckBox) findViewById( R.id.step5);
        six = (CheckBox) findViewById( R.id.step6);

        boxesEmpty = true;
        //THIS SHOULD BE IN onStart() ????????????
        oneChecked = false;
        twoChecked = false;
        threeChecked = false;
        fourChecked = false;
        fiveChecked = false;
        sixChecked = false;
        one.setChecked(false);
        two.setChecked(false);
        three.setChecked(false);
        four.setChecked(false);
        five.setChecked(false);
        six.setChecked(false);



        max = 0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

   //     addListenerCheck();

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
                aboutAlert.setMessage("NoCussion is designed to guide concussed athletes throughout their recovery.  It mostly help athletes during the second step of the recovery, which is a Light Aerobic Exercise where the heart rate should not go above nor beyond a threshold value.  The threshold value should be measured with your care provider. Moreover, this application will evaluate the concussion symptoms, with respect to the SCAT3 test. All results, heartbeat and symptoms evaluation will be sent to the user's care provider at the end of the session.  The application will monitor the heartbeat with a heart sensor and will limit the user within her/his heart boundaries.  This application should not replace a doctor’s consultation but should be used hands-in-hand with your trainer/health professional.")
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
                helpAlert.setMessage("This page is where you provide the information on which steps you have completed so far.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("Help").create();
                helpAlert.show();
                return true;
            case R.id.Switch3:
                Intent logout = new Intent(RecoveryTrackingActivity.this, MainActivity.class);
                startActivity(logout);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCheckboxClicked(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.step1:

                if (checked) {
                    //selection.add("Step 1: 24 hour of rest");
                    oneChecked = true;
                }
                else
                {
                  //  selection.remove("Step 1: 24 hour of rest");
                    oneChecked = false;
                }

                break;

            case R.id.step2:

                if (checked) {
                   // selection.add("Step 2: Light aerobic exercises");
                    twoChecked = true;
                }
                else
                {
                    twoChecked = false;
                    // selection.remove("Step 2: Light aerobic exercises");
                }
                break;

            case R.id.step3:

                if (checked) {
                    //selection.add("Step 3: Sport specified exercises");
                    threeChecked = true;
                }
                else
                {
                   threeChecked = false;
                    // selection.remove("Step 3: Sport specified exercises");
                }

                break;

            case R.id.step4:

                if (checked) {
                   // selection.add("Step 4: Non-contact training drills");
                    fourChecked = true;
                }
                else
                {
                   fourChecked = false;
                    //  selection.remove("Step 4: Non-contact training drills");
                }

                break;

            case R.id.step5:

                if (checked) {
                    fiveChecked = true;
              //      selection.add("Step 5: Full contact practice");
                }
                else
                {
                   // selection.remove("Step 5: Full contact practice");
                    fiveChecked = false;
                }

                break;

            case R.id.step6:

                if (checked) {
                    sixChecked = true;
                   // selection.add("Step 6: Return to play");
                }
                else
                {
                    sixChecked = false;
                    //selection.remove("Step 6: Return to play");
                }

                break;
        }

    }

    public void final_selection(View view)
    {



        if(sixChecked){
            max = 5;
            boxesEmpty = false;
        }
        else if(fiveChecked) {
            max = 4;
            boxesEmpty = false;
        }
        else if(fourChecked) {
            max = 3;
            boxesEmpty = false;
        }
        else if(threeChecked) {
            max = 2;
            boxesEmpty = false;
        }
        else if(twoChecked) {
            max = 1;
            boxesEmpty = false;
        }
        else if(oneChecked) {
            max = 0;
            boxesEmpty = false;
        }
        else {
            boxesEmpty = true;
        }

        if(max == 0 && boxesEmpty){
            Toast.makeText(getApplicationContext(), "Please make a selection", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "EMPTY. toast should show here");
            return;
        }
        Log.i(TAG, checkBoxes[max]);
        SharedData.checkboxData =  "\nWhere " + SharedData.user.getUserName() + " is at in their " +
            "recovery:\n" + checkBoxes[max] + "\n";
        Intent intent = new Intent(getApplicationContext(), Excercise_Setup_Activity.class);
        startActivity(intent);
    }





}
