package com.maxleap.test.timeLineTest.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.maxleap.MLPushBroadcastReceiver;

/**
 * Created by johnny on 15/9/1.
 */
public class GetUri  extends MLPushBroadcastReceiver{
    @Override
    protected Uri getUri(Intent intent) {
        return Uri.parse("http://www.baidu.com");
    }
    @Override
    protected void startIntent(Context context, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        super.startIntent(context, intent);
    }
}
