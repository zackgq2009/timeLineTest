package com.maxleap.test.timeLineTest.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.maxleap.MLAnalytics;
import com.maxleap.MLGameAnalytics;
import com.maxleap.MLIapTransaction;

import com.maxleap.test.timeLineTest.BaseActivity;
import com.maxleap.test.timeLineTest.R;
import com.maxleap.utils.DeviceInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mrseasons on 2015/07/22.
 */
public class BuyCoinsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);

        setTitle("购买金币");

        System.out.println(DeviceInfo.getLanguage());
        System.out.println(DeviceInfo.getLocale());

        final List<Pair<Integer, String>> pairs = Arrays.asList(
                new Pair<>(100, "$0.99"),
                new Pair<>(1000, "$4.99"),
                new Pair<>(3000, "$9.99"),
                new Pair<>(8000, "$19.99"),
                new Pair<>(25000, "$49.99")
        );
        final Float[] price = {
                0.99f,
                4.99f,
                9.99f,
                19.99f,
                49.99f
        };


        ListView listView = (ListView) findViewById(R.id.list);
        final Apt adapter = new Apt(this,
                pairs);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int coins = pairs.get(i).first;
                settings.addCoin(coins);

                if (i==pairs.size()-1){
                    Map m = new HashMap<String,String>();
                    m.put("name","guoqing");
                    MLAnalytics.logEvent("ASDFASDF", m);
//                    System.out.println(GetLog.getLog(BuyCoinsActivity.this));
                }

                if (i==pairs.size()-2){
                    Map m = new HashMap<String,String>();
                    m.put("age","31");
                    MLAnalytics.logEvent("asdfasdf", m);
                }

                if (i==pairs.size()-3){
                    Map m = new HashMap<String,String>();
                    m.put("height","184");
                    m.put("weight","184");
                    m.put("facebook","184");
                    MLAnalytics.logEvent("qwerqwer", m);
                }

                MLIapTransaction transaction = new MLIapTransaction(
                        "金币", MLIapTransaction.TYPE_IN_APP, (long) (price[i] * 1000000), "USD"
                );
                transaction.setOrderId(UUID.randomUUID().toString());
                transaction.setVirtualCurrencyAmount(coins);

                MLGameAnalytics.onChargeRequest(transaction);
                MLGameAnalytics.onChargeSuccess(transaction);

                new AlertDialog.Builder(context)
                        .setTitle("购买成功")
                        .setMessage("恭喜!")
                        .setPositiveButton("OK", null).show();
                adapter.notifyDataSetInvalidated();
            }
        });
    }

    class Apt extends ArrayAdapter<Pair<Integer, String>> {

        public Apt(Context context, List<Pair<Integer, String>> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.two_items, parent, false);

            Pair<Integer, String> pair = getItem(position);
            TextView text1 = (TextView) convertView.findViewById(R.id.text1);
            text1.setText("" + pair.first);

            TextView text2 = (TextView) convertView.findViewById(R.id.text2);
            text2.setText(pair.second);

            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MLAnalytics.onPageStart("buyCoins");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MLAnalytics.onPageEnd("buyCoins");
    }
}
