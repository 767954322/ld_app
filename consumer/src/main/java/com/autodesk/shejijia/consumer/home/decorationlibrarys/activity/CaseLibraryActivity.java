package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.HoverCaseAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.adapter.MassiveCasesAdapter;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseLibraryBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.FiltrateContentBean;
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
public class CaseLibraryActivity extends NavigationBarActivity {




    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_library;
    }

    @Override
    protected void initView() {
        super.initView();
        caseMtab = ((MaterialTabs) findViewById(R.id.caseMtab));
        slippingViewPager = ((NoSlippingViewPager) findViewById(R.id.slippingViewPager));
        fragmentList = new ArrayList<Fragment>();

        initTab();
        mFooterView = View.inflate(this, R.layout.view_empty_layout, null);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
//        mListView = (ListView) findViewById(R.id.hover_case_list_view);
        mIvEmpty = ((ImageView) mFooterView.findViewById(R.id.iv_default_empty));

//        mListView.addFooterView(mFooterView);
        setImageForNavButton(ButtonType.RIGHT, R.drawable.icon_search);
        setImageForNavButton(ButtonType.SECONDARY, R.drawable.icon_filtrate_normal);

        setVisibilityForNavButton(ButtonType.RIGHT, true);
        setVisibilityForNavButton(ButtonType.SECONDARY, true);
    }

    private void initTab() {
        fragmentList.add(new MassiveCasesFragment());
        fragmentList.add(new MassiveCasesFragment());
        massiveCasesAdapter = new MassiveCasesAdapter(getSupportFragmentManager(),fragmentList);
        slippingViewPager.setAdapter(massiveCasesAdapter);
        caseMtab.setSameWeightTabs(true);
        caseMtab.setBackgroundColor(Color.WHITE);//Tab的背景色
        caseMtab.setIndicatorColor(Color.RED);//下滑指示器的颜色
        caseMtab.setIndicatorHeight(DensityUtil.dip2px(this, 2));//下滑指示器的高度
        caseMtab.setTextColorSelected(Color.RED);//设置选中的tab字体颜色
        caseMtab.setTextColorUnselected(Color.BLACK);//设置未选中的tab字体颜色
        caseMtab.setTabPaddingLeftRight(40);//设置tab距离左右的padding值
        caseMtab.setTabTypefaceSelectedStyle(Typeface.NORMAL);//选中时候字体
        caseMtab.setTabTypefaceUnselectedStyle(Typeface.NORMAL);//未选中时候字体
        caseMtab.setTextSize(DensityUtil.dip2px(this, 16));
        caseMtab.setViewPager(slippingViewPager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mContext = this;
        screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();

        setTitleForNavbar(UIUtils.getString(R.string.case_library));
//        if (mHoverCaseAdapter == null) {
//            mHoverCaseAdapter = new HoverCaseAdapter(mContext, mCasesEntities, screenWidth, screenHeight);
//        }
//        mListView.setAdapter(mHoverCaseAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
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
                //TODO MERGE 825
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
                //TODO MERGE 825
//                OFFSET = 10;
                mCasesEntities.clear();
                break;
            case 1:
                //TODO MERGE 825
//                OFFSET += 10;
                break;
            default:
                break;
        }
        mCasesEntities.addAll(mCaseLibraryBean.getCases());
        hideFooterView(mCasesEntities);
        mHoverCaseAdapter.notifyDataSetChanged();
        //TODO MERGE 825
//        mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
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



    /// 静态常量,常量,静态上下文.
    public static final int REQUEST_CODE = 0x92;
    public static final String BLANK = "";

    /// 控件.
    private MaterialTabs caseMtab;
    private MassiveCasesAdapter massiveCasesAdapter;
    private ArrayList<Fragment> fragmentList;
    private NoSlippingViewPager slippingViewPager;
    private RelativeLayout mRlEmpty;
    private TextView mTvEmptyMessage;
//    private ListView mListView;
    private ImageView mIvEmpty;
    private View mFooterView;

    private int screenWidth;
    private int screenHeight;
    private boolean isFirstIn = true;
    private Context mContext;
    private FiltrateContentBean mFiltrateContentBean;
    /// 集合,类.
    private HoverCaseAdapter mHoverCaseAdapter;
    private ArrayList<CaseLibraryBean.CasesEntity> mCasesEntities = new ArrayList<>();
}


