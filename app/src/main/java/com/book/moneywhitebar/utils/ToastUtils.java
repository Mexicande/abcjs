package com.book.moneywhitebar.utils;

import android.widget.Toast;

import com.book.moneywhitebar.App;


/**
 * Created by apple on 2017/4/6.
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(String message) {
        if (toast == null) {
            Toast.makeText(App.getApp(), message, Toast.LENGTH_SHORT).show();
        } else {
            toast.setText(message);
            toast.show();
        }
    }
}
