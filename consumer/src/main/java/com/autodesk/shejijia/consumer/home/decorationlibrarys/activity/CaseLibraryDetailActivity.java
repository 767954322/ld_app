package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.entity.CaseDetailBean;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.ImageShowView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author DongXueQiu .
 * @version 1.0 .
 * @date 2016/3/25 0025 9:53 .
 * @filename CaseLibraryDetailActivity.
 * @brief 案例详情页面.
 */
public class CaseLibraryDetailActivity extends NavigationBarActivity implements View.OnClickListener, ImageShowView.ImageShowViewListener {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mImageShowView = (ImageShowView) findViewById(R.id.ad_view);
        mLookMore = (LinearLayout) findViewById(R.id.ll_case_detail_look_more);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        case_id = getIntent().getStringExtra(Constant.CaseLibraryDetail.CASE_ID);   /// 获取发过来的ID.
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        getCaseDetailData(case_id);

        if (caseDetailBean != null){

            return;

        }else {

            mLookMore.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mLookMore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_case_detail_look_more:     /// 查看介绍页面.
                Intent intent = new Intent(this, CaseDescriptionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.CaseLibraryDetail.CASE_DETAIL_BEAN, caseDetailBean);
                intent.putExtras(bundle);
                this.startActivity(intent);
                this.overridePendingTransition(R.anim.activity_open, 0);
                break;

            default:
                break;
        }
    }

    /**
     * 查看大图监听
     *
     * @param position  　图片的索引
     * @param imageView 控件
     */
    @Override
    public void onImageClick(int position, View imageView) {
        String url = (String) imageView.getTag();
        Intent intent = new Intent(CaseLibraryDetailActivity.this, GalleryUrlActivity.class);
        intent.putExtra(Constant.CaseLibraryDetail.CASE_URL, url);
        startActivity(intent);
        overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
    }

    /**
     * 获取案例的数据
     *
     * @param case_id 该案例的ID
     */
    public void getCaseDetailData(String case_id) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                String info = GsonUtil.jsonToString(jsonObject);
                caseDetailBean = GsonUtil.jsonToBean(info, CaseDetailBean.class);
                KLog.json(TAG, info);

                updateViewFromData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLibraryDetailActivity.this,
                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().getCaseListDetail(case_id, okResponseCallback);
    }

    /**
     * 网络获取数据，更新页面
     */
    private void updateViewFromData() {
        for (int i = 0; i < caseDetailBean.getImages().size(); i++) {
            if (null != caseDetailBean && caseDetailBean.getImages().size() != 0) {
                imageUrl = caseDetailBean.getImages().get(i).getFile_url() + Constant.CaseLibraryDetail.JPG;
                mImageUrl.add(imageUrl);
            }
        }
        mImageShowView.setImageResources(mImageUrl, this);
        setTitleForNavbar(caseDetailBean.getTitle());
    }

    /// 控件.
    private ImageShowView mImageShowView;
    private LinearLayout mLookMore;

    /// 变量.
    private String case_id;
    private String imageUrl;

    /// 集合,类.
    private CaseDetailBean caseDetailBean;
    private ArrayList<String> mImageUrl = new ArrayList<>();
}
