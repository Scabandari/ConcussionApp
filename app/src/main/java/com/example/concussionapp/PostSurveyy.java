package com.example.concussionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PostSurveyy extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_surveyy);

        Button Capiche;

        Capiche = (Button) findViewById(R.id.understand_button);

        Capiche.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent (PostSurveyy.this,MainActivity.class));

            }
        });

    }

        public void YesAlert(View v)
        {
            AlertDialog.Builder yesAlert = new AlertDialog.Builder(this);
            yesAlert.setMessage("As you know, to recover well and faster, you should not experience any acute symptoms during your recovery process, you need to have NO symptoms during a training at all time, if you do STOP IMMEDIATELY and rest. If symptoms persist consult a doctor or a physician. You also need to spend 24 hours without having any symptoms before the next training session. Since some symptoms have occurred, you must restart you recovery process, starting from step 1. \nFull Rest for 24 hours.")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setTitle("Alert!").create();
            yesAlert.show();

        }

        public void NoAlert(View v)
        {
            AlertDialog.Builder noAlert = new AlertDialog.Builder(this);
            noAlert.setMessage("You are one step closer to finishing your recovery process. You can proceed to next step of recovery. Be caution for the next 24 hours as symptoms might reoccur. If so, consult your care provider before the next training session.")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("Congratulations!").create();
        noAlert.show();

        }





}
