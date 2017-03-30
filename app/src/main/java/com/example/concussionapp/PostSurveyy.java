package com.example.concussionapp;

import android.content.Intent;
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

        Button Oui;
        Button Non;
        Button Capiche;

        Oui = (Button) findViewById(R.id.Yes_Button);
        Non = (Button) findViewById(R.id.No_Button);
        Capiche = (Button) findViewById(R.id.understand_button);


        Oui.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent(PostSurveyy.this,YesPop.class));

            }
        });

        Non.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent (PostSurveyy.this,NonPop.class));

            }
        });

        Capiche.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent (PostSurveyy.this,MainActivity.class));

            }
        });




    }




}
