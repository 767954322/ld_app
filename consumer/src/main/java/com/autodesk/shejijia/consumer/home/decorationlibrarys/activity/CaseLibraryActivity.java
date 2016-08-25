package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.HoverCaseAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseLibraryBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.fragment.MassiveCasesFragment;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.slippingviewpager.NoSlippingViewPager;
import com.autodesk.shejijia.shared.components.common.uielements.mtab.MaterialTabs;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.DensityUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;

import org.json.JSONObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/3/12 0012 17:58 .
 * @filename CaseLibraryActivity.
 * @brief 案例库详情页面.
 */
public class CaseLibraryActivity extends NavigationBarActivity implements PullToRefreshLayout.OnRefreshListener, HoverCaseAdapter.OnItemHoverCaseClickListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_library;
    }

    @Override
    protected void initView() {
        super.initView();
        mPullToRefreshLayout = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
        mListView = (ListView) findViewById(R.id.hover_case_list_view);
        mIvEmpty = ((ImageView) mFooterView.findViewById(R.id.iv_default_empty));

        mListView.addFooterView(mFooterView);
        setImageForNavButton(ButtonType.RIGHT, R.drawable.icon_search);
        setImageForNavButton(ButtonType.SECONDARY, R.drawable.icon_filtrate_normal);

        setVisibilityForNavButton(ButtonType.RIGHT, true);
        setVisibilityForNavButton(ButtonType.SECONDARY, true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mContext = this;
        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();

        setTitleForNavbar(UIUtils.getString(R.string.case_library));
        if (mHoverCaseAdapter == null) {
            mHoverCaseAdapter = new HoverCaseAdapter(mContext, mCasesEntities, screenWidth, screenHeight);
        }
        mListView.setAdapter(mHoverCaseAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mPullToRefreshLayout.setOnRefreshListener(this);
        mHoverCaseAdapter.setOnItemHoverCaseClickListener(this);
    }

    /**
     * 单击item进入该案例详情页面
     *
     * @param position item的位置
     */
    @Override
    public void OnItemHoverCaseClick(int position) {
        String case_id = mCasesEntities.get(position).getId();
        Intent intent = new Intent(mContext, CaseLibraryNewActivity.class);
        intent.putExtra(Constant.CaseLibraryDetail.CASE_ID, case_id);
        startActivity(intent);
    }

    @Override
    protected void secondaryNavButtonClicked(View view) {
        super.secondaryNavButtonClicked(view);
        Intent intent = new Intent(this, FiltrateActivity.class);
        intent.putExtra(Constant.CaseLibrarySearch.SEARCH_TYPE, 1);
        intent.putExtra(Constant.CaseLibrarySearch.AREA_INDEX, mFiltrateContentBean == null ? 0 : mFiltrateContentBean.getAreaIndex());
        intent.putExtra(Constant.CaseLibrarySearch.HOUSING_INDEX, mFiltrateContentBean == null ? 0 : mFiltrateContentBean.getHouseIndex());
        intent.putExtra(Constant.CaseLibrarySearch.STYLE_INDEX, mFiltrateContentBean == null ? 0 : mFiltrateContentBean.getStyleIndex());
        this.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        Intent search = new Intent(this, SearchActivity.class);
        startActivity(search);
    }

    /**
     * 获取案例库数据并刷新
     */
    public void getCaseLibraryData(final String custom_string_style, final String custom_string_type, final String custom_string_keywords,
                                   final String custom_string_area, final String custom_string_bedroom, final String taxonomy_id,
                                   final int offset, final int limit, final String custom_string_restroom, final String custom_string_form, final int state) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                CaseLibraryBean mCaseLibraryBean = GsonUtil.jsonToBean(jsonString, CaseLibraryBean.class);

                updateViewFromData(mCaseLibraryBean, state);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
//                mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
                ApiStatusUtil.getInstance().apiStatuError(volleyError,CaseLibraryActivity.this);
//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, mContext,
//                        AlertView.Style.Alert, null).show();
                hideFooterView(mCasesEntities);
            }
        };
        MPServerHttpManager.getInstance().getCaseListData(custom_string_style, custom_string_type, custom_string_keywords,
                custom_string_area, custom_string_bedroom, taxonomy_id,
                custom_string_restroom, custom_string_form, offset, limit,  callback);
    }

    /**
     * @param mCaseLibraryBean
     * @param state
     */
    private void updateViewFromData(CaseLibraryBean mCaseLibraryBean, int state) {

        switch (state) {
            case 0:
                OFFSET = 10;
                mCasesEntities.clear();
                break;
            case 1:
                OFFSET += 10;
                break;
            default:
                break;
        }
        mCasesEntities.addAll(mCaseLibraryBean.getCases());
        hideFooterView(mCasesEntities);
        mHoverCaseAdapter.notifyDataSetChanged();
        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    /**
     * 是否隐藏底部布局
     *
     * @param list 传入案例库数据集合
     */
    private void hideFooterView(List<CaseLibraryBean.CasesEntity> list) {
        if (list != null && list.size() > 0) {
            mRlEmpty.setVisibility(View.GONE);
        } else {
            mRlEmpty.setVisibility(View.VISIBLE);
        }
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.photopicker_thumbnail_placeholder);
        mIvEmpty.setImageBitmap(bmp);
        mTvEmptyMessage.setText(R.string.no_designer_case);
        WindowManager wm = (WindowManager) CaseLibraryActivity.this.getSystemService(CaseLibraryActivity.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        android.view.ViewGroup.LayoutParams layoutParams = mRlEmpty.getLayoutParams();
        mRlEmpty.getLayoutParams();
        layoutParams.height = height - 10;
        mRlEmpty.setLayoutParams(layoutParams);
        mTvEmptyMessage.setText(UIUtils.getString(R.string.no_designer_case));
    }

    /// 是否显示ListView.
    private void showListView() {
        mPullToRefreshLayout.setVisibility(View.VISIBLE);
        mRlEmpty.setVisibility(View.GONE);
    }

    /// 第一次进入页面刷新.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /// For the first time into the automatic refresh .
        if (isFirstIn) {
            mPullToRefreshLayout.autoRefresh();
            isFirstIn = false;
        }
    }

    /// 下拉刷新.
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        showListView();
        getCaseLibraryData(mFiltrateContentBean == null ? BLANK : mFiltrateContentBean.getStyle(), BLANK, BLANK,
                mFiltrateContentBean == null ? BLANK : mFiltrateContentBean.getArea(), BLANK, "01", 0, LIMIT, BLANK,
                mFiltrateContentBean == null ? BLANK : mFiltrateContentBean.getHousingType(), 0);
    }

    /// 上拉加载.
    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        getCaseLibraryData(mFiltrateContentBean == null ? BLANK : mFiltrateContentBean.getStyle(), BLANK, BLANK,
                mFiltrateContentBean == null ? BLANK : mFiltrateContentBean.getArea(), BLANK, "01", OFFSET, LIMIT, BLANK,
                mFiltrateContentBean == null ? BLANK : mFiltrateContentBean.getHousingType(), 1);
    }

    /// 刷新.
    public void updateNotify(FiltrateContentBean content) {
        this.mFiltrateContentBean = content;
        mPullToRefreshLayout.autoRefresh();
    }

    /**
     * 接收返回来的数据，并做出操作
     *
     * @param resultCode 条件码
     * @param data       回来的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null == data) {
            return;
        }
        Bundle bundle = data.getExtras();
        switch (resultCode) {
            case FiltrateActivity.HC_RESULT_CODE:
                FiltrateContentBean filtrateContentBean = (FiltrateContentBean) bundle.getSerializable(Constant.CaseLibrarySearch.CONTENT_BEAN);
                updateNotify(filtrateContentBean);
                break;
        }
    }


    /// 静态常量,常量,静态上下文.
    public static final int REQUEST_CODE = 0x92;
    public static final String BLANK = "";

    /// 控件.
    private PullToRefreshLayout mPullToRefreshLayout;
    private RelativeLayout mRlEmpty;
    private TextView mTvEmptyMessage;
    private ListView mListView;
    private ImageView mIvEmpty;
    private View mFooterView;

    private int LIMIT = 10;
    private int OFFSET = 0;
    private int screenWidth;
    private int screenHeight;
    private boolean isFirstIn = true;
    private Context mContext;
    private FiltrateContentBean mFiltrateContentBean;
    /// 集合,类.
    private HoverCaseAdapter mHoverCaseAdapter;
    private ArrayList<CaseLibraryBean.CasesEntity> mCasesEntities = new ArrayList<>();
}


