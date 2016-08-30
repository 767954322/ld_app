package com.autodesk.shejijia.consumer.home.decorationdesigners.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.SearchHoverCaseBean;
import com.autodesk.shejijia.shared.framework.adapter.CommonAdapter;
import com.autodesk.shejijia.shared.framework.adapter.CommonViewHolder;

import java.util.List;

/**
 * @author he.liu .
 * @version v1.0 .
 * @date 2016-8-22 .
 * @file DesignerSearchAdapter.java .
 * @brief 设计师查询页面适配器.
 */
public class DesignerSearchAdapter extends CommonAdapter<SearchHoverCaseBean> {

    private List<SearchHoverCaseBean> items;
    private Context context;


    public DesignerSearchAdapter(Context context, List<SearchHoverCaseBean> datas) {
        super(context, datas, R.layout.item_list_layout);
        this.context = context;
        this.items = items;
    }

    @Override
    public void convert(CommonViewHolder holder, SearchHoverCaseBean searchHoverCaseBean) {
        String value = searchHoverCaseBean.getValue();
        String description = searchHoverCaseBean.getDescription();
        description = TextUtils.isEmpty(description) ? "" : description;
        holder.setText(R.id.tv, value + description);
    }
}
