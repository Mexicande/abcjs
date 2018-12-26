package com.book.moneywhitebar.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import com.book.moneywhitebar.R;
import com.book.moneywhitebar.base.BaseActivity;
import com.book.moneywhitebar.common.Contacts;
import com.book.moneywhitebar.model.LoginEvent;
import com.book.moneywhitebar.utils.ActivityUtils;
import com.book.moneywhitebar.utils.SPUtil;
import com.book.moneywhitebar.utils.ToastUtils;

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
