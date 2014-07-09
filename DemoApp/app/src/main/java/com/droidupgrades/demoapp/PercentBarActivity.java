package com.droidupgrades.demoapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.droidupgrades.widget.progress.PercentBar;

public class PercentBarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percent_bar);


        PercentBar percentBarOne = (PercentBar) findViewById(R.id.percentBarOne);
        percentBarOne.setPercentage(75);

        PercentBar percentBarTwo = (PercentBar) findViewById(R.id.percentBarTwo);
        percentBarTwo.setPercentage(25);
    }


}
