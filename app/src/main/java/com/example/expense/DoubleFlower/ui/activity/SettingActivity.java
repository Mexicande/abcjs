package com.example.expense.DoubleFlower.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.expense.DoubleFlower.R;
import com.example.expense.DoubleFlower.base.BaseActivity;
import com.example.expense.DoubleFlower.common.Contacts;
import com.example.expense.DoubleFlower.model.LoginEvent;
import com.example.expense.DoubleFlower.utils.ActivityUtils;
import com.example.expense.DoubleFlower.utils.SPUtil;
import com.example.expense.DoubleFlower.utils.ToastUtils;

/**
 * @author apple
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.apply)
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

    }
    private void initView() {
        toolbarTitle.setText("设置");
        String token = SPUtil.getString(Contacts.TOKEN);
        if(!TextUtils.isEmpty(token)){
            apply.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @OnClick({R.id.toolbar_back, R.id.super_about, R.id.super_version, R.id.apply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.super_about:
                ActivityUtils.startActivity(AboutActivity.class);
                break;
            case R.id.super_version:
                ToastUtils.showToast("已经是最近版本啦！");
                break;
            case R.id.apply:
                SPUtil.remove(Contacts.PHONE);
                SPUtil.remove(Contacts.TOKEN);
                ActivityUtils.startActivity(LoginActivity.class);
                EventBus.getDefault().post(new LoginEvent(null));
                finish();
                break;
        }
    }
}
