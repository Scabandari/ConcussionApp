package com.example.concussionapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MACActivity extends AppCompatActivity {

    TextView macID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac);

        macID = (TextView) findViewById(R.id.MAC);
        macID.setText(SharedData.MAC);

    }
}
