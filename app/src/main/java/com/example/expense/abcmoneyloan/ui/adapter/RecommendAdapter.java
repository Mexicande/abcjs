package com.example.expense.abcmoneyloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.example.expense.abcmoneyloan.R;
import com.example.expense.abcmoneyloan.glide.GlideCircleTransform;
import com.example.expense.abcmoneyloan.model.Product;

/**
 *
 * @author apple
 * @date 2017/4/11
 */

public class RecommendAdapter extends BaseQuickAdapter<Product,BaseViewHolder> {

    public RecommendAdapter(List<Product> data)
    {
        super(R.layout.recommend_item, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        helper.setText(R.id.name,item.getName())
                .setText(R.id.time,item.getFastest_time())
                .setText(R.id.rate,item.getMin_algorithm()+"%")
                .setText(R.id.tv_rate,item.getInterest_algorithm());

        String maximumAmount = item.getMaximum_amount();
        if(maximumAmount.length()>4){
            String substring = maximumAmount.substring(0, maximumAmount.length() - 4);
            helper.setText(R.id.limit,item.getMinimum_amount()+"-"+substring+"万");
        }else {
            helper.setText(R.id.limit,item.getMinimum_amount()+"-"+maximumAmount);
        }
        RequestOptions options=new RequestOptions()
                .centerCrop()
                .optionalTransform(new GlideCircleTransform());
        Glide.with(mContext).load(item.getProduct_logo()).apply(options)
                .into((ImageView) helper.getView(R.id.logo));
    }
}
