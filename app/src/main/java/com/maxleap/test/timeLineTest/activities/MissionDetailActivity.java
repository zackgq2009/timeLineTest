package com.maxleap.test.timeLineTest.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.maxleap.MLAnalytics;
import com.maxleap.MLGameAnalytics;
import com.maxleap.test.timeLineTest.BaseActivity;
import com.maxleap.test.timeLineTest.R;

/**
 * Created by mrseasons on 2015/07/22.
 */
public class MissionDetailActivity extends BaseActivity {

    private Apt adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        setTitle(settings.currentMission);

        String[] menus = {
                "购买金币", "购买物品", "使用物品", "冲关成功", "冲关失败", "暂停冲关"
        };

        ListView listView = (ListView) findViewById(R.id.list);
        adapter = new Apt(this,
                menus);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(BuyCoinsActivity.class);
                        break;
                    case 1:
                        startActivity(BuyItemsActivity.class);
                        break;
                    case 2:
                        startActivity(UseItemsActivity.class);
                        break;
                    case 3:
                        settings.rewardMission();
                        MLGameAnalytics.onMissionComplete(settings.currentMission);
                        adapter.notifyDataSetInvalidated();

                        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                .setTitle("冲关成功")
                                .setMessage("恭喜!")
                                .setNegativeButton("重玩", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MLGameAnalytics.onMissionBegin(settings.currentMission);
                                    }
                                });
                        if (settings.hasNextMission()) {

                            builder.setPositiveButton("下一关", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    settings.nextMission();
                                    MLGameAnalytics.onMissionBegin(settings.currentMission);

                                    startActivity(MissionDetailActivity.class);
                                    finish();
                                }
                            });

                        }
                        builder.show();
                        break;
                    case 4:
                        MLGameAnalytics.onMissionFailed(settings.currentMission, "冲关失败");
                        new AlertDialog.Builder(context)
                                .setTitle("冲关失败")
                                .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .setNegativeButton("重玩", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MLGameAnalytics.onMissionBegin(settings.currentMission);
                                    }
                                }).show();
                        break;
                    case 5:
                        MLGameAnalytics.onMissionPause(settings.currentMission);
                        new AlertDialog.Builder(context)
                                .setTitle("暂停冲关")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MLGameAnalytics.onMissionResume(settings.currentMission);
                                    }
                                }).show();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                MLGameAnalytics.onMissionFailed(settings.currentMission, "退出");
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class Apt extends ArrayAdapter<String> {

        public Apt(Context context, String[] objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.two_items, parent, false);
            TextView text1 = (TextView) convertView.findViewById(R.id.text1);
            text1.setText(getItem(position));

            TextView text2 = (TextView) convertView.findViewById(R.id.text2);
            if (position == 0) {
                text2.setVisibility(View.VISIBLE);
                text2.setText("" + settings.currentCoins);
            } else {
                text2.setVisibility(View.GONE);
            }

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetInvalidated();
        }
        MLAnalytics.onPageStart("missionDetail");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MLAnalytics.onPageEnd("missionDetail");
    }
}
