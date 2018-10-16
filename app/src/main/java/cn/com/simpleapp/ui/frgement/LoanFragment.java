package cn.com.simpleapp.ui.frgement;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.simpleapp.R;
import cn.com.simpleapp.common.Contacts;
import cn.com.simpleapp.listener.OnRequestDataListener;
import cn.com.simpleapp.model.Product;
import cn.com.simpleapp.model.ProductEntity;
import cn.com.simpleapp.net.Api;
import cn.com.simpleapp.net.ApiService;
import cn.com.simpleapp.ui.activity.LoginActivity;
import cn.com.simpleapp.ui.adapter.LoanAdapter;
import cn.com.simpleapp.utils.BrowsingHistory;
import cn.com.simpleapp.utils.RecyclerViewDecoration;
import cn.com.simpleapp.utils.SPUtil;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 * 产品大全
 */
public class LoanFragment extends Fragment {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.recylerview)
    RecyclerView mRecylerview;
    @BindView(R.id.Swip)
    SwipeRefreshLayout mSwip;
    Unbinder unbinder;
    private LoanAdapter mProductAdapter;
    private final int REQUESTION_CODE=1001;
    private final int RESULT_CODE=100;
    private String url;
    public LoanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loan, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        getData();
        setListener();
        return view;
    }

    private void initView() {
        title.setText("贷款大全");
        mProductAdapter = new LoanAdapter(null);
        mRecylerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewDecoration decoration = new RecyclerViewDecoration(10);
        mRecylerview.addItemDecoration(decoration);
        mRecylerview.setAdapter(mProductAdapter);

    }
    private void getData() {


        ApiService.GET_SERVICE(Api.LSIT, new HashMap<String, String>(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                if(mSwip.isRefreshing()){
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
                String token = SPUtil.getString( Contacts.TOKEN);
                if(TextUtils.isEmpty(token)){
                    url=product.getUrl();
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,REQUESTION_CODE);
                }else {
                    new BrowsingHistory().execute(product.getId());
                    Uri uri = Uri.parse(product.getUrl());
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(REQUESTION_CODE==requestCode){
            if (resultCode == RESULT_CODE) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }
    }
}
