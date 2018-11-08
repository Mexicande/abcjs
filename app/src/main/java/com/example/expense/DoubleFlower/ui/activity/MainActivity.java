package com.example.expense.DoubleFlower.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.expense.DoubleFlower.R;
import com.example.expense.DoubleFlower.base.BaseActivity;
import com.example.expense.DoubleFlower.ui.adapter.MyViewPagerAdapter;
import com.example.expense.DoubleFlower.ui.adapter.NoTouchViewPager;
import com.example.expense.DoubleFlower.ui.frgement.CenterFragment;
import com.example.expense.DoubleFlower.ui.frgement.HomeFragment;
import com.example.expense.DoubleFlower.ui.frgement.LoanFragment;
import com.example.expense.DoubleFlower.utils.StatusBarUtil;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

/**
 * @author apple
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tab)
    PageBottomTabLayout tab;
    @BindView(R.id.viewPager)
    NoTouchViewPager mViewPager;
    public static NavigationController navigationController;

    public  static MyViewPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {
        navigationController = tab.custom()
                .addItem(newItem(R.mipmap.icon_default_home, R.mipmap.icon_home,"主页"))
                .addItem(newItem(R.mipmap.icon_defualt_loan, R.mipmap.icon_loan,"产品"))
                .addItem(newItem(R.mipmap.icon_defualt_center, R.mipmap.icon_center,"我的"))
                .build();
        ArrayList<Fragment> list=new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new LoanFragment());
        list.add(new CenterFragment());
        pagerAdapter=new MyViewPagerAdapter(getSupportFragmentManager(),list);
        mViewPager.setAdapter(pagerAdapter);
        //自动适配ViewPager页面切换
        navigationController.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(list.size());

    }
    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.GRAY);
        normalItemView.setTextCheckedColor(getResources().getColor(R.color.colorPrimary));
        return normalItemView;
    }

    private long mLastBackTime = 0;
    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mLastBackTime) < 1000) {
            finish();
        } else {
            mLastBackTime = System.currentTimeMillis();
            Toast.makeText(this, "请再确认一次", Toast.LENGTH_SHORT).show();
        }

    }
}
