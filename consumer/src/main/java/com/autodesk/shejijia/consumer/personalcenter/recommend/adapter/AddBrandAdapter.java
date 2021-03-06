package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.CheckedTextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.adapter.CommonAdapter;
import com.autodesk.shejijia.consumer.base.adapter.CommonViewHolder;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendMallsBean;

import java.util.List;

/**
 * Created by luchongbin on 16-11-1.
 */

public class AddBrandAdapter extends CommonAdapter<RecommendBrandsBean> {
    private Context context;
    private List<Integer> itemIds;
    private int maxSize;
    public AddBrandAdapter(Context context, List<RecommendBrandsBean> brandsBeens, List<Integer> itemIds, int maxSize,int layoutId) {
        super(context, brandsBeens, layoutId);
        this.context = context;
        this.itemIds = itemIds;
        this.maxSize = maxSize;
    }

    @Override
    public boolean hasStableIds() {
        //getCheckedItemIds()方法要求此处返回为真
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        if (itemIds == null || itemIds.size() < maxSize) {
            return true;
        }
        for (int id : itemIds) {
            if (id == position) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void convert(final CommonViewHolder holder, RecommendBrandsBean recommendBrandsBean) {
        List<RecommendMallsBean> mallsBeans = recommendBrandsBean.getMalls();
        StringBuffer sb = new StringBuffer();
        String brandName = recommendBrandsBean.getName();
        sb.append(TextUtils.isEmpty(brandName) ? "\n" : brandName + "\n");
        for (RecommendMallsBean mallsBean : mallsBeans) {
            if (TextUtils.isEmpty(mallsBean.getMall_name())) {
                continue;
            }
            sb.append(mallsBean.getMall_name() + "、");
        }
        if (sb.length() <= 0) {
            return;
        }
        sb = sb.delete(sb.length() - 1, sb.length());
        SpannableStringBuilder builder = new SpannableStringBuilder(sb);
        int index = 0;
        if (brandName != null && brandName.length() > 0) {
            index = brandName.length();
        }
        builder.setSpan(new AbsoluteSizeSpan(15,true), 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//设置字体的大小
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        builder.setSpan(span, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.bg_66)), index, sb.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//字体的颜色
        CheckedTextView textView = holder.getView(R.id.ctv_select);
        textView.setText(builder);


    }

    ;
}
