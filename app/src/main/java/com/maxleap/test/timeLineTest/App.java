package com.maxleap.test.timeLineTest;

import android.app.Application;
import com.maxleap.MaxLeap;
import com.maxleap.TestUtils;

/**
 * Created by mrseasons on 2015/07/22.
 */
public class App extends Application {

    public static final String TEST_APP_ID = "57844ba0169e7d00013e63ba";
    public static final String TEST_API_KEY = "NXFlcnd3LXk0cS1sVmNUV05qZWNqQQ";

    @Override
    public void onCreate() {
        super.onCreate();
//        TestUtils.useDevEnv();

//        TestUtils.useUatEnv();
        TestUtils.debug();
        MaxLeap.setLogLevel(MaxLeap.LOG_LEVEL_VERBOSE);
        MaxLeap.Options options = new MaxLeap.Options();
        options.marketingEnable = true;
        options.appId = TEST_APP_ID;
        options.clientKey = TEST_API_KEY;
        options.serverRegion = MaxLeap.REGION_CN;
        MaxLeap.initialize(this, options);
//        TestUtils.useHttp();
    }
}
