package cn.com.simpleapp.ui.frgement;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.com.simpleapp.R;
import cn.com.simpleapp.common.Contacts;
import cn.com.simpleapp.glide.GlideRoundTransform;
import cn.com.simpleapp.listener.OnRequestDataListener;
import cn.com.simpleapp.model.Banner;
import cn.com.simpleapp.model.MessageBean;
import cn.com.simpleapp.model.Product;
import cn.com.simpleapp.net.Api;
import cn.com.simpleapp.net.ApiService;
import cn.com.simpleapp.ui.activity.LoginActivity;
import cn.com.simpleapp.ui.activity.ProductActivity;
import cn.com.simpleapp.ui.adapter.LoanAdapter;
import cn.com.simpleapp.ui.adapter.RecommendAdapter;
import cn.com.simpleapp.utils.BrowsingHistory;
import cn.com.simpleapp.utils.RecycleViewDivider;
import cn.com.simpleapp.utils.RecyclerViewDecoration;
import cn.com.simpleapp.utils.SPUtil;
import cn.com.simpleapp.utils.ToastUtils;

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
                    url=model.getApp();
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent,REQUESTION_CODE);
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
                    intent.putExtra("title",product.getP_name());
                    intent.putExtra("link",product.getUrl());
                    intent.putExtra("id",product.getId());
                    startActivity(intent);
                }else {
                    new BrowsingHistory().execute(product.getId());
                    Uri uri = Uri.parse(product.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });

        mRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Product product = mRecommendAdapter.getData().get(position);
                String token = SPUtil.getString( Contacts.TOKEN);
                if(TextUtils.isEmpty(token)){
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("title",product.getP_name());
                    intent.putExtra("link",product.getUrl());
                    intent.putExtra("id",product.getId());
                    startActivity(intent);
                }else {
                    new BrowsingHistory().execute(product.getId());
                    Uri uri = Uri.parse(product.getUrl());
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
        ApiService.GET_SERVICE(Api.PRODUCT_LSIT, null, new OnRequestDataListener() {
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

        ApiService.GET_SERVICE(Api.RECOMMEND, null, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                try {
                    String data = json.getString("data");
                    List<Product> mRecommendList = new Gson().fromJson(data, new TypeToken<List<Product>>() {
                    }.getType());
                    mRecommendAdapter.setNewData(mRecommendList);
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
        ApiService.GET_SERVICE(Api.Notice, null, new OnRequestDataListener() {
            @Override
            public void requestSuccess(int code, JSONObject json) {
                try {
                    String data = json.getString("data");
                    List<MessageBean> mRecommendList = new Gson().fromJson(data, new TypeToken<List<MessageBean>>() {
                    }.getType());

                    List<String>list=new ArrayList<>();
                    for(MessageBean s:mRecommendList){
                        list.add(s.getBody());
                    }
                    marqueeFactory.setData(list);
                    marqueeView.setMarqueeFactory(marqueeFactory);
                    marqueeView.startFlipping();
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
    private RecyclerView mNewsRecyclerView;
    private RecommendAdapter mRecommendAdapter;
    private SimpleMarqueeView<String> marqueeView;
    private SimpleMF<String> marqueeFactory;
    private View setView() {
        marqueeFactory = new SimpleMF(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.header_layout, null);
        banner = (BGABanner) view.findViewById(R.id.banner_fresco_demo_content);
        marqueeView=view.findViewById(R.id.simpleMarqueeView);
        ButterKnife.findById(view,R.id.layout_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.launch(getActivity(),"1");
            }
        });
        mRecommendAdapter=new RecommendAdapter(null);
        mNewsRecyclerView=view.findViewById(R.id.header_Recylerview);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        mNewsRecyclerView.setAdapter(mRecommendAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mNewsRecyclerView);
     /*   LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mNewsRecyclerView);*/

        ButterKnife.findById(view,R.id.layout_big).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.launch(getActivity(),"1");
            }
        });
        ButterKnife.findById(view,R.id.layout_smart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.launch(getActivity(),"1");
            }
        });
        ButterKnife.findById(view,R.id.layout_sesame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductActivity.launch(getActivity(),"1");
            }
        });
        return view;
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
