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

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu2, menu);
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
                helpAlert.setMessage("On this page, we provide you information regarding signs & symptoms of a concussion, we also provide tips that could help accelerate your recovery process, as well as steps to take towards your recovery process. From this page you can use the 'Quickstart' to use heart monitor alone OR use 'Start Recovery' to begin with your recovery process.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("Help").create();
                helpAlert.show();
                return true;

            case R.id.Switch3:
                Intent logout = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(logout);
                return true;

            case R.id.Switch4:
                Intent changeMAC = new Intent(WelcomeActivity.this, MACActivity.class);
                startActivity(changeMAC);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void quickstart(View view)
    {

        Intent intent = new Intent(this, Quickstart.class);
        startActivity(intent);
    }

    public void sign_symptoms(View view)
    {

        AlertDialog.Builder preventionAlert = new AlertDialog.Builder(this);
         preventionAlert.setMessage("Concussion should be suspected in the presence of symptoms and physical signs. \n\n Potential symptoms of concussion: \n Headache \n Pressure in head \n Neck Pain \n Confusion \n Sensitivity to light and noise \n Balance problems \n Don’t feel right \n Difficulty concentrating \n Difficulty remembering \n Anxious or Nervous \n Blurred vision")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("Signs & Symptoms").create();
        preventionAlert.show();
    }

    public void recovery_steps(View view)
    {

        AlertDialog.Builder preventionAlert = new AlertDialog.Builder(this);
        preventionAlert.setMessage("1. A 24 hours rest \n2. Light aerobic exercises, increase heart rate up to 70% \n3. Sport specified exercises, no head contact \n4. Non-contact training drills \n5. Full contact practice \n6. Return to play \n\nRecall that there should be at least a 24 hours or longer period without symptoms for each step. If any symptoms reoccur, stop any activity and contact a doctor or physician before your next training again. Medical clearance needed.\n* Information provided by the Scat3(Sport Concussion Assessment Tool - 3rd Edition)")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("Recovery steps").create();
        preventionAlert.show();
    }

    public void recovery_tips(View view)
    {

        AlertDialog.Builder preventionAlert = new AlertDialog.Builder(this);
        preventionAlert.setMessage("For a better recovery process, athlete should avoid alcohol, driving, sports and aspirin. Full rest until symptoms are resolved is mandatory.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("Recovery Tips").create();
        preventionAlert.show();
    }
    public void start_recovery(View view)
    {

        Intent intent = new Intent(this, RecoveryTrackingActivity.class);
        startActivity(intent);
    }


}

