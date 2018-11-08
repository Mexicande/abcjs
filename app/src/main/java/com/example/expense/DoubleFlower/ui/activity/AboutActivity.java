package com.example.expense.DoubleFlower.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.expense.DoubleFlower.R;
import com.example.expense.DoubleFlower.base.BaseActivity;
import com.example.expense.DoubleFlower.utils.AppUtils;

/**
 * @author apple
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.toolbar_back)
    ImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.version)
    TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }
    private void initView() {

        toolbarBack.setVisibility(View.VISIBLE);
        toolbarTitle.setText("关于我们");
        String appVersionName = AppUtils.getAppVersionName();
        version.setText(getString(R.string.app_name) + "V" + appVersionName);


    }
    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }
}
