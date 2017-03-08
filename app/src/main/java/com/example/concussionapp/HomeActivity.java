package com.example.concussionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//create an instance of SQLite database
        dbHandler = DBHandler.getInstance(this);
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
                //Create an object of users to get the password stored

                User Checkpass = dbHandler.fetchUser(username);

                Log.i(TAG, "Username " + username);
                Log.i(TAG, "Password " + password);
                Log.i(TAG, "Stored password " + Checkpass.getPassWord());

                //check if stored password matches user password
                if (password.equals(Checkpass.getPassWord()))
                {
                    Toast.makeText(HomeActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();

                }
                else
                {
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