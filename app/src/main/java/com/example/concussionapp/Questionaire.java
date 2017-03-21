package com.example.concussionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Questionaire extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        goToExercise();

    }

    protected void goToExercise()

    {
        Button buttonsubmit = (Button) findViewById(R.id.submitquiz);

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View Profile) {
             //   startActivity(new Intent(MainActivity.this, Questionaire.class));
            //    Intent intent = new Intent(v.getContext(), Excercise_Setup_Activity.class);
                // intent.putExtra("course_keys", db.getFloatList());
            //    startActivity(intent);



                Intent intent = new Intent(getApplicationContext(),Excercise_Setup_Activity.class);
                startActivity(intent);
            }
        });
    }

}
