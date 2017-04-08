package com.example.concussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

   //     addListenerCheck();

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
