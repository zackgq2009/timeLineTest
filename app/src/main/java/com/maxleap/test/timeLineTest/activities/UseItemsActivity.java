package com.maxleap.test.timeLineTest.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.maxleap.MLAnalytics;
import com.maxleap.MLGameAnalytics;
import com.maxleap.test.timeLineTest.BaseActivity;
import com.maxleap.test.timeLineTest.R;

/**
 * Created by mrseasons on 2015/07/22.
 */
public class UseItemsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        final String[] items = {
                "物品1", "物品2", "物品3"
        };


        ListView listView = (ListView) findViewById(R.id.list);
        final Apt adapter = new Apt(this,
                items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!settings.hasItem(i)) {
                    Toast.makeText(context, "没有该物品", Toast.LENGTH_SHORT).show();
                    return;
                }
                settings.useItem(i, 1);
                MLGameAnalytics.onItemUse(items[i], 1);

                new AlertDialog.Builder(context)
                        .setTitle("使用成功")
                        .setMessage("恭喜!")
                        .setPositiveButton("OK", null)
                        .show();
                adapter.notifyDataSetInvalidated();
            }
        });
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
            text2.setText("" + settings.itemsNumber[position]);

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLAnalytics.onPageStart("useItems");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MLAnalytics.onPageEnd("useItems");
    }
}
