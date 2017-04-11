package com.example.concussionapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class Questionaire extends AppCompatActivity {

    private static final String TAG = Questionaire.class.getSimpleName();



    //MY SHIT
    private RatingBar HeadacheRatingBar;
    private RatingBar DizzyRatingBar;
    private RatingBar PressureRatingBar;
    private RatingBar NeckRatingBar;
    private RatingBar LightRatingBar;
    private RatingBar NoiseRatingBar;
    private RatingBar NervousRatingBar;
    private RatingBar RightRatingBar;

    private RatingBar DifficultyConcentrating;
    private RatingBar feelingInFog;
    private RatingBar Nausea;

    private String HeadacheRating;
    private String DizzyRating;
    private String PressureRating;
    private String NeckRating;
    private String LightRating;
    private String NoiseRating;
    private String NervousRating;
    private String RightRating;
    private String concentratingString;
    private String fogFeeling;
    private String rateNausea;
    //MY shit

    private String[] questionValues;

    private String passToEmail;

    private final int arraySize = 7;

    private int currentApiVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
        }

        //initialize all values to 1

        questionValues = new String [arraySize];
        Intent intent = new Intent();
        passToEmail = intent.getStringExtra("allData");
        for(int i =0; i< questionValues.length; i++) {
            questionValues[i] = "1";
        }

        Log.i(TAG, "Received data from Exercise Session: " + passToEmail);
        Log.i(TAG, "Received data from Exercise Session: " + passToEmail);
        Log.i(TAG, "Received data from Exercise Session: " + passToEmail);
        Log.i(TAG, "Received data from Exercise Session: " + passToEmail);
        Log.i(TAG, "Username is: " + SharedData.user.getUserName() );
        Log.i(TAG, "Email is: " +  SharedData.user.getUserName());
     

        goToExercise();

        //My shit
        listenerHeadache();
        listenerDizzy();
        listenerPressure();

        listenerNoise();


        difficultyConcentrating();
        feelingFog();
        naseaL();
        //My shit

        currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {
            getWindow().getDecorView().setSystemUiVisibility(flags);

            //Code handling the press of volume up and down, without it
            //when pressed, navigation bar show up and doesnt hide

            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
            {
                @Override
                public void onSystemUiVisibilityChange(int visibility)
                {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                    {
                        decorView.setSystemUiVisibility(flags);
                    }
                }

            });
        }
    }



    @Override
    public void onBackPressed() {}

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()){
            case R.id.Switch1:
                //what happens when menu item is pressed

                AlertDialog.Builder aboutAlert = new AlertDialog.Builder(this);
                aboutAlert.setMessage(" NoCussion is a tool designed for any member of the medical team who is treating athletes recovering from a concussion.  The application gives information on concussion and tips for a better recovery process. NoCussion guide the athletes throughout their recovery. The user will have her/his heart beat monitor during their training and will be asked to rate their concussion symptom after a physical effort. All data will be sent to the athlete’s care provider by email.  NoCussion facilitates the communication between the athlete and the care provider during the recovery process.")
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
                helpAlert.setMessage("On this page, you should rate from 1 to 5 how you feel after training. This survey will help your care provider monitor your symptoms after your training sessions.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("Help").create();
                helpAlert.show();
                return true;
            case R.id.Switch3:
                Intent logout = new Intent(Questionaire.this, MainActivity.class);
                startActivity(logout);

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void naseaL() {

        Nausea = (RatingBar) findViewById(R.id.ratingBar7);

        Nausea.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                rateNausea = String.valueOf(rating);
                if(isValidInput(rateNausea)) {
                    Log.i(TAG, "Nausea: " + HeadacheRating);
                    questionValues[6] = "Nausea: " + rateNausea + "\n";
                }
                else {
                    questionValues[6] = "Nausea: " + 0 + "\n";
                }


            }
        });
    }

    public void feelingFog() {

        feelingInFog = (RatingBar) findViewById(R.id.ratingBar6);

        feelingInFog.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                fogFeeling = String.valueOf(rating);
                if(isValidInput(fogFeeling)) {

                    Log.i(TAG, "Rating: " + HeadacheRating);
                    questionValues[5] = "Feeling in Fog: " + fogFeeling + "\n";
                } else {
                    Log.i(TAG, "Rating: " + HeadacheRating);
                    questionValues[5] = "Feeling in Fog: " + 0 + "\n";
                }


            }
        });
    }


    public void difficultyConcentrating() {

        DifficultyConcentrating = (RatingBar) findViewById(R.id.ratingBar5);

        DifficultyConcentrating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                concentratingString = String.valueOf(rating);
                if(isValidInput(concentratingString)) {
                    Log.i(TAG, "Rating: " + concentratingString);
                    questionValues[4] = "Difficulty Concentrating: " + concentratingString + "\n";
                } else {
                    Log.i(TAG, "Rating: " + HeadacheRating);
                    questionValues[4] = "Difficulty Concentrating: " + 0 + "\n";
                }

            }
        });
    }

    public void listenerHeadache() {

        HeadacheRatingBar = (RatingBar) findViewById(R.id.HeadacheRate);

        HeadacheRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                HeadacheRating = String.valueOf(rating);

                if(isValidInput(HeadacheRating)) {
                    Log.i(TAG, "Rating: " + HeadacheRating);
                    questionValues[0] = "Headache rating: " + HeadacheRating + "\n";
                } else {
                    Log.i(TAG, "Rating: " + HeadacheRating);
                    questionValues[0] = "Headache rating: " + 0 + "\n";
                }


        }
        });
    }

    public void listenerDizzy() {

        DizzyRatingBar = (RatingBar) findViewById(R.id.ratingBar3);

        DizzyRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {

                DizzyRating = String.valueOf(rating);
                if(isValidInput(DizzyRating)) {
                    Log.i(TAG, "Rating: " + DizzyRating);

                    questionValues[2] = "Dizzy rating: " + DizzyRating + "\n";
                } else {
                    Log.i(TAG, "Rating: " + DizzyRating);

                    questionValues[2] = "Dizzy rating: " + 0 + "\n";
                }


            }
        });
    }

    public void listenerPressure() {

        PressureRatingBar = (RatingBar) findViewById(R.id.ratingBar2);

        PressureRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                PressureRating = String.valueOf(rating);
                if(isValidInput(PressureRating)) {
                Log.i(TAG, "Rating: " + PressureRating);

                questionValues[1] = "Head Pressure rating: " + PressureRating + "\n";
                 } else {
                    Log.i(TAG, "Rating: " + PressureRating);

                    questionValues[1] = "Head Pressure rating: " + 0 + "\n";
                }
            }
        });
    }

    //this one is for light & noise sensitivity
    public void listenerNoise() {

        NoiseRatingBar = (RatingBar) findViewById(R.id.ratingBar4);

        NoiseRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                NoiseRating = String.valueOf(rating);

                if(isValidInput(NoiseRating)) {
                Log.i(TAG, "Rating: " + NoiseRating);
                questionValues[3] = "Light and noise sensitivity rating: " + NoiseRating + "\n";
                 } else {
                    Log.i(TAG, "Rating: " + NoiseRating);
                    questionValues[3] = "Light and noise sensitivity rating: " + 0 + "\n";
                }
            }
        });
    }

    protected void goToExercise()

    {
        Button buttonsubmit = (Button) findViewById(R.id.submitquiz);

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View Profile) {
             //   startActivity(new Intent(MainActivity.this, Questionaire.class));
            //    Intent intent = new Intent(v.getContext(), Excercise_Setup_Activity.class);
                // intent.putExtra("course_keys", db.getFloatList());
            //    startActivity(intent);



                Intent intent2 = new Intent(getApplicationContext(), SecondQuestionaire.class);
      //          intent2.putExtra("surveyData", passToEmail);
                Log.i(TAG, "All data: " + passToEmail);
                Log.i(TAG, "All  data: " + passToEmail);
                Log.i(TAG, "All  data: " + passToEmail);
                Log.i(TAG, "All  data: " + passToEmail);
                Log.i(TAG, "All  data: " + passToEmail);
                String hugeString = "\n";
                for(int i = 0; i < arraySize; i++) {
                    hugeString += questionValues[i];
                }
                SharedData.dataArray[2] = hugeString;

               startActivity(intent2);
            }
        });
    }
        private boolean isValidInput(String s) {

            boolean flag = false;
            switch(s) {
                case "0":
                    flag = true;
                    break;

                case "1":
                    flag = true;
                    break;

                case "2":
                    flag = true;
                    break;

                case "3":
                    flag = true;
                    break;
                case "4":
                    flag = true;
                    break;

                case "5":
                    flag = true;
                    break;

                default:
                    flag = false;
            }
                    return flag;
        }
}

