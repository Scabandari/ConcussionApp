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




    private String headPressureRating;
    private String sensitivityToNoiseRating;
    private String sensitivityToLightRating;
    private String nauseaRating;
    private String headacheRating;
    private String dizzinessRating;

    private String passToExercise;



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
        listenerHeadPressure();
        listenerDizziness();
        listenerHeadache();
        goToExercise();

    }

    public void listenerHeadPressure() {

        headPressureRatingBar = (RatingBar) findViewById(R.id.headPressure);

        headPressureRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                headPressureRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + headPressureRating);
                passToExercise += "Head Pressure rating: " + headPressureRating + "\n";
                Toast.makeText(Questionaire.this, headPressureRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }


    public void listenerDizziness() {

        dizzinessRB = (RatingBar) findViewById(R.id.dizziness);

       dizzinessRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                dizzinessRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + dizzinessRating);
                passToExercise += "Dizziness rating: " + dizzinessRating + "\n";
                Toast.makeText(Questionaire.this, dizzinessRating, Toast.LENGTH_SHORT).show();
                //     }
            }
        });
    }



    public void listenerHeadache() {

        headache = (RatingBar) findViewById(R.id.headache);

        headache.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            //       ratingBar4.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //  if(fromUser) {
                headacheRating = String.valueOf(rating);
                Log.i(TAG, "Rating: " + headacheRating);
                passToExercise += "Headache rating: " + headacheRating + "\n";
                Toast.makeText(Questionaire.this, headacheRating, Toast.LENGTH_SHORT).show();
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



                Intent intent = new Intent(getApplicationContext(),Excercise_Setup_Activity.class);
                intent.putExtra("surveyData", passToExercise);
                Log.i(TAG, "All survey data: " +passToExercise);
                startActivity(intent);
            }
        });
    }

}
