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
/*
                username.setFilters(new InputFilter[] {
                        new InputFilter() {
                            public CharSequence filter(CharSequence src, int start,
                                                       int end, Spanned dst, int dstart, int dend) {

                                if(src.equals("")){ // for backspace
                                    return src;
                                }
                                if(src.toString().matches("[a-zA-Z0-9 ]*")) //put your constraints here
                                {
                                    return src;
                                }
                                return "";
                            }
                        }
                });
//
                //
           /*     InputFilter[] filters = new InputFilter[1];
                filters[0] = new InputFilter(){
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (end > start) {

                            char[] acceptedChars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '&', ' '};

                            for (int index = start; index < end; index++) {
                                if (!new String(acceptedChars).contains(String.valueOf(source.charAt(index)))) {
                                    return "";
                                }
                            }
                        }
                        return null;
                    }

                };
                loginpassword.setFilters(filters);
                loginusername.setFilters(filters);

//*/
//

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
                aboutAlert.setMessage(" NoCussion is a tool designed for any member of the medical team who is treating athletes recovering from a concussion.  The application gives information on concussion and tips for a better recovery process. NoCussion guide the athletes throughout their recovery. The user will have her/his heart beat monitor during their training and will be asked to rate their concussion symptom after a physical effort. All data will be sent to the athlete’s care provider by email.  NoCussion facilitates the communication between the athlete and the care provider during the recovery process. ")
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
                helpAlert.setMessage("Please input your username and password to log in succesfully")
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