package com.example.expense.DoubleFlower.ui.frgement;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.expense.DoubleFlower.ui.activity.LoginActivity;
import com.example.expense.DoubleFlower.ui.activity.ProductActivity;
import com.example.expense.DoubleFlower.ui.adapter.LoanAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import com.example.expense.DoubleFlower.R;
import com.example.expense.DoubleFlower.common.Contacts;
import com.example.expense.DoubleFlower.glide.GlideRoundTransform;
import com.example.expense.DoubleFlower.listener.OnRequestDataListener;
import com.example.expense.DoubleFlower.model.Banner;
import com.example.expense.DoubleFlower.model.Product;
import com.example.expense.DoubleFlower.net.Api;
import com.example.expense.DoubleFlower.net.ApiService;
import com.example.expense.DoubleFlower.utils.BrowsingHistory;
import com.example.expense.DoubleFlower.utils.RecyclerViewDecoration;
import com.example.expense.DoubleFlower.utils.SPUtil;
import com.example.expense.DoubleFlower.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author apple
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.recylerview)
    RecyclerView recylerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    private LoanAdapter mAdapter;
    private String url;
    private final int REQUESTION_CODE=1001;
    private final int RESULT_CODE=100;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        getDate();
        getBanner();
        setListener();
        return view;
    }

    private void initView() {
        mAdapter = new LoanAdapter(null);
        recylerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewDecoration decoration = new RecyclerViewDecoration(10);
        recylerview.addItemDecoration(decoration);
        recylerview.setAdapter(mAdapter);
        View footerView = setView();
        mAdapter.addHeaderView(footerView, 0);
        banner.setAdapter(new BGABanner.Adapter<ImageView, Banner>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, Banner model, int position) {
                RequestOptions options=new RequestOptions()
                        .centerCrop()
                        .optionalTransform(new GlideRoundTransform(5));
                Glide.with(getActivity())
                        .load(model.getPictrue())
                        .apply(options)
                        .into(itemView);
            }
        });
    }
    private void setListener() {
        banner.setDelegate(new BGABanner.Delegate<ImageView, Banner>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, Banner model, int position) {
                String token = SPUtil.getString( Contacts.TOKEN);
                if(TextUtils.isEmpty(token)){
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("title",model.getAdvername());
                    intent.putExtra("link",model.getApp());
                    startActivity(intent);
                }else {
                    Uri uri = Uri.parse(model.getApp());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });


        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Product product = mAdapter.getData().get(position);
                String token = SPUtil.getString( Contacts.TOKEN);
                if(TextUtils.isEmpty(token)){
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("title",product.getName());
                    intent.putExtra("link",product.getLink());
                    intent.putExtra("id",product.getId());
                    startActivity(intent);
                }else {
                    new BrowsingHistory().execute(product.getId());
                    Uri uri = Uri.parse(product.getLink());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });


        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getBanner();
                getDate();
            }
        });
    }


    private void getDate() {
        /**product_list**/
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("type","8");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiService.JSONGET_SERVICE(Api.URL, jsonObject, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                try {
                    String data = json.getString("data");
                    List<Product> mRecommendList = new Gson().fromJson(data, new TypeToken<List<Product>>() {
                    }.getType());
                    mAdapter.setNewData(mRecommendList);
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
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.finishRefresh();
                }
            }
        });

    }

    private void getBanner() {
        /**banner**/
        ApiService.GET_SERVICE(Api.BANNER, new HashMap<String, String>(), new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                try {
                    String data = json.getString("data");
                    Gson gson = new Gson();
                    Banner[] banners = gson.fromJson(data, Banner[].class);
                    if (banners.length > 0) {
                        List<Banner> banners1 = Arrays.asList(banners);
                        banner.setData(banners1, null);
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

            }
        });
    }
    private BGABanner banner;
    private View setView() {

        View view = getActivity().getLayoutInflater().inflate(R.layout.header_layout, null);
        banner = (BGABanner) view.findViewById(R.id.banner_fresco_demo_content);
        ButterKnife.findById(view,R.id.layout_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.launch(getActivity(),"11","新品专区");
            }
        });
        ButterKnife.findById(view,R.id.layout_big).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.launch(getActivity(),"4","大额贷款");
            }
        });
        ButterKnife.findById(view,R.id.layout_smart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.launch(getActivity(),"7","小额贷款");
            }
        });
        ButterKnife.findById(view,R.id.layout_sesame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.launch(getActivity(),"9","身份贷");
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
