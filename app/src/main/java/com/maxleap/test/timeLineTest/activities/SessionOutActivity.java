package com.maxleap.test.timeLineTest.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.maxleap.MLAnalytics;

/**
 * Created by johnny on 15/8/19.
 */
public class SessionOutActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button bt = new Button(this);
        bt.setText("back");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       setContentView(bt);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLAnalytics.onPageStart("sessionOut");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MLAnalytics.onPageEnd("sessionOut");
    }


}
