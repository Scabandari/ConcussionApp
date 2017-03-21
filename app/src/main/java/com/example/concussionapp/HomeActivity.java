package com.example.concussionapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    private static final  String TAG = "HomeActivity";

    DBHandler dbHandler;
    Button btnIn;
    Button btnUp;

/* HAVING PROBLEMS TESTING LOGIN FUNCTIONALITY, SOMETHING TO DO W/
MULTIPLE USERS HAVING SAME USER NAME. Will fix for sprint 2 but for
now This next boolean is to clear the database
and it should only happen once therefore it's a boolean = false before and true after
 */
 //   private boolean databaseIsCleared = false; //must be taken out of final version of project, clears database

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

        //if statement just clears the database but only happens once
  /*      if(!databaseIsCleared) {
            dbHandler.deleteAllUsers();
            databaseIsCleared = true;
        }
        */
        btnIn = (Button) findViewById(R.id.ButtonSignIn);
        btnUp = (Button) findViewById(R.id.ButtonSignUp);

        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start sign up activity
                Intent SignUpintent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(SignUpintent);
            }
        });

        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText loginusername = (EditText) findViewById(R.id.loginusername);
                final EditText loginpassword = (EditText) findViewById(R.id.loginpassword);


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

                    Intent intent = new Intent(v.getContext(), Questionaire.class);
                    // intent.putExtra("course_keys", db.getFloatList());
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "Username or Password incorrect", Toast.LENGTH_LONG).show();

                }

            }
        });

    }


/*    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //close database
        dbHandler.close();
    }
*/
}