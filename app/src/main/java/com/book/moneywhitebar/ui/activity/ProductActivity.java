package com.book.moneywhitebar.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.book.moneywhitebar.R;
import com.book.moneywhitebar.base.BaseActivity;
import com.book.moneywhitebar.common.Contacts;
import com.book.moneywhitebar.listener.OnRequestDataListener;
import com.book.moneywhitebar.model.Product;
import com.book.moneywhitebar.net.Api;
import com.book.moneywhitebar.net.ApiService;
import com.book.moneywhitebar.ui.adapter.LoanAdapter;
import com.book.moneywhitebar.utils.BrowsingHistory;
import com.book.moneywhitebar.utils.RecyclerViewDecoration;
import com.book.moneywhitebar.utils.SPUtil;
import com.book.moneywhitebar.utils.ToastUtils;

/**
 * @author apple
 */
public class ProductActivity extends BaseActivity {


    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.Swip)
    SwipeRefreshLayout mSwip;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private LoanAdapter mProductAdapter;
    private final int REQUESTION_CODE = 1001;
    private final int RESULT_CODE = 100;
    private String url;


    public static void launch(Context context, String identity,String title) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra("identity", identity);
        intent.putExtra("title", title);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        getData();
        setListener();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product;
    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        toolbarTitle.setText(title);
        mProductAdapter = new LoanAdapter(null);
        mRecylerview.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewDecoration decoration = new RecyclerViewDecoration(10);
        mRecylerview.addItemDecoration(decoration);
        mRecylerview.setAdapter(mProductAdapter);

    }

    private void getData() {
        String identity = getIntent().getStringExtra("identity");
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type",identity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiService.JSONGET_SERVICE(Api.URL, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                if (mSwip.isRefreshing()) {
                    mSwip.setRefreshing(false);
                }
                try {
                    String data = json.getString("data");
                    Gson gson = new Gson();
                    Product[] welfare = gson.fromJson(data, Product[].class);
                    if (welfare.length > 0) {
                        List<Product> welfare1 = Arrays.asList(welfare);
                        mProductAdapter.setNewData(welfare1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void requestFailure(int code, String msg) {
                ToastUtils.showToast(msg);
            }

            @Override
            public void onFinish() {
                if (mSwip.isRefreshing()) {
                    mSwip.setRefreshing(false);
                }
            }
        });
    }

    private void setListener() {

        mProductAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Product product = mProductAdapter.getData().get(position);
                String token = SPUtil.getString(Contacts.TOKEN);
                if (TextUtils.isEmpty(token)) {
                    Intent intent = new Intent(ProductActivity.this, LoginActivity.class);
                    intent.putExtra("title", product.getName());
                    intent.putExtra("link", product.getLink());
                    intent.putExtra("id", product.getId());
                    startActivity(intent);
                } else {
                    new BrowsingHistory().execute(product.getId());
                    Uri uri = Uri.parse(product.getLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
        mSwip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUESTION_CODE == requestCode) {
            if (resultCode == RESULT_CODE) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }
    }

    @OnClick(R.id.toolbar_back)
    public void onViewClicked() {
        finish();
    }
}
