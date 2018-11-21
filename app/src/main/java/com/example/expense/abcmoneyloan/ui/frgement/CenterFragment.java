package com.example.expense.abcmoneyloan.ui.frgement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expense.abcmoneyloan.ui.activity.EmptyActivity;
import com.example.expense.abcmoneyloan.ui.activity.FeedbackActivity;
import com.example.expense.abcmoneyloan.ui.activity.HtmlActivity;
import com.example.expense.abcmoneyloan.ui.activity.LoginActivity;
import com.example.expense.abcmoneyloan.ui.activity.SettingActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.example.expense.abcmoneyloan.R;
import com.example.expense.abcmoneyloan.common.Contacts;
import com.example.expense.abcmoneyloan.model.LoginEvent;
import com.example.expense.abcmoneyloan.utils.ActivityUtils;
import com.example.expense.abcmoneyloan.utils.SPUtil;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 */
public class CenterFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.tv_name)
    TextView tvName;
    private String token;
    private final int LOGIN_REQUESTION = 10000;
    private final int LOAN_REQUESTION = 20000;
    private final int FREE_REQUESTION = 30000;
    private final int RESULT_CODE = 200;

    public CenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView();

        return view;
    }

    private void initView() {
        token = SPUtil.getString(Contacts.TOKEN);
        if(!TextUtils.isEmpty(token)){
            tvName.setVisibility(View.GONE);
        }

    }

    @Subscribe
    public void getLogin(LoginEvent event) {
        token = SPUtil.getString(Contacts.TOKEN);
        tvName.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.super_schedule, R.id.super_free, R.id.super_safe,
            R.id.super_feedback, R.id.super_setting,R.id.tv_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_name:
                token = SPUtil.getString(Contacts.TOKEN);
                if (TextUtils.isEmpty(token)) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, LOAN_REQUESTION);
                }
                break;
            case R.id.super_schedule:
                token = SPUtil.getString(Contacts.TOKEN);
                if (TextUtils.isEmpty(token)) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, LOAN_REQUESTION);
                } else {
                    Intent intent = new Intent(getActivity(), EmptyActivity.class);
                    intent.putExtra("title", "贷款进度");
                    startActivity(intent);
                }
                break;
            case R.id.super_free:
                token = SPUtil.getString(Contacts.TOKEN);
                if (TextUtils.isEmpty(token)) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, FREE_REQUESTION);
                } else {
                    Intent intent = new Intent(getActivity(), EmptyActivity.class);
                    intent.putExtra("title", "我的免息券");
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
