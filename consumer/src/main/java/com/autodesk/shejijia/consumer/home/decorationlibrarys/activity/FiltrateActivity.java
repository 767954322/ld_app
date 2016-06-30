package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FiltrateAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.utility.ConvertUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.NoScrollGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 2016/3/10 0010 14:50 .
 * @file FiltrateActivity  .
 * @brief 筛选页面 .
 */
public class FiltrateActivity extends NavigationBarActivity implements AdapterView.OnItemClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_filtrate;
    }

    @Override
    protected void initView() {
        super.initView();
        sGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_style);
        hGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_house);
        aGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_area);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        mStyleIndex = getIntent().getIntExtra(Constant.CaseLibrarySearch.STYLE_INDEX, 0);
        mHouseIndex = getIntent().getIntExtra(Constant.CaseLibrarySearch.HOUSING_INDEX, 0);
        mAreaIndex = getIntent().getIntExtra(Constant.CaseLibrarySearch.AREA_INDEX, 0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.bid_filter));
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.select_finish));

        mStyleData.addAll(filledData(getResources().getStringArray(R.array.all)));
        mStyleData.addAll(filledData(getResources().getStringArray(R.array.style)));
        mHouseData.addAll(filledData(getResources().getStringArray(R.array.all)));
        mHouseData.addAll(filledData(getResources().getStringArray(R.array.mlivingroom)));
        mAreaData.addAll(filledData(getResources().getStringArray(R.array.all)));
        mAreaData.addAll(filledData(getResources().getStringArray(R.array.area)));

        mSAdapter = new FiltrateAdapter(this, mStyleData);
        mHAdapter = new FiltrateAdapter(this, mHouseData);
        mAAdapter = new FiltrateAdapter(this, mAreaData);
        sGridView.setAdapter(mSAdapter);
        hGridView.setAdapter(mHAdapter);
        aGridView.setAdapter(mAAdapter);
        setSelection(mSAdapter, mStyleIndex);
        setSelection(mHAdapter, mHouseIndex);
        setSelection(mAAdapter, mAreaIndex);
    }

    @Override
    protected void initListener() {
        super.initListener();
        /// 各个GridView的监听 .
        sGridView.setOnItemClickListener(this);
        hGridView.setOnItemClickListener(this);
        aGridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(sGridView)) {
            mSAdapter.setSelection(position);
            mStyleIndex = position;
            mSAdapter.notifyDataSetChanged();
        } else if (parent.equals(hGridView)) {
            mHAdapter.setSelection(position);
            mHouseIndex = position;
            mHAdapter.notifyDataSetChanged();
        } else if (parent.equals(aGridView)) {
            mAAdapter.setSelection(position);
            mAreaIndex = position;
            mAAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 筛选
     *
     * @param view 要点击的完成控件
     */
    @Override
    protected void rightNavButtonClicked(View view) {
//        super.rightNavButtonClicked(view);
        Map<String, String> room = AppJsonFileReader.getRoomHall(this);
        Map<String, String> area = AppJsonFileReader.getArea(this);
        Map<String, String> style = AppJsonFileReader.getStyle(this);


        String all = UIUtils.getString(R.string.my_bid_all);
        mLivingRoom = mHouseData.get(mHouseIndex);
        mLivingRoom = ConvertUtils.getKeyByValue(room, mLivingRoom);
        mLivingRoom = mLivingRoom.equals(all) ? BLANK : mLivingRoom;

        mArea = mAreaData.get(mAreaIndex);
        mArea = ConvertUtils.getKeyByValue(area, mArea);
        mArea = mArea.equals(all) ? BLANK : mArea;

        mStyle = mStyleData.get(mStyleIndex);
        mStyle = ConvertUtils.getKeyByValue(style, mStyle);
        mStyle = mStyle.equals(all) ? BLANK : mStyle;
        FiltrateContentBean filtrateContentBean = new FiltrateContentBean();
        filtrateContentBean.setHousingType(mLivingRoom);
        filtrateContentBean.setArea(mArea);
        filtrateContentBean.setStyle(mStyle);
        filtrateContentBean.setAreaIndex(mAreaIndex);
        filtrateContentBean.setHouseIndex(mHouseIndex);
        filtrateContentBean.setStyleIndex(mStyleIndex);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CaseLibrarySearch.CONTENT_BEAN, filtrateContentBean);
        intent.putExtras(bundle);
        int type = getIntent().getIntExtra(Constant.CaseLibrarySearch.SEARCH_TYPE, 0);
        switch (type) {
            case 0:
                setResult(CBF_RESULT_CODE, intent);
                break;
            case 1:
                setResult(HC_RESULT_CODE, intent);
                break;
            default:
                break;
        }
        this.finish();
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

    /**
     * 选择某项,并刷新
     *
     * @param adapter  筛选适配器
     * @param position 点击位置
     */
    private void setSelection(FiltrateAdapter adapter, int position) {
        adapter.setSelection(position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 页面销毁时,将各数据集合置为空
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mStyleData = null;
        mHouseData = null;
        mAreaData = null;
    }

    /// 静态常量.
    public static final int CBF_RESULT_CODE = 0x91;
    public static final int HC_RESULT_CODE = 0x93;
    public static final String BLANK = "";

    /// 控件.
    private NoScrollGridView sGridView;
    private NoScrollGridView hGridView;
    private NoScrollGridView aGridView;

    /// 变量.
    private int mStyleIndex = 0;
    private String mArea;
    private String mLivingRoom;
    private String mStyle;
    private int mHouseIndex = 0;
    private int mAreaIndex = 0;

    /// 集合,类.
    private FiltrateAdapter mSAdapter;
    private FiltrateAdapter mHAdapter;
    private FiltrateAdapter mAAdapter;
    private List<String> mStyleData = new ArrayList<>();
    private List<String> mHouseData = new ArrayList<>();
    private List<String> mAreaData = new ArrayList<>();
}
