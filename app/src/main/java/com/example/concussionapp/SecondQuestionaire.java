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
        showData = (TextView) findViewById(R.id.data);
        showData.setText(" Your data from today's session will be send to your care provider.\nYou can add a comment to the bottom of the email if necessary. ");
        Log.i(TAG, "Data to be sent is:  " + hrData);

        //new-Subhi
        //ConfirmUser = (EditText) findViewById(R.id.Confirm_Username);

        sendemail = (Button) findViewById(R.id.send_button);
        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);

                String emailContent = "";
                for(int i=0; i<SharedData.size; i++) {
                    emailContent += SharedData.dataArray[i];
                }

                emailContent += "\n\nThank you for using ConcussionApp. Post any comments you may have " +
                        "for your care provider below. \n\nComments:";

                Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String [] {SharedData.user.getCareProviderEmailAddress()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Today's exercise data for user: " + SharedData.user.getUserName());
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email!", "");
                }
                catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SecondQuestionaire.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }


}
