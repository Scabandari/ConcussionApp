package com.example.concussionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


    }

    public void quickstart(View view)
    {

        Intent intent = new Intent(this, Chronometer_Heart_Rate_Activity.class);
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

