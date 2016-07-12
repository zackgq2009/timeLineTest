package com.maxleap.test.timeLineTest.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.maxleap.MLAnalytics;
import com.maxleap.MLDataManager;
import com.maxleap.MLGameAnalytics;
import com.maxleap.MLMarketing;
import com.maxleap.MLObject;
import com.maxleap.MLUser;
import com.maxleap.MLUserManager;
import com.maxleap.SaveCallback;
import com.maxleap.SignUpCallback;
import com.maxleap.exception.MLException;
import com.maxleap.test.timeLineTest.BaseActivity;
import com.maxleap.test.timeLineTest.R;

import java.util.List;

/**
 * Created by mrseasons on 2015/07/22.
 */
public class MissionListActivity extends BaseActivity {

    private Apt adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        setTitle("关卡选择");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new Apt(this,
                settings.missions);
        listView.setAdapter(adapter);

        MLUser user = MLUser.getCurrentUser();
        if (user == null){
            user = new MLUser();
            user.setUserName("new user 003");
            user.setPassword("new password");
            MLUserManager.signUpInBackground(user, new SignUpCallback() {
                @Override
                public void done(MLException e) {
                    if (e == null) {
                        System.out.println("注册成功");
                    } else {
                        e.printStackTrace();
                    }
                }
            });

        }


        MLObject object = new MLObject("first object");
        MLDataManager.saveInBackground(object, new SaveCallback() {
            @Override
            public void done(MLException e) {
                if (e == null) {
                    System.out.println("aaaaaaaaaaaaaaaaa");
                } else {
                    e.printStackTrace();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {





                if (!settings.missionsEnabled.get(i)) {
                    return;
                }
                settings.setMissionPos(i);

                MLGameAnalytics.onMissionBegin(settings.currentMission);
                startActivity(MissionDetailActivity.class);
            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MissionListActivity.this,SessionOutActivity.class);
                startActivity(in);
            }
        });
    }




    class Apt extends ArrayAdapter<String> {

        public Apt(Context context, List<String> objects) {
            super(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);

            TextView text1 = (TextView) convertView.findViewById(android.R.id.text1);
            if (settings.missionsEnabled.get(position)) {
                text1.setTextColor(Color.BLACK);
            } else {
                text1.setTextColor(Color.LTGRAY);
            }

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.notifyDataSetInvalidated();
        MLMarketing.setInAppMessageDisplayActivity(this);
        MLAnalytics.onPageStart("missionList");
    }

    @Override
    protected void onPause() {
        super.onPause();

        MLMarketing.dismissCurrentInAppMessage();

        MLMarketing.clearInAppMessageDisplayActivity();
        MLAnalytics.onPageEnd("missionList");
    }
}
