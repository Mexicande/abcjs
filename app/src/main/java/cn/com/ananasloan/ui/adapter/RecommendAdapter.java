package cn.com.ananasloan.ui.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.ananasloan.R;
import cn.com.ananasloan.glide.GlideCircleTransform;
import cn.com.ananasloan.glide.GlideRoundTransform;
import cn.com.ananasloan.model.Product;

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
        helper.setText(R.id.name,item.getP_name())
                .setText(R.id.time,item.getFastest_time())
                .setText(R.id.rate,item.getMin_algorithm()+"%")
                .setText(R.id.tv_rate,item.getInterest_algorithm()==0?"日利率":"月利率");

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
        Glide.with(mContext).load(item.getP_logo()).apply(options)
                .into((ImageView) helper.getView(R.id.logo));
    }
}
