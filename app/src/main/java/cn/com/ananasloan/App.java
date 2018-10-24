package cn.com.ananasloan;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.meituan.android.walle.WalleChannelReader;
import com.umeng.analytics.MobclickAgent;

import cn.com.ananasloan.common.Contacts;

/**
 * @author apple
 */
public class App extends Application {
    public static App context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initOkGo();
    }
    public  static App getApp(){
        return context;
    }
    private void initOkGo() {
        String channel = WalleChannelReader.getChannel(this.getApplicationContext());
        HttpParams params=new HttpParams();
        String name = getString(R.string.app_name);
        params.put("market",channel);
        params.put("name", name);
        OkGo.getInstance().init(this)
                .addCommonParams(params);
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, Contacts.Keys.UMENG_KEY
                ,channel));
    }
}
