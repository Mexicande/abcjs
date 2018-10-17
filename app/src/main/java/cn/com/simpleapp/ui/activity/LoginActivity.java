package cn.com.simpleapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.allen.library.SuperButton;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.simpleapp.R;
import cn.com.simpleapp.base.BaseActivity;
import cn.com.simpleapp.common.Contacts;
import cn.com.simpleapp.listener.OnRequestDataListener;
import cn.com.simpleapp.model.LoginEvent;
import cn.com.simpleapp.net.Api;
import cn.com.simpleapp.net.ApiService;
import cn.com.simpleapp.utils.BrowsingHistory;
import cn.com.simpleapp.utils.CaptchaTimeCount;
import cn.com.simpleapp.utils.CodeUtils;
import cn.com.simpleapp.utils.SPUtil;
import cn.com.simpleapp.utils.StatusBarUtil;
import cn.com.simpleapp.utils.ToastUtils;
import cn.com.simpleapp.view.editext.PowerfulEditText;


/**
 * @author apple
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.ed_phone)
    PowerfulEditText edPhone;
    @BindView(R.id.result)
    ImageView result;
    @BindView(R.id.verify_iv)
    ImageView verifyIv;
    @BindView(R.id.et_Result)
    EditText etResult;
    @BindView(R.id.layout_Result)
    RelativeLayout layoutResult;
    @BindView(R.id.ed_code)
    PowerfulEditText edCode;
    @BindView(R.id.bt_code)
    Button btCode;
    @BindView(R.id.layout_code)
    RelativeLayout layoutCode;
    @BindView(R.id.bt_login)
    SuperButton btLogin;
    private CaptchaTimeCount captchaTimeCount;
    private int oldNew = 1;
    private KProgressHUD hud;
    private String phone;
    private int isolduser;
    private final int RESULT_CODE=100;

    private CodeUtils codeUtils;
    private String yanZhengResult;
    private String etYanZhengCode;
    private String yanZhengCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 40);
        ButterKnife.bind(this);
        initView();
        setListener();
        initYanzheng();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    private void initView() {
        captchaTimeCount = new CaptchaTimeCount(Contacts.Times.MILLIS_IN_TOTAL, Contacts.Times.COUNT_DOWN_INTERVAL, btCode, this);

        hud = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f);

    }

    private void setListener() {
        edPhone.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (!etResult.getText().toString().isEmpty() && s.toString().length() == 11) {
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                } else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etResult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edPhone.getText().toString().length() == 11 && !s.toString().isEmpty()) {
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                } else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edCode.addTextListener(new PowerfulEditText.TextListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                if (edPhone.getText().toString().length() == 11 && s.toString().length() == 4) {
                    btLogin.setEnabled(true);
                    btLogin.setUseShape();
                } else {
                    btLogin.setEnabled(false);
                    btLogin.setUseShape();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initYanzheng() {
        codeUtils = CodeUtils.getInstance();
        Bitmap bitmap = codeUtils.createBitmap();
        verifyIv.setImageBitmap(bitmap);
        yanZhengCode = codeUtils.getCode();
        yanZhengResult = codeUtils.getResult() + "";
    }


    /**
     * 验证码效验
     */
    private void verCode(String code) {
        hud.show();
        phone = edPhone.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("userphone", phone);
        map.put("code", code);

        ApiService.GET_SERVICE(Api.LOGIN.CHECKCODE, map, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();

                try {
                    JSONObject date = data.getJSONObject("data");
                    String msg = date.getString("msg");
                    String isSucess = date.getString("isSuccess");
                    if ("1".equals(isSucess)) {
                        String token = date.getString("token");
                        String userphone = date.getString("userphone");
                        SPUtil.putString(Contacts.TOKEN, token);
                        SPUtil.putString(Contacts.PHONE, userphone);
                        EventBus.getDefault().post(new LoginEvent(phone));
                            Intent intent = new Intent();
                            intent.putExtra("phone", phone);
                            setResult(RESULT_CODE, intent);
                            finish();
                    }
                    ToastUtils.showToast( msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast( msg);
            }

            @Override
            public void onFinish() {
                hud.dismiss();

            }
        });

    }

    /**
     * 验证码获取
     */
    private void getCode() {
        captchaTimeCount.start();

        String phone = edPhone.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("userphone", phone);
        ApiService.GET_SERVICE(Api.LOGIN.CODE, map, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                try {
                    JSONObject date = data.getJSONObject("data");
                    String msg = date.getString("msg");
                    String isSucess = date.getString("isSuccess");
                    if ("1".equals(isSucess)) {
                    }
                    ToastUtils.showToast(msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast( msg);
            }

            @Override
            public void onFinish() {

            }
        });

    }

    /**
     * isOldUser
     * 新老用户
     */
    private void isOldUser() {
        hud.show();
        phone = edPhone.getText().toString();
        Map<String, String> map = new HashMap<>();
        map.put("userphone", phone);
        ApiService.GET_SERVICE(Api.LOGIN.isOldUser, map, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();
                try {
                    JSONObject date = data.getJSONObject("data");
                    oldNew = date.getInt("isolduser");
                    if (oldNew == 1) {
                        String token = date.getString("token");
                        String userphone = date.getString("userphone");
                        SPUtil.putString(Contacts.TOKEN, token);
                        SPUtil.putString(Contacts.PHONE, userphone);
                        layoutCode.setVisibility(View.GONE);
                        EventBus.getDefault().post(new LoginEvent(phone));
                        String title = getIntent().getStringExtra("title");
                        if (!TextUtils.isEmpty(title)) {
                            String id = getIntent().getStringExtra("id");
                            new BrowsingHistory().execute(id);
                        }
                        Intent intent = new Intent();
                        intent.putExtra("phone", phone);
                        setResult(200, intent);
                        finish();

                    } else {
                        layoutResult.setVisibility(View.GONE);
                        layoutCode.setVisibility(View.VISIBLE);
                        getCode();
                        btLogin.setEnabled(false);
                        btLogin.setUseShape();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast( msg);
            }

            @Override
            public void onFinish() {
                hud.dismiss();
            }
        });
    }

    @OnClick({R.id.back, R.id.bt_code, R.id.bt_login, R.id.verify_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.bt_code:
                getCode();
                break;
            case R.id.bt_login:
                if (oldNew == 1) {
                    etYanZhengCode = etResult.getText().toString().trim();
                    if (TextUtils.isEmpty(etYanZhengCode)) {
                        ToastUtils.showToast( "请输入图片里的结果");
                        return;
                    }
                    if (!yanZhengResult.equals(etYanZhengCode)) {
                        ToastUtils.showToast( "图片结果输入有误");
                        etResult.getText().clear();
                        initYanzheng();
                    } else {
                        isOldUser();
                    }
                } else {
                    String code = edCode.getText().toString();
                    verCode(code);
                }

                break;
            case R.id.verify_iv:
                initYanzheng();
                break;
        }
    }
}
