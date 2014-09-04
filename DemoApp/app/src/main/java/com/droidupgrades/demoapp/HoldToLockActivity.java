package com.droidupgrades.demoapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.droidupgrades.widget.control.HoldToLock;

public class HoldToLockActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holdtolock);

        HoldToLock holdToLock = (HoldToLock) findViewById(R.id.holdToLockBtn);
        holdToLock.setOnClickListener(onHoldToLockClick);
    }

    private View.OnClickListener onHoldToLockClick = new View.OnClickListener() {

        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "Locked!", Toast.LENGTH_SHORT).show();
        }
    };
}