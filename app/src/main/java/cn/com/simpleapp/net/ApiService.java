package cn.com.simpleapp.net;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.com.simpleapp.App;
import cn.com.simpleapp.R;
import cn.com.simpleapp.listener.OnRequestDataListener;


/**
 * Created by apple on 2018/4/13.
 */

public class ApiService {
    /**
     * @param params
     * @param listener
     * banner
     */
    public static void GET_SERVICE(String url,Map<String,String> params, final OnRequestDataListener listener) {
        newExcuteJsonPost(url,params,listener);
    }

    private static void newExcuteJsonPost(String url, Map<String,String> params, final OnRequestDataListener listener){
        final String netError = App.getApp().getString(R.string.net_error);
        OkGo.<String>post(url)
                .tag(App.getApp())
                .params(params,false)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(response.body()!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(response.body());
                                int code = jsonObject.getInt("error_code");
                                if(code==0){
                                    listener.requestSuccess(code, jsonObject);
                                }else {
                                    listener.requestFailure(code, jsonObject.getString("error_message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            listener.requestFailure(-1, netError);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        listener.requestFailure(-1, netError);

                    }
                });


    }


    /**
     * @param jsonObject
     * @param listener
     * banner
     */
    public static void JSONGET_SERVICE(String url, JSONObject jsonObject, final OnRequestDataListener listener) {
        JSONnewExcuteJsonPost(url,jsonObject,listener);
    }

    private static void JSONnewExcuteJsonPost(String url,  JSONObject jsonObject, final OnRequestDataListener listener){
        final String netError = App.getApp().getString(R.string.net_error);
        OkGo.<String>post(url)
                .tag(App.getApp())
                .upJson(jsonObject)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if(response.body()!=null){
                            try {
                                JSONObject jsonObject=new JSONObject(response.body());
                                int code = jsonObject.getInt("error_code");
                                if(code==0){
                                    listener.requestSuccess(code, jsonObject);
                                }else {
                                    listener.requestFailure(code, jsonObject.getString("error_message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            listener.requestFailure(-1, netError);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        listener.requestFailure(-1, netError);

                    }
                });


    }

}
