package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendMallsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.ShowBrandsBean;

import java.util.List;

/**
 * Created by luchongbin on 16-11-1.
 */

public class AddBrandAdapter extends CommonAdapter<RecommendBrandsBean> {
   private Context context;


    public AddBrandAdapter(Context context, List<RecommendBrandsBean> brandsBeens, int layoutId) {
        super(context, brandsBeens, layoutId);
        this.context = context;
    }
    @Override
    public boolean hasStableIds() {
        //getCheckedItemIds()方法要求此处返回为真
        return true;
    }

    @Override
    public void convert(final CommonViewHolder holder, RecommendBrandsBean recommendBrandsBean) {
        List<RecommendMallsBean> mallsBeans = recommendBrandsBean.getMalls();
        StringBuffer sb = new StringBuffer();
        sb.append(recommendBrandsBean.getBrand_name()+"\n");
        for(RecommendMallsBean mallsBean:mallsBeans){
            if(TextUtils.isEmpty(mallsBean.getMall_name())){
                continue;
            }
            sb.append(mallsBean.getMall_name()+"、");
        }
        if(sb.length() <= 0){
            return;
        }
        sb = sb.delete(sb.length()-1,sb.length());
        SpannableStringBuilder builder = new SpannableStringBuilder(sb);
        builder.setSpan(new AbsoluteSizeSpan(48), 0, recommendBrandsBean.getBrand_name().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//设置字体的大小
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.bg_66)), recommendBrandsBean.getBrand_name().length(), sb.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//字体的颜色
//        holder.getView(R.id.ctv_select).setSelected(true);
        TextView textView = holder.getView(R.id.ctv_select);
        textView.setSelected(true);
        textView.setText(builder);


    };
}
