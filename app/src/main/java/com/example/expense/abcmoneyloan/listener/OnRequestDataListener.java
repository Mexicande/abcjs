package com.example.expense.abcmoneyloan.listener;


import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/10.
 * Author: XuDeLong
 */
public interface OnRequestDataListener {
    void requestSuccess(int code, JSONObject json);

    void requestFailure(int code, String msg);
    void onFinish();
}
