package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerCostBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerFiltrateBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerStyleBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerWorkTimeBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FiltrateAdapter;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.NoScrollGridView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/8/12 0025 14:46 .
 * @file DesignerFiltrateActivity  .
 * @brief 设计师列表筛选 .
 */
public class DesignerFiltrateActivity extends NavigationBarActivity implements AdapterView.OnItemClickListener {

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
        mYearIndex = getIntent().getIntExtra(Constant.CaseLibrarySearch.YEAR_INDEX, 0);
        mStyleIndex = getIntent().getIntExtra(Constant.CaseLibrarySearch.STYLEL_INDEX, 0);
        mPriceIndex = getIntent().getIntExtra(Constant.CaseLibrarySearch.PRICE_INDEX, 0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.bid_filter));
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.select_finish));

        mYearData.addAll(filledData(getResources().getStringArray(R.array.all)));
        mYearData.addAll(filledData(getResources().getStringArray(R.array.year)));
        mStyleData.addAll(filledData(getResources().getStringArray(R.array.all)));
        mStyleData.addAll(filledData(getResources().getStringArray(R.array.style)));
        mPriceData.addAll(filledData(getResources().getStringArray(R.array.all)));
        mPriceData.addAll(filledData(getResources().getStringArray(R.array.price)));

        mYAdapter = new FiltrateAdapter(this, mYearData);
        mSAdapter = new FiltrateAdapter(this, mStyleData);
        mPAdapter = new FiltrateAdapter(this, mPriceData);

        yGridView.setAdapter(mYAdapter);
        sGridView.setAdapter(mSAdapter);
        pGridView.setAdapter(mPAdapter);

        setSelection(mYAdapter, mYearIndex);
        setSelection(mSAdapter, mStyleIndex);
        setSelection(mPAdapter, mPriceIndex);

    }

    @Override
    protected void initListener() {
        super.initListener();
        yGridView.setOnItemClickListener(this);
        sGridView.setOnItemClickListener(this);
        pGridView.setOnItemClickListener(this);
    }

    /**
     * 筛选
     *
     * @param view 要点击的完成控件
     */
    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);

        String all = UIUtils.getString(R.string.my_bid_all);

        mYear = mYearData.get(mYearIndex);
        mYear = mYear.equals(all) ? BLANK : mYear;

        mStyle = mStyleData.get(mStyleIndex);
        mStyle = mStyle.equals(all) ? BLANK : mStyle;

        mPrice = mPriceData.get(mPriceIndex);
        mPrice = mPrice.equals(all) ? BLANK : mPrice;

        DesignerFiltrateBean designerFiltrateBean = new DesignerFiltrateBean();

        designerFiltrateBean.setWorkingYears(mYear);
        designerFiltrateBean.setStyle(mStyle);
        designerFiltrateBean.setPrice(mPrice);
        designerFiltrateBean.setYearIndex(mYearIndex);
        designerFiltrateBean.setStyleIndex(mStyleIndex);
        designerFiltrateBean.setPriceIndex(mPriceIndex);

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CaseLibrarySearch.DESIGNER_FILTRATE, designerFiltrateBean);
        intent.putExtras(bundle);
        setResult(DF_RESULT_CODE, intent);
        this.finish();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(yGridView)) {
            mYAdapter.setSelection(position);
            mYearIndex = position;
            mYAdapter.notifyDataSetChanged();
        } else if (parent.equals(sGridView)) {
            mSAdapter.setSelection(position);
            mStyleIndex = position;
            mSAdapter.notifyDataSetChanged();
        } else if (parent.equals(pGridView)) {
            mPAdapter.setSelection(position);
            mPriceIndex = position;
            mPAdapter.notifyDataSetChanged();
        }
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
     * 获取设计师从业年限.
     */
    public void getDesignerWorkTime() {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                DesignerWorkTimeBean designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerWorkTimeBean.class);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerFiltrateActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getDesignerExperiences(okResponseCallback);
    }

    /**
     * 获取设计师设计费区间.
     */
    public void getDesignerCost() {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                DesignerCostBean designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerCostBean.class);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerFiltrateActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getDesignerCost(okResponseCallback);
    }

    /**
     * 获取设计师设计风格.
     */
    public void getDesignerStyles() {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                DesignerStyleBean designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerStyleBean.class);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, DesignerFiltrateActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getDesignerStyles(okResponseCallback);
    }

    private NoScrollGridView yGridView;
    private NoScrollGridView sGridView;
    private NoScrollGridView pGridView;

    private int mYearIndex = 0;
    private int mStyleIndex = 0;
    private int mPriceIndex = 0;
    private String mYear;
    private String mStyle;
    private String mPrice;

    public static final int DF_RESULT_CODE = 0;
    public static final String BLANK = "";

    private FiltrateAdapter mYAdapter;
    private FiltrateAdapter mSAdapter;
    private FiltrateAdapter mPAdapter;

    private List<String> mYearData = new ArrayList<>();
    private List<String> mStyleData = new ArrayList<>();
    private List<String> mPriceData = new ArrayList<>();


}
