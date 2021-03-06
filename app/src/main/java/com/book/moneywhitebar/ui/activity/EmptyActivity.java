package com.book.moneywhitebar.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.book.moneywhitebar.R;
import com.book.moneywhitebar.base.BaseActivity;

/**
 * @author apple
 */
public class EmptyActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        toolbarTitle.setText(title);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }
}
