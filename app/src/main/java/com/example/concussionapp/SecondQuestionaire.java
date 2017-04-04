package com.example.concussionapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondQuestionaire extends AppCompatActivity {

    String hrData;
    TextView showData;
    Button done;
    DBHandler dbHandler;

    private static final  String TAG = "SecondQuestionaire";
    Button sendemail;
    EditText ConfirmUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_questionaire);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //new-Subhi
        dbHandler = DBHandler.getInstance(this);


        Intent intent = getIntent();
        hrData = intent.getStringExtra("allData");

        showData = (TextView) findViewById(R.id.data);

        showData.setText(hrData);
        Log.i(TAG, "Data to be sent is:  " + hrData);

        //new-Subhi
        ConfirmUser = (EditText) findViewById(R.id.Confirm_Username);

        sendemail = (Button) findViewById(R.id.send_button);
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = ConfirmUser.getText().toString();
                User user = dbHandler.fetchUser(username);

                //  String ToEmail = user.getCareProviderEmailAddress();

                Log.i("Send Email ", "");
                Log.i(TAG, "User name from database is: " + user.getUserName());
                Log.i(TAG, "Password from database is: " + user.getPassWord());
                Log.i(TAG, "Email from database is: " + user.getCareProviderEmailAddress());
                Log.i(TAG, "Data to be sent is:  " + hrData);


                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String [] {user.getCareProviderEmailAddress()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Today's exercise data for user: " + user.getUserName());
                emailIntent.putExtra(Intent.EXTRA_TEXT,hrData);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email!", "");
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SecondQuestionaire.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }


              //  sendEmail();
            }
        });

        done = (Button) findViewById(R.id.done_button);
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), PostSurveyy.class);
                startActivity(intent);
            }
        });

    }

//new-Subhi
/*    protected void sendEmail()
    {

        String username = ConfirmUser.getText().toString();
        User user = dbHandler.fetchUser(username);

      //  String ToEmail = user.getCareProviderEmailAddress();

        Log.i("Send Email ", "");
        Log.i(TAG, "User name from database is: " + user.getUserName());
        Log.i(TAG, "Password from database is: " + user.getPassWord());
        Log.i(TAG, "Email from database is: " + user.getCareProviderEmailAddress());


        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
       // emailIntent.putExtra(Intent.EXTRA_EMAIL, ToEmail);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, user.careProviderEmailAddress);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Today's exercise data");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here - Data acquired during exercise");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email!", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SecondQuestionaire.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }*/

}
