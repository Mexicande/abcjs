package cn.com.simpleapp;

import android.app.Application;

/**
 * @author apple
 */
public class App extends Application {
    public static App context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public  static App getApp(){
        return context;
    }
}
