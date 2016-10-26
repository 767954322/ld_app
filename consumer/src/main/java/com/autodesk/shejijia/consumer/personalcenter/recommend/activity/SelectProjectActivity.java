package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecommendAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.SelectProjectAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectProjectActivity extends NavigationBarActivity {

    private SelectProjectAdapter mAdapter;
    private ListView mListView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_select_project;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.select_project));
        mListView = (ListView) findViewById(R.id.lv_select_project);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        List<RecommendEntity.ItemsBean> list=new ArrayList<>();
        RecommendEntity.ItemsBean itemsBean = new RecommendEntity.ItemsBean();
        itemsBean.setCity_name("创建新的项目");
        list.add(0,itemsBean);
        mAdapter = new SelectProjectAdapter(SelectProjectActivity.this, list, R.layout.item_lv_select_project);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

}
