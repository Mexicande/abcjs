package cn.com.simpleapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.com.simpleapp.R;

/**
 *
 * @author apple
 *
 */
public class ProductActivity extends AppCompatActivity {


    public static void launch(Context context, String identity) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("identity", identity);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
    }
}
