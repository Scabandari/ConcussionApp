package com.example.concussionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class Questionaire extends AppCompatActivity {

    private static final String TAG = Questionaire.class.getSimpleName();
    private RatingBar headPressureRatingBar;
    private RatingBar sensitivityToLight;
    private RatingBar sensitivityToNoise;
    private RatingBar nauseaRatingBar;
    private RatingBar headache;
    private RatingBar dizzinessRB;


    //MY SHIT
    private RatingBar HeadacheRatingBar;
    private RatingBar DizzyRatingBar;
    private RatingBar PressureRatingBar;
    private RatingBar NeckRatingBar;
    private RatingBar LightRatingBar;
    private RatingBar NoiseRatingBar;
    private RatingBar NervousRatingBar;
    private RatingBar RightRatingBar;

    private String HeadacheRating;
    private String DizzyRating;
    private String PressureRating;
    private String NeckRating;
    private String LightRating;
    private String NoiseRating;
    private String NervousRating;
    private String RightRating;
    //MY shit

    private String headPressureRating;
    private String sensitivityToNoiseRating;
    private String sensitivityToLightRating;
    private String nauseaRating;
    private String headacheRating;
    private String dizzinessRating;

    private String passToExercise;

    private String getUserName;
    private String getEmail;


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

        getUserName = "initialized";
        getEmail = "initialized";
        Intent intent = new Intent();
        getUserName = intent.getStringExtra("username");
        getEmail = intent.getStringExtra("email");
        Log.i(TAG, "msg: " +  getUserName);
        Log.i(TAG, "msg: " +  getEmail);
        passToExercise = "User: " + getUserName + "\nSurvey data :\n ";

       // listenerHeadPressure();
       //  listenerDizziness();
       //  listenerHeadache();
        goToExercise();

        //My shit
        listenerHeadache();
        listenerDizzy();
        listenerPressure();
        listenerNeck();
        listenerLight();
        listenerNoise();
        listenerNervous();
        listenerRight();
        //My shit

    }

    public void listenerHeadache() {

        HeadacheRatingBar = (RatingBar) findViewById(R.id.HeadacheRate);

        HeadacheRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                HeadacheRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + HeadacheRating);
                passToExercise += "Headache rating: " + HeadacheRating + "\n";
                Toast.makeText(Questionaire.this, HeadacheRating, 100).show();

            }
        });
    }

    public void listenerDizzy() {

        DizzyRatingBar = (RatingBar) findViewById(R.id.DizzyRate);

        DizzyRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                DizzyRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + DizzyRating);
                passToExercise += "Dizzy rating: " + DizzyRating + "\n";
                Toast.makeText(Questionaire.this, DizzyRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }

    public void listenerPressure() {

        PressureRatingBar = (RatingBar) findViewById(R.id.PressureRate);

        PressureRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                PressureRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + PressureRating);
                passToExercise += "Head Pressure rating: " + PressureRating + "\n";
                Toast.makeText(Questionaire.this, PressureRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }

    public void listenerNeck() {

        NeckRatingBar = (RatingBar) findViewById(R.id.NeckRate);

        NeckRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                NeckRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + NeckRating);
                passToExercise += "Neck pain rating: " + NeckRating + "\n";
                Toast.makeText(Questionaire.this, NeckRating, Toast.LENGTH_SHORT).show();
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
                passToExercise += "Light sensitivity rating: " + LightRating + "\n";
                Toast.makeText(Questionaire.this, LightRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }

    public void listenerNoise() {

        NoiseRatingBar = (RatingBar) findViewById(R.id.NoiseRate);

        NoiseRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                NoiseRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + NoiseRating);
                passToExercise += "Noise sensitivity rating: " + NoiseRating + "\n";
                Toast.makeText(Questionaire.this, NoiseRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }

    public void listenerNervous() {

        NervousRatingBar = (RatingBar) findViewById(R.id.NervousRate);

        NervousRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                NervousRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + NervousRating);
                passToExercise += "Anxiety/Nervousness rating: " + NervousRating + "\n";
                Toast.makeText(Questionaire.this, NervousRating, Toast.LENGTH_SHORT).show();
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
                passToExercise += "Feeling right rating: " + RightRating + "\n";
                Toast.makeText(Questionaire.this, RightRating, Toast.LENGTH_SHORT).show();
                //     }
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
                intent2.putExtra("surveyData", passToExercise);
                Log.i(TAG, "All survey data: " + passToExercise);
                startActivity(intent2);
            }
        });
    }

}
