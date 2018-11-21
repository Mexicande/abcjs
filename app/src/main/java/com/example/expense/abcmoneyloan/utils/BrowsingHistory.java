package com.example.expense.abcmoneyloan.utils;


import com.meituan.android.walle.WalleChannelReader;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.expense.abcmoneyloan.App;
import com.example.expense.abcmoneyloan.R;
import com.example.expense.abcmoneyloan.common.Contacts;
import com.example.expense.abcmoneyloan.listener.OnRequestDataListener;
import com.example.expense.abcmoneyloan.net.Api;
import com.example.expense.abcmoneyloan.net.ApiService;


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
            jsonObject.put("app_name","向钱贷");
            jsonObject.put("channel","xiangqiandai");
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
