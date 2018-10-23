package cn.com.simplecolourful.ui.frgement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.simplecolourful.R;
import cn.com.simplecolourful.common.Contacts;
import cn.com.simplecolourful.model.LoginEvent;
import cn.com.simplecolourful.ui.activity.EmptyActivity;
import cn.com.simplecolourful.ui.activity.FeedbackActivity;
import cn.com.simplecolourful.ui.activity.HtmlActivity;
import cn.com.simplecolourful.ui.activity.LoginActivity;
import cn.com.simplecolourful.ui.activity.SettingActivity;
import cn.com.simplecolourful.utils.ActivityUtils;
import cn.com.simplecolourful.utils.SPUtil;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 */
public class CenterFragment extends Fragment {


    @BindView(R.id.name)
    TextView name;
    Unbinder unbinder;
    private String token;
    private final int LOGIN_REQUESTION=10000;
    private final int LOAN_REQUESTION=20000;
    private final int FREE_REQUESTION=30000;
    private final int RESULT_CODE=200;
    public CenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initView();

        return view;
    }
    private void initView() {
        token = SPUtil.getString(Contacts.TOKEN);
        String phone = SPUtil.getString(Contacts.PHONE);
        if(!TextUtils.isEmpty(phone)){
            name.setText(phone);
        }

    }
    @Subscribe
    public void getLogin(LoginEvent event){
        if(event.msg!=null){
            name.setText(event.msg);
        }else {
            name.setText("未登录");
        }
        token = SPUtil.getString(Contacts.TOKEN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.layout_edit, R.id.super_schedule, R.id.super_free, R.id.super_safe, R.id.super_feedback, R.id.super_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_edit:
                token = SPUtil.getString(Contacts.TOKEN);
                if(TextUtils.isEmpty(token)){
                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(loginIntent,LOGIN_REQUESTION);
                }
                break;
            case R.id.super_schedule:
                token = SPUtil.getString(Contacts.TOKEN);
                if(TextUtils.isEmpty(token)){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,LOAN_REQUESTION);
                }else {
                    Intent intent = new Intent(getActivity(), EmptyActivity.class);
                    intent.putExtra("title","贷款进度");
                    startActivity(intent);
                }
                break;
            case R.id.super_free:
                token = SPUtil.getString(Contacts.TOKEN);
                if(TextUtils.isEmpty(token)){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,FREE_REQUESTION);
                }else {
                    Intent intent = new Intent(getActivity(), EmptyActivity.class);
                    intent.putExtra("title","我的免息券");
                    startActivity(intent);
                }
                break;
            case R.id.super_safe:
                Intent intent = new Intent(getActivity(), HtmlActivity.class);
                intent.putExtra("title", "安全小贴士");
                intent.putExtra("link", "http://m.anwenqianbao.com/#/minTips");
                startActivity(intent);
                break;
            case R.id.super_feedback:
                ActivityUtils.startActivity(FeedbackActivity.class);
                break;
            case R.id.super_setting:
                ActivityUtils.startActivity(SettingActivity.class);
                break;
        }
    }
}
