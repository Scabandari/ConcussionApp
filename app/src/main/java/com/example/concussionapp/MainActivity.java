package com.example.concussionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

// just want to switch branch, but i have to commit
public class MainActivity extends AppCompatActivity
{

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
    public void onStart()
    {
        super.onStart();
        getApplicationContext();
        db= DBHandler.getInstance(this); //singleton pattern



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

                    //what happems when menu item is pressed

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void to_home_activity(View view)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    public void to_quickstart(View view)
    {


        Intent intent = new Intent(this, Chronometer_Heart_Rate_Activity.class);
        //    intent.putExtra("course_keys", db.getFloatList());
        startActivity(intent);
    }

    public void to_signup_activity(View view)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);

    }

}
