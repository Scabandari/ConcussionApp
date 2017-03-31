package com.example.concussionapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by valeriedube on 17-03-28.
 */
public class NonPop extends Activity
{



    // commenting just to see if new branch is working
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_pop);


        setContentView(R.layout.activity_non_pop);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)( width*.8), (int)(height*.6));

    }

}
