package com.book.moneywhitebar.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
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
import com.book.moneywhitebar.R;
import com.book.moneywhitebar.base.BaseActivity;
import com.book.moneywhitebar.common.Contacts;
import com.book.moneywhitebar.listener.OnRequestDataListener;
import com.book.moneywhitebar.model.LoginEvent;
import com.book.moneywhitebar.net.Api;
import com.book.moneywhitebar.net.ApiService;
import com.book.moneywhitebar.utils.BrowsingHistory;
import com.book.moneywhitebar.utils.CaptchaTimeCount;
import com.book.moneywhitebar.utils.CodeUtils;
import com.book.moneywhitebar.utils.SPUtil;
import com.book.moneywhitebar.utils.StatusBarUtil;
import com.book.moneywhitebar.utils.ToastUtils;
import com.book.moneywhitebar.view.editext.PowerfulEditText;


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
    private int oldNew = 0;
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
        StatusBarUtil.setTranslucent(this);
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

        JSONObject object=new JSONObject();
        try {
            object.put("userphone",phone);
            object.put("app_name",getString(R.string.app_name));
            object.put("terminal","2");
            object.put("code",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiService.JSONGET_SERVICE(Api.LOGIN.CHECKCODE, object, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();

                try {
                    JSONObject date = data.getJSONObject("data");
                    String token = date.getString("token");
                    SPUtil.putString(Contacts.TOKEN, token);
                    SPUtil.putString( Contacts.PHONE, phone);
                    EventBus.getDefault().post(new LoginEvent(phone));
                    String title = getIntent().getStringExtra("title");
                    String link = getIntent().getStringExtra("link");
                    if(!TextUtils.isEmpty(title)){
                        String id = getIntent().getStringExtra("id");
                        new BrowsingHistory().execute(id);
                        Uri uri = Uri.parse(link);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }else {
                        Intent intent=new Intent();
                        intent.putExtra("phone",phone);
                        setResult(200,intent);
                    }
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailure(int code, String msg) {
                hud.dismiss();
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

        JSONObject object=new JSONObject();
        try {
            object.put("userphone",phone);
            object.put("app_name",getString(R.string.app_name));
            object.put("terminal","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.JSONGET_SERVICE(Api.LOGIN.isOldUser, object, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject data) {
                hud.dismiss();
                try {
                    JSONObject date = data.getJSONObject("data");
                    oldNew = date.getInt("is_user");
                    if (oldNew == 0) {
                        String token = date.getString("token");
                        SPUtil.putString(Contacts.TOKEN, token);
                        SPUtil.putString(Contacts.PHONE, phone);
                        layoutCode.setVisibility(View.GONE);
                        EventBus.getDefault().post(new LoginEvent(phone));
                        String title = getIntent().getStringExtra("title");
                        String link = getIntent().getStringExtra("link");
                        if(!TextUtils.isEmpty(title)){
                            String id = getIntent().getStringExtra("id");
                            new BrowsingHistory().execute(id);
                            Uri uri = Uri.parse(link);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }else {
                            Intent intent=new Intent();
                            intent.putExtra("phone",phone);
                            setResult(200,intent);
                        }
                        finish();

                    } else {
                        captchaTimeCount.start();
                        layoutResult.setVisibility(View.GONE);
                        layoutCode.setVisibility(View.VISIBLE);
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
                finish();
                break;
            case R.id.bt_code:
                getCode();
                break;
            case R.id.bt_login:
                if (oldNew == 0) {
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
            default:
                break;
        }
    }
}
