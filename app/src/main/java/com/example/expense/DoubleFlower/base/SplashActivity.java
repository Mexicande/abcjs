package com.example.expense.DoubleFlower.base;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.expense.DoubleFlower.ui.activity.MainActivity;

import java.lang.ref.WeakReference;

import com.example.expense.DoubleFlower.utils.ActivityUtils;
import com.example.expense.DoubleFlower.utils.SharedPreferencesUtil;

/**
 * @author apple
 * loding
 */
public class SplashActivity extends AppCompatActivity {
    private SwitchHandler mHandler = new SwitchHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler.sendEmptyMessageDelayed(2, 1000);
    }
    private static class SwitchHandler extends Handler {
        private WeakReference<SplashActivity> mWeakReference;

        SwitchHandler(SplashActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what){
                    case 2:
                        ActivityUtils.startActivity(MainActivity.class);
                        activity.finish();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    private void setWelcome(){
        boolean isFirstOpen = SharedPreferencesUtil.getBoolean(SplashActivity.this, SharedPreferencesUtil.FIRST_OPEN, true);
        if (isFirstOpen) {
            Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        }else {
          mHandler.sendEmptyMessageDelayed(2, 1000);
        }
    }
}
