package com.example.concussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MACActivity extends AppCompatActivity {

    private final String TAG = "MACActivity";
    EditText one;
    EditText two;
    EditText three;
    EditText four;
    EditText five;
    EditText six;

    private String newMac;

    TextView macID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mac);

        newMac = "";
        macID = (TextView) findViewById(R.id.MAC);
        macID.setText(SharedData.MAC);

        one = (EditText) findViewById(R.id.firstET);
        two = (EditText) findViewById(R.id.secondET);
        three = (EditText) findViewById(R.id.thirdET);
        four = (EditText) findViewById(R.id.fourthET);
        five = (EditText) findViewById(R.id.fifthET);
        six = (EditText) findViewById(R.id.sixthET);

    }

    public void saveButton(View view) {
        String first = one.getText().toString();
        String second = two.getText().toString();
        String third = three.getText().toString();
        String fourth = four.getText().toString();
        String fifth = five.getText().toString();
        String sixth = six.getText().toString();

        newMac = first + ":" + second + ":" + third + ":" + fourth + ":" + fifth + ":" + sixth;
        SharedData.MAC = newMac;
        Toast.makeText(getApplicationContext(), "MAC address updated.", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "New Mac Address: " + SharedData.MAC);
        macID.setText(SharedData.MAC);
    }


    public void doneButton(View view) {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);

        startActivity(intent);
    }

}


