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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //initialize all values to 1
        for(int i =0; i< questionValues.length; i++) {
            questionValues[i] = "1";
        }
        questionValues = new String [arraySize];
        Intent intent = new Intent();
        passToEmail = intent.getStringExtra("allData");

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
   //     listenerNeck();
     //   listenerLight();
        listenerNoise();
     //   listenerNervous();
       // listenerRight();

        difficultyConcentrating();
        feelingFog();
        naseaL();
        //My shit

    }

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
                Log.i(TAG, "Nausea: " + HeadacheRating);
                //            SharedData.data += "Headache rating: " + HeadacheRating + "\n";
                questionValues[6] = "Nausea: " + rateNausea + "\n";

                //Toast.makeText(Questionaire.this, HeadacheRating, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void feelingFog() {

        feelingInFog = (RatingBar) findViewById(R.id.ratingBar6);

        feelingInFog.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                fogFeeling = String.valueOf(rating);
                Log.i(TAG, "Rating: " + HeadacheRating);
                //            SharedData.data += "Headache rating: " + HeadacheRating + "\n";
                questionValues[5] = "Feeling in Fog: " + fogFeeling + "\n";

                //Toast.makeText(Questionaire.this, HeadacheRating, Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void difficultyConcentrating() {

        DifficultyConcentrating = (RatingBar) findViewById(R.id.ratingBar5);

        DifficultyConcentrating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                concentratingString = String.valueOf(rating);
                Log.i(TAG, "Rating: " + HeadacheRating);
                //            SharedData.data += "Headache rating: " + HeadacheRating + "\n";
                questionValues[4] = "Difficulty Concentrating: " + DifficultyConcentrating + "\n";

                //Toast.makeText(Questionaire.this, HeadacheRating, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void listenerHeadache() {

        HeadacheRatingBar = (RatingBar) findViewById(R.id.HeadacheRate);

        HeadacheRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                HeadacheRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + HeadacheRating);
    //            SharedData.data += "Headache rating: " + HeadacheRating + "\n";
                questionValues[0] = "Headache rating: " + HeadacheRating + "\n";

                //Toast.makeText(Questionaire.this, HeadacheRating, Toast.LENGTH_SHORT).show();

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
                Log.i(TAG, "Rating: " + DizzyRating);
 //               SharedData.data  += "Dizzy rating: " + DizzyRating + "\n";
                questionValues[2] = "Dizzy rating: " + DizzyRating + "\n";
                //Toast.makeText(Questionaire.this, DizzyRating, Toast.LENGTH_SHORT).show();
                //     }
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
                Log.i(TAG, "Rating: " + PressureRating);
   //             SharedData.data  += "Head Pressure rating: " + PressureRating + "\n";
                questionValues[1] = "Head Pressure rating: " + PressureRating + "\n";
                //Toast.makeText(Questionaire.this, PressureRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }
/*
    public void listenerNeck() {

        NeckRatingBar = (RatingBar) findViewById(R.id.NeckRate);

        NeckRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                NeckRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + NeckRating);
  //              SharedData.data  += "Neck pain rating: " + NeckRating + "\n";
                questionValues[3] = "Neck pain rating: " + NeckRating + "\n";
                //Toast.makeText(Questionaire.this, NeckRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }

    public void listenerLight() {

        LightRatingBar = (RatingBar) findViewById(R.id.LightRate);

        LightRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                LightRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + LightRating);
    //            SharedData.data  += "Light sensitivity rating: " + LightRating + "\n";
                questionValues[4] = "Light sensitivity rating: " + LightRating + "\n";
                //Toast.makeText(Questionaire.this, LightRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }
*/
    //this one is for light & noise sensitivity
    public void listenerNoise() {

        NoiseRatingBar = (RatingBar) findViewById(R.id.ratingBar4);

        NoiseRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                NoiseRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + NoiseRating);
    //            SharedData.data  += "Noise sensitivity rating: " + NoiseRating + "\n";
                questionValues[3] = "Light and noise sensitivity rating: " + NoiseRating + "\n";
                //Toast.makeText(Questionaire.this, NoiseRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }
/*
    public void listenerNervous() {

        NervousRatingBar = (RatingBar) findViewById(R.id.NervousRate);

        NervousRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                NervousRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + NervousRating);
        //        SharedData.data  += "Anxiety/Nervousness rating: " + NervousRating + "\n";
                questionValues[6] = "Anxiety/Nervousness rating: " + NervousRating + "\n";
                //Toast.makeText(Questionaire.this, NervousRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }

    public void listenerRight() {

        RightRatingBar = (RatingBar) findViewById(R.id.RightRate);

        RightRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                RightRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + RightRating);
          //      SharedData.data  += "Feeling right rating: " + RightRating + "\n";
                questionValues[7] = "Feeling right rating: " + RightRating + "\n";
                //Toast.makeText(Questionaire.this, RightRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }
*/
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
                String hugeString = "";
                for(int i = 0; i < arraySize; i++) {
                    hugeString += questionValues[i];
                }
                SharedData.dataArray[2] = hugeString;

               startActivity(intent2);
            }
        });
    }

}
