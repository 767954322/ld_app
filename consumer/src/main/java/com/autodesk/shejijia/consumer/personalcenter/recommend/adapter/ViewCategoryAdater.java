package com.autodesk.shejijia.consumer.personalcenter.recommend.adapter;

import android.content.Context;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.BaseCommonRvAdapter;

import java.util.List;

/**
 * @User: 蜡笔小新
 * @date: 16-11-7
 * @GitHub: https://github.com/meikoz
 */

public class ViewCategoryAdater extends BaseCommonRvAdapter<String> {

    public ViewCategoryAdater(Context context, int layoutId, List<String> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, String s, int position) {
        //TODO　判断是否是选中的类型
//        view.setBackgroundResource(R.drawable.store_bg_btn_checked);
        TextView tv_category_name = holder.getView(R.id.tv_category_name);
        tv_category_name.setText(s);
    }
}
