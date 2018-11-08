package com.example.expense.DoubleFlower.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.expense.DoubleFlower.R;
import com.example.expense.DoubleFlower.base.BaseActivity;
import com.example.expense.DoubleFlower.utils.NetworkUtils;
import com.example.expense.DoubleFlower.utils.ToastUtils;

/**
 * @author apple
 */
public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.apply)
    Button apply;
    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @OnClick({R.id.toolbar_back, R.id.apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.apply:
                boolean available = NetworkUtils.isAvailable(this);
                if (available) {
                    if (!TextUtils.isEmpty(etMessage.getText().toString())) {
                        ToastUtils.showToast("感觉您的宝贵意见，我们将稍后作答");
                        finish();
                    }
                } else {
                    ToastUtils.showToast("网络无法连接");
                }
                break;
            default:
                break;
        }


    }
    private void initView () {
        toolbarBack.setVisibility(View.VISIBLE);
        toolbarTitle.setText("意见反馈");
    }
}
