package com.maxleap.test.timeLineTest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;

import com.maxleap.MLAnalytics;
import com.maxleap.test.timeLineTest.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        findViewById(R.id.play_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MissionListActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLAnalytics.onPageStart("mainActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MLAnalytics.onPageEnd("mainActivity");
    }

}
