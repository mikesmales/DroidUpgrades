package com.droidupgrades.demoapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.droidupgrades.widget.control.SlideToActivateButton;

public class SlideToActivateActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidetoactivate);

        SlideToActivateButton slideToActivateBtn = (SlideToActivateButton) findViewById(R.id.slide_to_activate);
        slideToActivateBtn.setSliderImage(R.drawable.circle);
        slideToActivateBtn.setOnClickListener(onSlideToActivateClick);
    }

    private View.OnClickListener onSlideToActivateClick = new View.OnClickListener() {

        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Activated!", Toast.LENGTH_SHORT).show();
        }
    };
}