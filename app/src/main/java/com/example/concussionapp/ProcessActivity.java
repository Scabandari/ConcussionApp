package com.example.concussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

public class ProcessActivity extends AppCompatActivity {

    private static final String TAG = ProcessActivity.class.getSimpleName();

    private String[] StepValue;

    private CheckBox Step1;

    private String Step1box;

    private String[] selection1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);

 //       listenerStep1();
    }

    public void btn_step1(View view)
    {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId())
        {
            case R.id.step1:

                if(checked)
                {
                    selection1 [0] = "Step 1: 24 hour of rest";
                }

                break;

            case R.id.step2:

                if(checked)
                {
                    selection1 [1] = "Step 2: Light aerobic exercises";
                }

                break;

            case R.id.step3:

                if(checked)
                {
                    selection1 [2] = "Step 3: Sport specified exercises";
                }

                break;

            case R.id.step4:

                if(checked)
                {
                    selection1 [3] = "Step 4: Non-contact training drills";
                }

                break;

            case R.id.step5:

                if(checked)
                {
                    selection1 [4] = "Step 5: Full contact practice";
                }

                break;

            case R.id.step6:

                if(checked)
                {
                    selection1 [5] = "Step 6: Return to play";
                }

                break;

        }
    }

    public void final_selection(View v)
    {

        Intent
    }


/*
    public void listenerStep1() {

        Step1 = (CheckBox) findViewById(R.id.step1);

        Step1.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener(CompoundButton buttonView, boolean isChecked) {

            @Override
            public void onRatingChanged(CheckBox Checkbox, float Check, boolean fromUser) {

                Step1box = String.valueOf(Check);
                Log.i(TAG, "Checkbox: " + Step1box);

                StepValue[0] = "Headache rating: " + Step1box + "\n";


            }
        });
    }*/
}
