package com.example.concussionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class second_Activity extends AppCompatActivity {

    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onStart() {
        super.onStart();
        //I'm just displaying my info retrieved from database here for an example
        TextView ryansInfo = (TextView)findViewById(R.id.testing_db);
        getApplicationContext();
        db = DBHandler.getInstance(this); //singleton pattern
        User ry = db.fetchUser("Ryan");
        String ryansInfoInStringFormat = "Name: " + ry.getUserName() + ", Password: "
                + ry.getPassWord() + " Care Providers Email: "
                + ry.getCareProviderEmailAddress();
        ryansInfo.setText(ryansInfoInStringFormat);

    }

    public void to_second_activity(View view) {
        Intent intent = new Intent(this, second_Activity.class);
        startActivity(intent);
    }


}
