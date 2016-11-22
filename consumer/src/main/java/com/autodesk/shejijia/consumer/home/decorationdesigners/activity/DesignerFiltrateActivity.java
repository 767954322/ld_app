package com.autodesk.shejijia.consumer.home.decorationdesigners.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerWorkTimeBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerWorkTimeBean.RelateInformationListBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.FindDesignerBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FiltrateCostAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FiltrateStyleAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.FiltrateWorkYearAdapter;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.uielements.NoScrollGridView;
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
        setTextColorForRightNavButton(UIUtils.getColor(R.color.bg_0084ff));

        allListBean = new RelateInformationListBean();
        allListBean.setName("全部");
        allListBean.setCode("");
        allListBean.setDescription("");
        allListBean.setExtension("");

        getDesignerWorkTime();
        getDesignerCost();
        getDesignerStyles();

        mYAdapter = new FiltrateWorkYearAdapter(this, mWorkTimeList);
        mSAdapter = new FiltrateStyleAdapter(this, mStyleList);
        mPAdapter = new FiltrateCostAdapter(this, mCostList);

        yGridView.setAdapter(mYAdapter);
        sGridView.setAdapter(mSAdapter);
        pGridView.setAdapter(mPAdapter);

        setWorkYearSelection(mYAdapter, mYearIndex);
        setStyleSelection(mSAdapter, mStyleIndex);
        setCostSelection(mPAdapter, mPriceIndex);

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
        if (mWorkTimeList.size() == 0 || mStyleList.size() == 0 || mCostList.size() == 0) {
            return;
        }
        if (null != mWorkTimeList && mYearIndex <= mWorkTimeList.size()) {
            mYear = mWorkTimeList.get(mYearIndex).getCode();
        }
        if (null != mStyleList && mStyleIndex <= mStyleList.size()) {
            mStyleName = mStyleList.get(mStyleIndex).getName();
            mStyleCode = mStyleList.get(mStyleIndex).getCode();
        }
        if (null != mCostList && mPriceIndex <= mCostList.size()) {
            mPrice = mCostList.get(mPriceIndex).getCode();
        }

        String start_experience = "";
        String end_experience = "";

        String[] workTime = mYear.split("-");
        if (null != workTime) {
            if (workTime.length >= 2) {
                start_experience = workTime[0];
                end_experience = workTime[1];
            }
            if (workTime.length == 1) {
                start_experience = end_experience = workTime[0];
            }
        }


        FindDesignerBean findDesignerBean = new FindDesignerBean();
        findDesignerBean.setNick_name("");
        if ("全部".equals(mStyleName)) {
            mStyleName = "";
            mStyleCode = "";
        }
        if (mStyleName.equals("其他")) {
            findDesignerBean.setStyle_names("other");
        } else {
            findDesignerBean.setStyle_names(mStyleName);
        }

        findDesignerBean.setStyle(mStyleCode);
        findDesignerBean.setStart_experience(start_experience);
        findDesignerBean.setEnd_experience(end_experience);
        findDesignerBean.setDesign_price_code(mPrice);

        findDesignerBean.setYearIndex(mYearIndex);
        findDesignerBean.setStyleIndex(mStyleIndex);
        findDesignerBean.setPriceIndex(mPriceIndex);

        Intent intent = new Intent();
        intent.putExtra(Constant.CaseLibrarySearch.DESIGNER_FILTRATE, findDesignerBean);
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals(yGridView)) {
            mYAdapter.setWorkYearSelection(position);
            mYearIndex = position;
            mYAdapter.notifyDataSetChanged();
        } else if (parent.equals(sGridView)) {
            mSAdapter.setStyleSelection(position);
            mStyleIndex = position;
            mSAdapter.notifyDataSetChanged();
        } else if (parent.equals(pGridView)) {
            mPAdapter.setCostSelection(position);
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
    private void setWorkYearSelection(FiltrateWorkYearAdapter adapter, int position) {
        adapter.setWorkYearSelection(position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 选择某项,并刷新
     *
     * @param adapter  筛选适配器
     * @param position 点击位置
     */
    private void setStyleSelection(FiltrateStyleAdapter adapter, int position) {
        adapter.setStyleSelection(position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 选择某项,并刷新
     *
     * @param adapter  筛选适配器
     * @param position 点击位置
     */
    private void setCostSelection(FiltrateCostAdapter adapter, int position) {
        adapter.setCostSelection(position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取设计师从业年限.
     */
    public void getDesignerWorkTime() {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerWorkTimeBean.class);
                mWorkTimeList.addAll(designerWorkTimeBean.getRelate_information_list());
                if (!mWorkTimeList.contains(allListBean)) {
                    mWorkTimeList.add(0, allListBean);
                    mYAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, DesignerFiltrateActivity.this);
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
                designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerWorkTimeBean.class);
                mCostList.addAll(designerWorkTimeBean.getRelate_information_list());
                if (!mCostList.contains(allListBean)) {
                    mCostList.add(0, allListBean);
                    mPAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, DesignerFiltrateActivity.this);
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
                designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerWorkTimeBean.class);
                mStyleList.addAll(designerWorkTimeBean.getRelate_information_list());
                if (!mStyleList.contains(allListBean)) {
                    mStyleList.add(0, allListBean);
                    mSAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, DesignerFiltrateActivity.this);
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
    private String mStyleName;
    private String mStyleCode;
    private String mPrice;

    private FiltrateWorkYearAdapter mYAdapter;
    private FiltrateStyleAdapter mSAdapter;
    private FiltrateCostAdapter mPAdapter;

    private DesignerWorkTimeBean designerWorkTimeBean;
    private List<RelateInformationListBean> mWorkTimeList = new ArrayList<>();
    private List<RelateInformationListBean> mStyleList = new ArrayList<>();
    private List<RelateInformationListBean> mCostList = new ArrayList<>();
    private RelateInformationListBean allListBean;

}
