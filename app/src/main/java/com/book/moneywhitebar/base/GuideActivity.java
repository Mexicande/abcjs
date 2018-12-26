package com.book.moneywhitebar.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.book.moneywhitebar.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import com.book.moneywhitebar.R;

import com.book.moneywhitebar.utils.ActivityUtils;
import com.book.moneywhitebar.utils.SharedPreferencesUtil;
import com.book.moneywhitebar.utils.StatusBarUtil;

/**
 * @author apple
 */
public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucent(this);
        setListener();
        processLogic();
    }
    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mBackgroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                ActivityUtils.startActivity(MainActivity.class);
                SharedPreferencesUtil.putBoolean(GuideActivity.this, SharedPreferencesUtil.FIRST_OPEN, false);
                finish();
            }
        });
    }
    private void processLogic() {
        // 设置数据源
        mBackgroundBanner.setData(null, ImageView.ScaleType.FIT_XY,R.mipmap.lod_1, R.mipmap.lod_2,R.mipmap.lod_3);

    }
}
