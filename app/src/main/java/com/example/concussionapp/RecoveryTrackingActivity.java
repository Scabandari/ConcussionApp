package com.example.concussionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecoveryTrackingActivity extends AppCompatActivity
{
  //  private CheckBox step1, step2, step3, step4, step5, step6;
  //  private Button submit;

    ArrayList<String> selection = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_tracking);

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
                    selection.add("Step 1: 24 hour of rest");
                }
                else
                {
                    selection.remove("Step 1: 24 hour of rest");
                }

                break;

            case R.id.step2:

                if (checked) {
                    selection.add("Step 2: Light aerobic exercises");
                }
                else
                {
                    selection.remove("Step 2: Light aerobic exercises");
                }
                break;

            case R.id.step3:

                if (checked) {
                    selection.add("Step 3: Sport specified exercises");
                }
                else
                {
                    selection.remove("Step 3: Sport specified exercises");
                }

                break;

            case R.id.step4:

                if (checked) {
                    selection.add("Step 4: Non-contact training drills");
                }
                else
                {
                    selection.remove("Step 4: Non-contact training drills");
                }

                break;

            case R.id.step5:

                if (checked) {
                    selection.add("Step 5: Full contact practice");
                }
                else
                {
                    selection.remove("Step 5: Full contact practice");
                }

                break;

            case R.id.step6:

                if (checked) {
                    selection.add("Step 6: Return to play");
                }
                else
                {
                    selection.remove("Step 6: Return to play");
                }

                break;
        }

    }

    public void final_selection(View view)
    {

        Intent intent = new Intent(getApplicationContext(), Excercise_Setup_Activity.class);
        startActivity(intent);
    }

    /* Vals code

    public void addListenerCheck()
    {

        step1 = (CheckBox)findViewById(R.id.Check_Step1);
        step2 = (CheckBox)findViewById(R.id.Check_Step2);
        step3 = (CheckBox)findViewById(R.id.Check_Step3);
        step4 = (CheckBox)findViewById(R.id.Check_Step4);
        step5 = (CheckBox)findViewById(R.id.Check_Step5);
        step6 = (CheckBox)findViewById(R.id.Check_Step6);
        submit = (Button) findViewById(R.id.done_button);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StringBuffer result = new StringBuffer();
                result.append(" Step1 : ").append(step1.isChecked());
                result.append(" Step2 : ").append(step2.isChecked());
                result.append(" Step3 : ").append(step3.isChecked());
                result.append(" Step4 : ").append(step4.isChecked());
                result.append(" Step5 : ").append(step5.isChecked());
                result.append(" Step6 : ").append(step6.isChecked());

                if(step1.isChecked() == true)
                {
                    selection1 [0] = "Step 1: 24 hour of rest";
                    Toast.makeText(RecoveryTrackingActivity.this, "Steps are: " + selection1[0], Toast.LENGTH_SHORT).show();
                }
                if (step2.isChecked() == true)
                {
                    selection1 [1] = "Step 2: Light aerobic exercises";
                }
                if(step3.isChecked() == true)
                {
                    selection1 [2] = "Step 3: Sport specified exercises";
                }
                if (step4.isChecked() == true)
                {
                    selection1 [3] = "Step 4: Non-contact training drills";
                }
                if(step5.isChecked() == true)
                {
                    selection1 [4] = "Step 5: Full contact practice";
                }
                if (step6.isChecked() == true)
                {
                    selection1 [5] = "Step 6: Return to play";
                }



                Intent intent = new Intent(getApplicationContext(), Excercise_Setup_Activity.class);
                startActivity(intent);

            }
        });

    }*/



}
