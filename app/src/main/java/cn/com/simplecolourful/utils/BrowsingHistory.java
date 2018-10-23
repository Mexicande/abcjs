package cn.com.simplecolourful.utils;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.com.simplecolourful.App;
import cn.com.simplecolourful.R;
import cn.com.simplecolourful.common.Contacts;
import cn.com.simplecolourful.listener.OnRequestDataListener;
import cn.com.simplecolourful.net.Api;
import cn.com.simplecolourful.net.ApiService;


/**
 *
 * @author tantan
 * @date 2017/7/14
 * 统计
 */

public class BrowsingHistory {


    public  void execute(final String prdId) {

        String token = SPUtil.getString(Contacts.TOKEN);
        String netError = App.getApp().getString(R.string.app_name);
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        map.put("id",prdId);
        map.put("name",netError);
        ApiService.GET_SERVICE(Api.APPLY, map, new OnRequestDataListener() {
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
