package com.droidupgrades.demoapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button percentBarBtn = (Button) findViewById(R.id.btn_percentBar);
        percentBarBtn.setOnClickListener(onPercentBarClick);

        Button slideToActivateBtn = (Button) findViewById(R.id.btn_slideToActivate);
        slideToActivateBtn.setOnClickListener(onSlideToActivateClick);

        Button holdToLockBtn = (Button) findViewById(R.id.btn_holdToLock);
        holdToLockBtn.setOnClickListener(onHoldToLockClick);
    }

    private void showPercentBarActivity() {
        Intent intent = new Intent(this, PercentBarActivity.class);
        startActivity(intent);
    }

    private void showSlideToActivateActivity() {
        Intent intent = new Intent(this, SlideToActivateActivity.class);
        startActivity(intent);
    }

    private void showHoldToLockActivity() {
        Intent intent = new Intent(this, HoldToLockActivity.class);
        startActivity(intent);
    }

    private View.OnClickListener onPercentBarClick = new View.OnClickListener() {

        public void onClick(View v) {
            showPercentBarActivity();
        }
    };

    private View.OnClickListener onSlideToActivateClick = new View.OnClickListener() {

        public void onClick(View v) {
            showSlideToActivateActivity();
        }
    };

    private View.OnClickListener onHoldToLockClick = new View.OnClickListener() {

        public void onClick(View v) {
            showHoldToLockActivity();
        }
    };
}