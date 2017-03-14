package com.example.concussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Excercise_Setup_Activity extends AppCompatActivity {

    protected EditText maxEditText;
    protected EditText minEditText;
    protected Spinner timeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise__setup_);

        //linking the editText on the user interface to the one created here
        maxEditText = (EditText) findViewById(R.id.MaxEditText);
        minEditText = (EditText) findViewById(R.id.MinEditText);
        timeSpinner = (Spinner) findViewById(R.id.TimeSpinner);

        Button done_setup_button;

        //linking the button on the user interface to the one created here and making it listen
        done_setup_button = (Button) findViewById(R.id.DoneSetupButton);
        done_setup_button.setOnClickListener(OnClickSetupButton);

    }

    public void sendMessage() //function to send the user to the next activity
    {
        Intent intent = new Intent (this,Chronometer_Heart_Rate_Activity.class);
        startActivity(intent);
        //intent.putExtra("maxEditText", intValue);
    }

    private Button.OnClickListener OnClickSetupButton = new Button.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            sendMessage();

        }
    };
    //making the button listen for it to be clicked

}