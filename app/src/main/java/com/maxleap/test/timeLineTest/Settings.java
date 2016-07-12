package com.maxleap.test.timeLineTest;

import android.content.Context;
import com.maxleap.MLGameAnalytics;
import com.maxleap.MLIapTransaction;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by mrseasons on 2015/07/22.
 */
public class Settings {

    public List<String> missions;
    public List<Boolean> missionsEnabled;
    public String currentMission;
    public int currentCoins;
    public Integer[] itemsNumber = {0, 0, 0};
    public Integer[] itemUnitPrice = {10, 20, 30};
    public final String[] items = {
            "物品1", "物品2", "物品3"
    };

    public Settings(Context context) {
        missions = Arrays.asList(
                "关卡1", "关卡2", "关卡3", "关卡4", "关卡5"
        );
        missionsEnabled = Arrays.asList(
                true, false, false, false, false
        );
    }


    public void setMissionPos(int pos) {
        currentMission = missions.get(pos);
    }

    public boolean nextMission() {
        int index = missions.indexOf(currentMission);
        index++;
        if (missions.size() > index) {
            currentMission = missions.get(index);
            missionsEnabled.set(index, true);
            return true;
        }
        return false;
    }

    public boolean hasNextMission() {
        int index = missions.indexOf(currentMission);
        index++;
        if (missions.size() > index) {
            return true;
        }
        return false;
    }

    public void addCoin(int coin) {
        currentCoins += coin;
    }

    public void addItems(int pos, int count) {
        itemsNumber[pos] += count;
        currentCoins -= count * itemUnitPrice[pos];
    }

    public void useItem(int pos, int count) {
        itemsNumber[pos] -= count;
    }

    public boolean hasItem(int pos) {
        return itemsNumber[pos] > 0;
    }

    public void rewardMission() {
        int index = missions.indexOf(currentMission);
        switch (index) {
            case 0:
                itemsNumber[0] += 1;
                itemsNumber[1] += 1;
                itemsNumber[2] += 1;
                MLGameAnalytics.onItemSystemReward(items[0], "物品", 1, "系统赠送");
                MLGameAnalytics.onItemSystemReward(items[1], "物品", 1, "系统赠送");
                MLGameAnalytics.onItemSystemReward(items[2], "物品", 1, "系统赠送");
                break;
            case 1:
                currentCoins += 100;
                MLIapTransaction transaction = new MLIapTransaction(
                        "金币", MLIapTransaction.TYPE_IN_APP, 0, null
                );
                transaction.setVirtualCurrencyAmount(100);
                MLGameAnalytics.onChargeSystemReward(transaction, "系统赠送");
                break;
            case 2:
                currentCoins += 50;
                Random random = new Random();
                int i = random.nextInt(itemsNumber.length - 1);
                itemsNumber[i] += 1;

                MLIapTransaction transaction1 = new MLIapTransaction(
                        "金币", MLIapTransaction.TYPE_IN_APP, 0, null
                );
                transaction1.setVirtualCurrencyAmount(50);
                MLGameAnalytics.onChargeSystemReward(transaction1, "系统赠送");

                MLGameAnalytics.onItemSystemReward(items[i], "物品", 1, "系统赠送");
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }
}
