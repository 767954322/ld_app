package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.base.utils.ConvertUtils;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerWorkTimeBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.Filtrate3DAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.uielements.NoScrollGridView;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.AppJsonFileReader;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

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
public class Filtrate3DActivity extends NavigationBarActivity implements AdapterView.OnItemClickListener ,View.OnClickListener{

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_3d_filtrate;
    }

    @Override
    protected void initView() {
        super.initView();
        sGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_style);
        hGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_house);
        aGridView = (NoScrollGridView) findViewById(R.id.gv_filtrate_area);


        tvReset = (TextView) findViewById(R.id.tv_reset);
        tvOk = (TextView) findViewById(R.id.tv_ok);

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

       // setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.select_finish));
        setVisibilityForNavButton(ButtonType.RIGHT,false);
        Resources rs = this.getResources();
        setTextColorForRightNavButton(rs.getColor(R.color.bg_0084ff));

        // mStyleData.addAll(filledData(getResources().getStringArray(R.array.all)));
        //mStyleData.addAll(filledData(getResources().getStringArray(R.array.style)));
        mHouseData.addAll(filledData(getResources().getStringArray(R.array.all3d)));
        mHouseData.addAll(filledData(getResources().getStringArray(R.array.mlivingroom)));
        mAreaData.addAll(filledData(getResources().getStringArray(R.array.all3d)));
        mAreaData.addAll(filledData(getResources().getStringArray(R.array.area)));

        mHAdapter = new Filtrate3DAdapter(Filtrate3DActivity.this, mHouseData);
        mAAdapter = new Filtrate3DAdapter(Filtrate3DActivity.this, mAreaData);
        hGridView.setAdapter(mHAdapter);
        aGridView.setAdapter(mAAdapter);
        setSelection(mHAdapter, mHouseIndex);
        setSelection(mAAdapter, mAreaIndex);

        getDesignerStyles();

    }

    @Override
    protected void initListener() {
        super.initListener();
        /// 各个GridView的监听 .
        sGridView.setOnItemClickListener(this);
        hGridView.setOnItemClickListener(this);
        aGridView.setOnItemClickListener(this);
        tvOk.setOnClickListener(Filtrate3DActivity.this);
        tvReset.setOnClickListener(Filtrate3DActivity.this);
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

    protected void filtrate3DOK() {
        Map<String, String> room = AppJsonFileReader.getRoomHall(this);
        Map<String, String> area = AppJsonFileReader.getArea(this);
        Map<String, String> style = AppJsonFileReader.getStyle(this);

        if (mHouseData.size() == 0 || mStyleData.size() == 0 || mAreaData.size() == 0) {
            return;
        }
        String all = UIUtils.getString(R.string.my_3d_all);
        mLivingRoom = mHouseData.get(mHouseIndex);
        mLivingRoom = ConvertUtils.getKeyByValue(room, mLivingRoom);
        mLivingRoom = mLivingRoom.equals(all) ? BLANK : mLivingRoom;

        mArea = mAreaData.get(mAreaIndex);
        String mmmArea = ConvertUtils.getNewKeyByValue(area, mArea);
        mArea = mArea.equals(all) ? BLANK : mmmArea;

        mStyle = mStyleData.get(mStyleIndex);
        mStyle = ConvertUtils.getKeyByValue(style, mStyle);
        mStyle = mStyle.equals(all) ? BLANK : mStyle;


        FiltrateContentBean filtrateContentBean = new FiltrateContentBean();
        if (mLivingRoom.equals("其它")) {
            filtrateContentBean.setHousingType("other");
        } else {
            filtrateContentBean.setHousingType(mLivingRoom);
        }

//        filtrateContentBean.setHousingType(mLivingRoom);
        filtrateContentBean.setArea(mArea);
        filtrateContentBean.setStyle(mStyle);
        filtrateContentBean.setAreaIndex(mAreaIndex);
        filtrateContentBean.setHouseIndex(mHouseIndex);
        filtrateContentBean.setStyleIndex(mStyleIndex);

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.CaseLibrarySearch.CONTENT_3D_BEAN, filtrateContentBean);
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

    private List<DesignerWorkTimeBean.RelateInformationListBean> mStyleList = new ArrayList<>();
    private DesignerWorkTimeBean.RelateInformationListBean allListBean;

    /**
     * 获取设计师设计风格.
     */
    public void getDesignerStyles() {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonToString = GsonUtil.jsonToString(jsonObject);
                DesignerWorkTimeBean designerWorkTimeBean = GsonUtil.jsonToBean(jsonToString, DesignerWorkTimeBean.class);
                allListBean = new DesignerWorkTimeBean.RelateInformationListBean();
                allListBean.setName("不限");
                allListBean.setCode("");
                allListBean.setDescription("");
                allListBean.setExtension("");
                mStyleList.addAll(designerWorkTimeBean.getRelate_information_list());
                mStyleList.add(0, allListBean);
                for (int i = 0; i < mStyleList.size(); i++) {
                    mStyleData.add(mStyleList.get(i).getName());
                }

                mSAdapter = new Filtrate3DAdapter(Filtrate3DActivity.this, mStyleData);
                sGridView.setAdapter(mSAdapter);
                setSelection(mSAdapter, mStyleIndex);

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                ApiStatusUtil.getInstance().apiStatuError(volleyError, Filtrate3DActivity.this);
            }
        };
        MPServerHttpManager.getInstance().getDesignerStyles(okResponseCallback);
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
    private void setSelection(Filtrate3DAdapter adapter, int position) {
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

    private TextView tvReset;
    private TextView tvOk;
    /// 静态常量.
    public static final int CBF_RESULT_CODE = 0x91;
    public static final int HC_RESULT_CODE = 0x98;
    public static final String BLANK = "";

    /// 控件.
    private NoScrollGridView sGridView;
    private NoScrollGridView hGridView;
    private NoScrollGridView aGridView;

    /// 变量.
    private String mArea;
    private String mLivingRoom;
    private String mStyle;
    private int mStyleIndex = 0;
    private int mHouseIndex = 0;
    private int mAreaIndex = 0;

    /// 集合,类.
    private Filtrate3DAdapter mSAdapter;
    private Filtrate3DAdapter mHAdapter;
    private Filtrate3DAdapter mAAdapter;
    private List<String> mStyleData = new ArrayList<>();
    private List<String> mHouseData = new ArrayList<>();
    private List<String> mAreaData = new ArrayList<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_ok:
                filtrate3DOK();
                break;
            case R.id.tv_reset:
                    mSAdapter.setSelection(0);
                    mStyleIndex =0;
                    mSAdapter.notifyDataSetChanged();
                    mHAdapter.setSelection(0);
                    mHouseIndex = 0;
                    mHAdapter.notifyDataSetChanged();
                    mAAdapter.setSelection(0);
                    mAreaIndex = 0;
                    mAAdapter.notifyDataSetChanged();
                break;
            default:

        }
    }
}
