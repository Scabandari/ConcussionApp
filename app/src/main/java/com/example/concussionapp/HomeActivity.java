package com.example.concussionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private EditText loginusername;
    private EditText loginpassword;
    private String passUsername;
    private String passEmail;
    private static final  String TAG = "HomeActivity";

    DBHandler dbHandler;
    Button btnIn;
 //   Button btnUp;

    SharedData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //create an instance of SQLite database
        dbHandler = DBHandler.getInstance(this);


        btnIn = (Button) findViewById(R.id.ButtonSignIn);

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 loginusername = (EditText) findViewById(R.id.loginusername);
                 loginpassword = (EditText) findViewById(R.id.loginpassword);


                //get username and password
                String username = loginusername.getText().toString();
                String password = loginpassword.getText().toString();

                //fetch the password from database for respective user
                //Create an object of Class User to store password & email

                User Checkpass = dbHandler.fetchUser(username);

                Log.i(TAG, "Username entered is:  " + username);
                Log.i(TAG, "Password entered is: " + password);
                Log.i(TAG, "User name from database is: " + Checkpass.getUserName());
                Log.i(TAG, "Password from database is: " + Checkpass.getPassWord());
                Log.i(TAG, "Email from database is: " + Checkpass.getCareProviderEmailAddress());

                if(username.equals("") || password.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_LONG).show();
                    return;
                }
                //check if stored password matches user password
                if (password.equals(Checkpass.getPassWord())) {

                    user = new SharedData(Checkpass);

                    passEmail = Checkpass.getCareProviderEmailAddress();
                    passUsername = Checkpass.getUserName();
                    Log.i(TAG, passEmail);
                    Log.i(TAG, passUsername);

                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);

                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "Username or Password incorrect", Toast.LENGTH_LONG).show();

                }


            }

        });

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()){
            case R.id.Switch1:
                //what happens when menu item is pressed

                AlertDialog.Builder aboutAlert = new AlertDialog.Builder(this);
                aboutAlert.setMessage("NoCussion is designed to guide concussed athletes throughout their recovery.  It mostly help athletes during the second step of the recovery, which is a Light Aerobic Exercise where the heart rate should not go above nor beyond a threshold value.  The threshold value should be measured with your care provider. Moreover, this application will evaluate the concussion symptoms, with respect to the SCAT3 test. All results, heartbeat and symptoms evaluation will be sent to the user's care provider at the end of the session.  The application will monitor the heartbeat with a heart sensor and will limit the user within her/his heart boundaries.  This application should not replace a doctor’s consultation but should be used hands-in-hand with your trainer/health professional.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("About Us").create();
                aboutAlert.show();
                return true;
            case R.id.Switch2:

                AlertDialog.Builder helpAlert = new AlertDialog.Builder(this);
                helpAlert.setMessage("Help menu")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("Help").create();
                helpAlert.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            loginusername.setText("");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            loginpassword.setText("");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}