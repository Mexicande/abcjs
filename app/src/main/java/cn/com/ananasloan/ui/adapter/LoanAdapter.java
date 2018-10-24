package cn.com.ananasloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.ananasloan.R;
import cn.com.ananasloan.glide.GlideRoundTransform;
import cn.com.ananasloan.model.Product;
import cn.com.ananasloan.model.ProductEntity;

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
        helper.setText(R.id.tv_name,item.getP_name())
                .setText(R.id.tv_desc,item.getP_desc())
                .setText(R.id.number,item.getApply());


        helper.setText(R.id.special_rate,item.getMin_algorithm()+"%");
        int interestAlgorithm = item.getInterest_algorithm();
        if(interestAlgorithm==0){
            helper.setText(R.id.interest_algorithm,"日利率");
        }else {
            helper.setText(R.id.interest_algorithm,"月利率");
        }

        RequestOptions options=new RequestOptions()
                .centerCrop()
                .optionalTransform(new GlideRoundTransform(5));
        Glide.with(mContext).load(item.getP_logo()).apply(options)
                .into((ImageView) helper.getView(R.id.iv_logo));
    }
}
