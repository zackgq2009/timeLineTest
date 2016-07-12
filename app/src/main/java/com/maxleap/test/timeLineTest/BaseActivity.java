package com.maxleap.test.timeLineTest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import com.maxleap.MLAnalytics;

/**
 * Created by mrseasons on 2015/07/22.
 */
public abstract class BaseActivity extends ActionBarActivity {

    protected static final String TAG = BaseActivity.class.getName();

    protected static Settings settings;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (settings == null) {
            settings = new Settings(getApplicationContext());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void startActivity(Class<?> clazz) {
        startActivity(
                new Intent(context, clazz)
        );
    }


    protected void d(String message) {
        Log.d(TAG, message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MLAnalytics.onPause(this);
    }
}
