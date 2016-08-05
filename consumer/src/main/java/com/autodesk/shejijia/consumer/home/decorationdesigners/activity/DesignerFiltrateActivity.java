package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.os.Bundle;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FiltrateAdapter;
import com.autodesk.shejijia.shared.components.common.uielements.NoScrollGridView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import java.util.ArrayList;
import java.util.List;

public class DesignerFiltrateActivity extends NavigationBarActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_designer_filtrate;
    }

    @Override
    protected void initView() {
        super.initView();
        yGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_year);
        sGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_style);
        pGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_price);

    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.bid_filter));
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.select_finish));


        mYearData.addAll(filledData(getResources().getStringArray(R.array.year)));
        mStyleData.addAll(filledData(getResources().getStringArray(R.array.style)));
        mPriceData.addAll(filledData(getResources().getStringArray(R.array.price)));

        mYAdapter = new FiltrateAdapter(this, mYearData);
        mSAdapter = new FiltrateAdapter(this, mStyleData);
        mPAdapter = new FiltrateAdapter(this, mPriceData);

        yGridView.setAdapter(mYAdapter);
        sGridView.setAdapter(mSAdapter);
        pGridView.setAdapter(mPAdapter);

    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    /**
     * 数组转成集合
     *
     * @param date 　数组
     * @return 数组转成的集合
     */
    private List<String> filledData(String[] date) {
        List<String> mSortList = new ArrayList<String>();
        for (String str : date) {
            mSortList.add(str);
        }
        return mSortList;
    }


    /// 控件.
    private NoScrollGridView yGridView;
    private NoScrollGridView sGridView;
    private NoScrollGridView pGridView;

    /// 集合,类.
    private FiltrateAdapter mYAdapter;
    private FiltrateAdapter mSAdapter;
    private FiltrateAdapter mPAdapter;

    private List<String> mYearData = new ArrayList<>();
    private List<String> mStyleData = new ArrayList<>();
    private List<String> mPriceData = new ArrayList<>();
}
