package com.example.expense.DoubleFlower.utils;


import com.meituan.android.walle.WalleChannelReader;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.expense.DoubleFlower.App;
import com.example.expense.DoubleFlower.R;
import com.example.expense.DoubleFlower.common.Contacts;
import com.example.expense.DoubleFlower.listener.OnRequestDataListener;
import com.example.expense.DoubleFlower.net.Api;
import com.example.expense.DoubleFlower.net.ApiService;


/**
 *
 * @author tantan
 * @date 2017/7/14
 * 统计
 */

public class BrowsingHistory {

    public  void execute(final String prdId) {
        String token = SPUtil.getString(Contacts.TOKEN);
        String channel = WalleChannelReader.getChannel(App.getApp());
        String netError = App.getApp().getString(R.string.app_name);

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("token",token);
            jsonObject.put("product_id",prdId);
            jsonObject.put("app_name",netError);
            jsonObject.put("channel","jiehuahua");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.JSONGET_SERVICE(Api.APPLY, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {

            }

            @Override
            public void requestFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });

    }
}
