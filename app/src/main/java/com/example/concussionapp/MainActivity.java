package com.example.concussionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static boolean databaseHasBeenCleared = false;
    public static boolean ryanInDatabase = false;
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        getApplicationContext();
        db= DBHandler.getInstance(this); //singleton pattern



    }

    public void to_second_activity(View view) {
 //this commented out code was just used for testing
       //if the database has not been cleared
/*        if(!databaseHasBeenCleared) {
            db.deleteAllUsers();
            databaseHasBeenCleared = true;
        }
*/
        //If I haven't been added to the database then add me to the database
        if(!ryanInDatabase) {
            User ryan = new User("Ryan", "secret_passord", "ryan@email.com");
            db.addUser(ryan);
            ryanInDatabase = true;
        }
        Intent intent = new Intent(this, second_Activity.class);
    //    intent.putExtra("course_keys", db.getFloatList());
        startActivity(intent);
    }
}
