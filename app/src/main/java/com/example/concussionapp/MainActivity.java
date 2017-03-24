package com.example.concussionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

// just want to switch branch, but i have to commit
public class MainActivity extends AppCompatActivity
{

    private static final String TAG = MainActivity.class.getSimpleName();
    private static boolean databaseHasBeenCleared = false;
    public static boolean ryanInDatabase = false;
    DBHandler db;
    Button about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        getApplicationContext();
        db= DBHandler.getInstance(this); //singleton pattern


        about = (Button) findViewById(R.id.aboutButton);
        about.setOnClickListener(OnClickAboutButton);

    }

    public void to_second_activity(View view)
    {
        //this commented out code was just used for testing
       //if the database has not been cleared
/*        if(!databaseHasBeenCleared) {
            db.deleteAllUsers();
            databaseHasBeenCleared = true;
        }
*/
        //If I haven't been added to the database then add me to the database
/*        if(!ryanInDatabase)
        {
            User ryan = new User("Ryan", "secret_passord", "ryan@email.com");
            db.addUser(ryan);
            ryanInDatabase = true;

        }
*/
        Intent intent = new Intent(this, HomeActivity.class);
    //    intent.putExtra("course_keys", db.getFloatList());
        startActivity(intent);
        }

    public void to_quickstart(View view)
    {


        Intent intent = new Intent(this, Chronometer_Heart_Rate_Activity.class);
        //    intent.putExtra("course_keys", db.getFloatList());
        startActivity(intent);
    }

    public void toAbout() //function to send the user to the next activity
    {
        Intent intent = new Intent (this,AboutActivity.class);
        startActivity(intent);
    }

    private Button.OnClickListener OnClickAboutButton = new Button.OnClickListener()
    {
        @Override
        public void onClick(View v) { toAbout(); }
    };

}
