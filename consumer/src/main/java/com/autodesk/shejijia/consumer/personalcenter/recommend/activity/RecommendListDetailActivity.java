package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.RecommendExpandableAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.CheckedInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MaterialCategoryBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendDetailsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.CustomHeaderExpandableListView;
import com.autodesk.shejijia.consumer.personalcenter.recommend.widget.BrandChangListener;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.OnItemClickListener;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.autodesk.shejijia.shared.components.common.utility.GsonUtil.jsonToBean;

/**
 * 清单页面:空白页面及编辑清单页面
 *
 * @author liuhea
 *         created at 16-10-24
 */
public class RecommendListDetailActivity extends NavigationBarActivity implements View.OnClickListener, BrandChangListener,
        ExpandableListView.OnGroupClickListener {
    private CustomHeaderExpandableListView mExpandListView;
    private AppCompatButton mBtnListSend;
    private Activity mActivity;
    private String mAsset_id;
    private LinearLayout mLlEmptyContentView;
    private RecommendExpandableAdapter mRecommendExpandableAdapter;
    private String mScfd;
    private List<RecommendSCFDBean> mRecommendSCFDList;

    public static void actionStartActivity(Context context, String asset_id) {
        Intent intent = new Intent(context, RecommendListDetailActivity.class);
        intent.putExtra("asset_id", asset_id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recommend_list_detail;
    }

    @Override
    protected void initView() {

        super.initView();
        mExpandListView = (CustomHeaderExpandableListView) findViewById(R.id.rcy_recommend_detail);
        mBtnListSend = (AppCompatButton) findViewById(R.id.btn_list_send);
        mLlEmptyContentView = (LinearLayout) findViewById(R.id.empty_view);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mAsset_id = intent.getStringExtra("asset_id");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mActivity = this;
        mRecommendSCFDList = new ArrayList<>();
        mExpandListView.setHeaderView(getLayoutInflater().inflate(R.layout.item_group_indicator,
                mExpandListView, false));
        mRecommendExpandableAdapter = new RecommendExpandableAdapter(this, mRecommendSCFDList, mExpandListView);
        mExpandListView.setAdapter(mRecommendExpandableAdapter);

        getRecommendDraftDetail();
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnListSend.setOnClickListener(this);
        mExpandListView.setGroupIndicator(null); //去掉箭头
        //点击不可收缩
        mExpandListView.setOnGroupClickListener(this);
        mRecommendExpandableAdapter.setBrandChangListener(this);
    }

    /**
     * 从推荐草稿中获取推荐清单信息
     */
    private void getRecommendDraftDetail() {
        CustomProgress.showDefaultProgress(mActivity);
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                String jsonString = jsonObject.toString();
                Log.d("RecommendListDetailAc", jsonString);
                RecommendDetailsBean recommendListDetailBean = jsonToBean(jsonString, RecommendDetailsBean.class);
                updateUI(recommendListDetailBean);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
                MPNetworkUtils.logError(TAG, volleyError);
            }
        };
        MPServerHttpManager.getInstance().getRecommendDraftDetail(mAsset_id, callback);
    }

    /**
     * 保存推荐清单详情页面
     */
    private void saveRecommendDetail() {
        Toast.makeText(mActivity, "保存", Toast.LENGTH_SHORT).show();
    }

    private void updateUI(RecommendDetailsBean recommendListDetailBean) {
        setTitle(recommendListDetailBean);

        mScfd = recommendListDetailBean.getScfd();
        updateItemView(mScfd);
    }

    private void updateItemView(String scfd) {
        // 更新适配器
        List<RecommendSCFDBean> recommendscfd = new Gson()
                .fromJson(scfd, new TypeToken<List<RecommendSCFDBean>>() {
                }.getType());

        if (null == recommendscfd) {
            mLlEmptyContentView.setVisibility(View.VISIBLE);
            return;
        }
        mRecommendSCFDList.addAll(recommendscfd);
        mLlEmptyContentView.setVisibility(View.GONE);
        mRecommendExpandableAdapter.notifyDataSetChanged();
        for (int i = 0; i < mRecommendSCFDList.size(); i++) {
            mExpandListView.expandGroup(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_list_send:
                Log.d("RecommendListDetailActi", "mRecommendSCFDList:" + mRecommendSCFDList);
                break;
        }
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        ViewCategoryActivity.jumpTo(RecommendListDetailActivity.this, mScfd, groupPosition);
        return true;
    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        //TODO @xuehua.yao
        Intent intent = new Intent(RecommendListDetailActivity.this, AddMaterialActivity.class);
        intent.putExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN, (Serializable) mRecommendSCFDList);
        startActivityForResult(intent, 24);

    }

    @Override
    protected void leftNavButtonClicked(View view) {
        if (mRecommendSCFDList == null || mRecommendSCFDList.size() <= 0) {
            finish();
        } else {
            new AlertView("", "当前清单还未发送，是否保存？",
                    "取消", null, new String[]{UIUtils.getString(R.string.sure)}, RecommendListDetailActivity.this, AlertView.Style.Alert, new OnItemClickListener() {
                @Override
                public void onItemClick(Object object, int position) {
                    if (position != AlertView.CANCELPOSITION) {
                        saveRecommendDetail();
                    } else {
                        RecommendListDetailActivity.this.finish();
                    }
                }
            }).show();
        }
    }

    private void setTitle(RecommendDetailsBean recommendListDetailBean) {
        setTitleForNavbar(recommendListDetailBean.getCommunity_name());
        setTitleForNavButton(ButtonType.RIGHT, "添加主材");
        setTextColorForRightNavButton(UIUtils.getColor(R.color.search_text_color));
    }


    @Override
    public void onBrandChangListener(RecommendSCFDBean recommendSCFDBean, String brandCode) {
        Intent intent = new Intent(mActivity, ChangeBrandActivity.class);
        intent.putExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN, recommendSCFDBean);
        intent.putExtra("brandCode", brandCode);
        mActivity.startActivityForResult(intent, 21);
    }

    @Override
    public void onBrandAddListener(RecommendSCFDBean recommendSCFDBean) {
        Intent intent = new Intent(mActivity, AddBrandActivity.class);
        intent.putExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN, recommendSCFDBean);
        startActivityForResult(intent, 22);
    }

    @Override
    public void onSubCategoryDeleteListener(int groupPosition) {
        mRecommendSCFDList.remove(groupPosition);
        mRecommendExpandableAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (null != data) {
                switch (requestCode) {
                    case 21: // 品牌变更．
                        RecommendSCFDBean mRecommendSCFDBean = (RecommendSCFDBean) data.getSerializableExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN);
                        for (RecommendSCFDBean recommendSCFDBean : mRecommendSCFDList) {
                            if (recommendSCFDBean.getSub_category_3d_id().equals(mRecommendSCFDBean.getSub_category_3d_id())) {
                                int post = mRecommendSCFDList.indexOf(recommendSCFDBean);
                                mRecommendSCFDList.remove(recommendSCFDBean);
                                mRecommendSCFDList.add(post, mRecommendSCFDBean);
                                break;
                            }
                        }
                        mRecommendExpandableAdapter.notifyDataSetChanged();

                        break;

                    case 22:// 添加品牌．
                        List<RecommendBrandsBean> brandAddList = (List<RecommendBrandsBean>) data.getSerializableExtra(JsonConstants.RECOMMENDBRANDBEAN);//接
                        String sub_category_3d_id = data.getStringExtra(Constant.JsonLocationKey.SUB_CATEGORY_3D_ID);
                        for (RecommendSCFDBean recommendSCFDBean : mRecommendSCFDList) {
                            if (recommendSCFDBean.getSub_category_3d_id().equals(sub_category_3d_id)) {
                                int post = mRecommendSCFDList.indexOf(recommendSCFDBean);
                                mRecommendSCFDList.remove(recommendSCFDBean);
                                recommendSCFDBean.getBrands().addAll(brandAddList);
                                mRecommendSCFDList.add(post, recommendSCFDBean);
                                break;
                            }
                        }
                        mRecommendExpandableAdapter.notifyDataSetChanged();

                        break;

                    case 23:// 定位二级品类．
                        int intExtra = data.getIntExtra(ViewCategoryActivity.LOCATION, 0);
                        mExpandListView.setSelection(intExtra);
                        break;

                    case 24: // 添加主材．
                        Bundle bundle = data.getExtras();
                        List<CheckedInformationBean> checkedInformationBeanList = (List<CheckedInformationBean>) bundle.get("totalList");
//                        private List<RecommendBrandsBean> checkedBrandsInformationBean; // 选中的品牌信息Bean
//                        private MaterialCategoryBean.Categories3dBean.SubCategoryBean subCategoryBean;//二级品类信息bean
                        ArrayList<RecommendSCFDBean> recommendSCFDListTemp = new ArrayList<>();
                        for (CheckedInformationBean checkedInformationBean : checkedInformationBeanList) {
                            // [1]获取主材,对比之．
                            // [2]对比主材及品牌
                            //   [2.1]已有主材，在集合中添加．
                            //   [2.2]未有主材，新建主材项．
                            MaterialCategoryBean.Categories3dBean.SubCategoryBean materialSubCategoryBean = checkedInformationBean.getSubCategoryBean();
                            String material_sub_category_3d_id1 = materialSubCategoryBean.getSub_category_3d_id();

                            for (RecommendSCFDBean recommendSCFDBean : mRecommendSCFDList) {
                                String sub_category_3d_id2 = recommendSCFDBean.getSub_category_3d_id();
                                // 已有二级品类．
                                if (material_sub_category_3d_id1.equalsIgnoreCase(sub_category_3d_id2)) {
                                    List<RecommendBrandsBean> checkedBrandsInformationBean = checkedInformationBean.getCheckedBrandsInformationBean();

                                    int post = mRecommendSCFDList.indexOf(recommendSCFDBean);
                                    mRecommendSCFDList.remove(recommendSCFDBean);
                                    ArrayList<RecommendBrandsBean> recommendBrandsBeans = new ArrayList<>();
                                    recommendBrandsBeans.addAll(checkedBrandsInformationBean);
                                    recommendSCFDBean.getBrands().addAll(recommendBrandsBeans);
                                    mRecommendSCFDList.add(post, recommendSCFDBean);
                                } else {
                                    // 新增二级品类．
                                    RecommendSCFDBean recommendSCFDBean1 = new RecommendSCFDBean();
                                    List<RecommendBrandsBean> checkedBrandsInformationBean = checkedInformationBean.getCheckedBrandsInformationBean();
                                    recommendSCFDBean1.setSub_category_3d_name(materialSubCategoryBean.getSub_category_3d_name());
                                    recommendSCFDBean1.setSub_category_3d_id(materialSubCategoryBean.getSub_category_3d_id());
                                    recommendSCFDBean1.setBrands(checkedBrandsInformationBean);
                                    recommendSCFDListTemp.add(recommendSCFDBean1);
                                }
                            }
                        }
                        mRecommendSCFDList.addAll(recommendSCFDListTemp);
                        mRecommendExpandableAdapter.notifyDataSetChanged();
                        break;

                    default:
                        break;
                }
            }
        }
    }
}
