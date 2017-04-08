package com.example.concussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class RecoveryTrackingActivity extends AppCompatActivity
{
    private CheckBox step1, step2, step3, step4, step5, step6;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_tracking);
        addListenerCheck();

    }


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

                Intent intent = new Intent(getApplicationContext(), Excercise_Setup_Activity.class);
                startActivity(intent);

            }
        });

    }



}
