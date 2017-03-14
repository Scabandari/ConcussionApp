package com.example.concussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
        public void onClick(View v)
        {

            String no = maxEditText.getText().toString();
            int no2 = Integer.parseInt(no);
            String n = minEditText.getText().toString();
            int n2 = Integer.parseInt(n);

            if (n2 > no2 )
            {

                Toast.makeText(getApplicationContext(), " Minimum is to high. ", Toast.LENGTH_LONG).show();
                return;
            }
            if (no2 < n2)
            {
                Toast.makeText(getApplicationContext(), " Maximum is to low. ", Toast.LENGTH_LONG).show();
                return;
            }
            sendMessage();



        }
    };
    //making the button listen for it to be clicked

}