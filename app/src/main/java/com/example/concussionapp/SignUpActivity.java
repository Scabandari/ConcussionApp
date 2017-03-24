package com.example.concussionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    EditText SignUpusername;
    EditText SignUppassword;
    EditText Confirmpassword;
    EditText SignUpEmail;

    Button CreateAcc;

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //get Instance of database adapter
        dbHandler = DBHandler.getInstance(this);

        SignUpusername = (EditText) findViewById(R.id.SignUpusername);
        SignUppassword = (EditText) findViewById(R.id.SignUppassword);
        Confirmpassword = (EditText) findViewById(R.id.Confirmpassword);
        SignUpEmail = (EditText) findViewById(R.id.SignUpEmail);


        CreateAcc = (Button) findViewById(R.id.CreateAccount);

        CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = SignUpusername.getText().toString();
                String password = SignUppassword.getText().toString();
                String email = SignUpEmail.getText().toString();
                String confirmPassword = Confirmpassword.getText().toString();

                User NewUser = new User(username,password,confirmPassword,email);

                Log.i(TAG, "Username " + username);
                Log.i(TAG, "Password " + password);
                Log.i(TAG, "Cpassword " + confirmPassword);
                Log.i(TAG, "Email " + email);


                //check if any field is empty
                if(username.equals("") || password.equals("") || confirmPassword.equals("") || email.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_LONG).show();
                    return;
                }
                //check both password inputted are the same
                if (!password.equals(confirmPassword))
                {
                    Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!isValidEmail(NewUser.getCareProviderEmailAddress()))
                {
                    Toast.makeText(getApplicationContext(), "Not a valid email address", Toast.LENGTH_LONG).show();
                    return;
                }

                else
                {
                    //checks if user name is NOT already in data base
                    if(!dbHandler.checkForUserName(NewUser.getUserName())) {
                        //Save data to database
                        dbHandler.addUser(NewUser);
                        Toast.makeText(getApplicationContext(), "Account created succesfully", Toast.LENGTH_LONG).show();
                        Intent SignUpIntent = new Intent(SignUpActivity.this, HomeActivity.class);
                        startActivity(SignUpIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Username already exists.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //close database
        dbHandler.close();
    }

    //this function was taken from stackoverflow here:
    // http://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}


