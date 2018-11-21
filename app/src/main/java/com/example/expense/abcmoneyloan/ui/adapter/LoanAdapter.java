package com.example.expense.abcmoneyloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import com.example.expense.abcmoneyloan.R;
import com.example.expense.abcmoneyloan.glide.GlideRoundTransform;
import com.example.expense.abcmoneyloan.model.Product;

/**
 *
 * @author apple
 * @date 2017/4/11
 */

public class LoanAdapter extends BaseQuickAdapter<Product,BaseViewHolder> {

    public LoanAdapter(List<Product> data) {
        super(R.layout.product_item, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Product item) {
        helper.setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_desc,item.getProduct_introduction())
                .setText(R.id.number,item.getApply_total());


        helper.setText(R.id.special_rate,item.getMin_algorithm());
            helper.setText(R.id.interest_algorithm,item.getInterest_algorithm());

        RequestOptions options=new RequestOptions()
                .centerCrop()
                .optionalTransform(new GlideRoundTransform(5));
        Glide.with(mContext).load(item.getProduct_logo()).apply(options)
                .into((ImageView) helper.getView(R.id.iv_logo));
    }
}
