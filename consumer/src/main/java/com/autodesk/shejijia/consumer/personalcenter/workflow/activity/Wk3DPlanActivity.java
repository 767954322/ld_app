package com.autodesk.shejijia.consumer.personalcenter.workflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPDesignFileBean;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.MPFileBean;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.consumer.personalcenter.workflow.adapter.Wk3DFinishDeliveryAdapter;
import com.autodesk.shejijia.consumer.personalcenter.workflow.adapter.Wk3DLevelZeroAdapter;
import com.autodesk.shejijia.consumer.personalcenter.workflow.adapter.Wk3DPlanAdapter;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanDelivery;
import com.autodesk.shejijia.consumer.personalcenter.workflow.entity.Wk3DPlanListBean;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.DeliverySelector;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;

import java.util.ArrayList;

/**
 * @author he.liu .
 * @version 1.0 .
 * @date 16-6-7
 * @file Wk3DPlanActivity.java  .
 * @brief 选择3D交付物的页面 .
 */
public class Wk3DPlanActivity extends NavigationBarActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wk3_dplan;
    }

    @Override
    protected void initView() {
        super.initView();
        mGridView3DPlan = (GridView) findViewById(R.id.gridView_3dplan);
        mBtnSubmit3DPlan = (Button) findViewById(R.id.btn_submit_3dplan);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        Bundle level_bundle = intent.getBundleExtra(Constant.DeliveryBundleKey.LEVEL_BUNDLE);

        if (null != level_bundle) {
            int delivery_state = level_bundle.getInt(Constant.DeliveryBundleKey.DELIVERY_STATE);
            mLevel = level_bundle.getInt(Constant.DeliveryBundleKey.LEVEL);
            /**
             * 0代表交付完成
             */
            if (delivery_state == 0) {
                doneDelivery(level_bundle, mLevel);
            } else if (delivery_state == 1) {
                /**
                 * 1代表正在交付中
                 */
                doingDelivery(level_bundle, mLevel);
            }
        } else {
            MyToast.show(this, UIUtils.getString(R.string.to_get_data_fail_try_again_later));
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        showSubmitBtnZero();
        showSubmitBtnOthers();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void leftNavButtonClicked(View view) {
        setAdapterListener();
        super.leftNavButtonClicked(view);
    }

    private void setAdapterListener() {
        if (mWk3DLevelZeroAdapter != null) {
            setWk3DLevelZeroAdapterOnItemCheckListener();
        }
        if (mWk3DPlanAdapter != null) {
            setWk3DPlanAdapterOnItemCheckListener();
        }
    }

    /**
     * 我的３D设计方案，适配器的单击事件
     */
    private void setWk3DLevelZeroAdapterOnItemCheckListener() {
        mWk3DLevelZeroAdapter.setOnItemCheckListener(new Wk3DLevelZeroAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(ToggleButton toggleButton, int position, boolean isChecked, ImageButton chooseBt) {
                if (isChecked) {
                    chooseBt.setImageResource(R.drawable.icon_common_radio_on);
                    if (mWk3DPlanListBeanArrayList != null && mWk3DPlanListBeanArrayList.size() > 0) {
                        mSelectDesignAssetId = mWk3DPlanListBeanArrayList.get(position).getDesign_asset_id();
                    }
                } else {
                    chooseBt.setImageResource(R.drawable.icon_common_radio_off);
                    mSelectDesignAssetId = "";
                }
                DeliverySelector.isSelected = false;
                DeliverySelector.select_design_asset_id = mSelectDesignAssetId;
                mLink = mWk3DPlanListBeanArrayList.get(position).getLink();
                DeliverySelector.sLink = mLink;
                showSubmitBtnZero();
            }
        });
        setSubmit3DPlanOnClickListener(true);
    }

    /**
     * Wk3DPlanAdapter适配器的单击事件
     */
    private void setWk3DPlanAdapterOnItemCheckListener() {
        mWk3DPlanAdapter.setOnItemCheckListener(new Wk3DPlanAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(ToggleButton toggleButton, int position, boolean isChecked, ImageButton chooseBt) {
                if (mDesignFileIdArrayListOthers != null) {
                    mDesignFileIdArrayList.addAll(mDesignFileIdArrayListOthers);
                }
                if (mDesignFileEntities != null) {
                    String design_file_id = mDesignFileEntities.get(position).getId();
                    if (isChecked) {
                        chooseBt.setImageResource(R.drawable.icon_common_radio_on);
                        mDesignFileIdArrayList.add(design_file_id);
                    } else {
                        chooseBt.setImageResource(R.drawable.icon_common_radio_off);
                        mDesignFileIdArrayList.remove(design_file_id);
                        ArrayList<String> strings1 = DeliverySelector.select_design_file_id_map.get(mLevel);
                        if (strings1 != null) {
                            strings1.remove(design_file_id);
                        }
                        if (mLevel == 4) {
                            ArrayList<String> strings = DeliverySelector.select_design_file_id_map.get(mLevel);
                            if (strings != null && strings.size() > position) {
                                strings.remove(position);
                            }
                        }
                    }
                    DeliverySelector.select_design_file_id_map.put(mLevel, mDesignFileIdArrayList);
                    showSubmitBtnOthers();
                }
            }
        });
        setSubmit3DPlanOnClickListener(false);
    }

    /**
     * 确定按钮的点击事件
     *
     * @param flag 是否提交
     */
    private void setSubmit3DPlanOnClickListener(final boolean flag) {
        mBtnSubmit3DPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    /**
                     * 假如有多套3D方案，通过传递过去的选择的asset_id 和保存的
                     */
                    if (DeliverySelector.isSelected) {
                        mSelectDesignAssetId = DeliverySelector.select_design_asset_id;
                        mLink = DeliverySelector.sLink;
                    }
                    DeliverySelector.isSelected = true;
                    bundle.putString(Constant.DeliveryResponseBundleKey.DESIGN_ASSET_ID, mSelectDesignAssetId);  /// 选中的某一套3D方案 .
                    bundle.putString(Constant.DeliveryResponseBundleKey.FILE_LINK, mLink);                                           /// 选中的3D方案的id .
                    intent.putExtra(Constant.DeliveryResponseBundleKey.RESPONSE_BUNDLE, bundle);
                    setResult(RESULT_OK, intent);
                } else {
                    DeliverySelector.isSelected = false;
                    mBtnSubmit3DPlan.setClickable(true);
                }
                finish();
            }
        });
    }

    /**
     * 交付状态进行中
     *
     * @param level_bundle 上个页面传过来的bundle
     * @param level        上个页面选择的第几个item
     */
    private void doingDelivery(Bundle level_bundle, int level) {
        if (level == 0) {
            refreshGridViewLevelZero(level_bundle);
        } else {
            mDesignFileEntities = (ArrayList<MPDesignFileBean>) level_bundle.getSerializable(Constant.DeliveryBundleKey.THREE_PLAN);
            refreshGridViewOthers(mDesignFileEntities, level);
        }
        setAdapterListener();
    }

    /**
     * 交付完成
     *
     * @param level_bundle 上个页面传过来的bundle
     * @param level        上个页面选择的第几个item
     */
    private void doneDelivery(Bundle level_bundle, int level) {
        mBtnSubmit3DPlan.setVisibility(View.GONE);
        mDeliveryFilesEntities = (ArrayList<MPFileBean>) level_bundle.getSerializable(Constant.DeliveryBundleKey.DELIVERY_ENTITY);
        if (null != mDeliveryFilesEntities) {
            switch (level) {
                case 0:
                    setTitleForNavbar(UIUtils.getString(R.string.three_plan));
                    break;
                case 1:
                    setTitleForNavbar(UIUtils.getString(R.string.flow_rendering_design));
                    break;
                case 2:
                    setTitleForNavbar(UIUtils.getString(R.string.flow_design_blueprint));
                    break;
                case 3:
                    setTitleForNavbar(UIUtils.getString(R.string.flow_bill_of_materials));
                    break;
                case 4:
                    setTitleForNavbar(UIUtils.getString(R.string.my_room_deliverable));
            }
            Wk3DFinishDeliveryAdapter wk3DFinishDeliveryAdapter = new Wk3DFinishDeliveryAdapter(UIUtils.getContext(), mDeliveryFilesEntities);
            mGridView3DPlan.setAdapter(wk3DFinishDeliveryAdapter);
            mGridView3DPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Wk3DPlanActivity.this, Wk3DPlanShowActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DeliveryShowBundleKey._IMAGE_BEAN, mDeliveryFilesEntities.get(position));
                    bundle.putBoolean(Constant.DeliveryShowBundleKey._LEVEL_TAG, false);
                    intent.putExtra(Constant.DeliveryShowBundleKey._BUNDLE_INTENT, bundle);
                    startActivity(intent);
                }
            });
        } else {
            MyToast.show(this, UIUtils.getString(R.string.to_get_data_fail_try_again_later));
        }
    }

    /**
     * 针对3D方案进行选择一个
     *
     * @param level_bundle 上个页面传过来的bundle
     */
    private void refreshGridViewLevelZero(Bundle level_bundle) {
        setTitleForNavbar(UIUtils.getString(R.string.three_plan));
        mWk3DPlanListBeanArrayList = (ArrayList<Wk3DPlanListBean>) level_bundle.getSerializable(Constant.DeliveryBundleKey.THREE_PLAN_ALL);
        mWk3DLevelZeroAdapter = new Wk3DLevelZeroAdapter(Wk3DPlanActivity.this, mWk3DPlanListBeanArrayList, DeliverySelector.select_design_asset_id);
        mGridView3DPlan.setAdapter(mWk3DLevelZeroAdapter);
    }

    /**
     * 更新选中某个3D方案后，给每个level设置相应数据，即design_file
     */
    private void refreshGridViewOthers(final ArrayList<MPDesignFileBean> design_file, final int level) {
        switch (level) {
            case 1:
                setTitleForNavbar(UIUtils.getString(R.string.flow_rendering_design));
                break;
            case 2:
                setTitleForNavbar(UIUtils.getString(R.string.flow_design_blueprint));
                break;
            case 3:
                setTitleForNavbar(UIUtils.getString(R.string.flow_bill_of_materials));
            case 4:
                setTitleForNavbar(UIUtils.getString(R.string.delivery_quantity_room));
                break;
        }
        mDesignFileIdArrayListOthers = DeliverySelector.select_design_file_id_map.get(level);
        mWk3DPlanAdapter = new Wk3DPlanAdapter(Wk3DPlanActivity.this, design_file, mDesignFileIdArrayListOthers);
        mGridView3DPlan.setAdapter(mWk3DPlanAdapter);
    }

    /**
     * 3D方案是否选中
     */
    public void showSubmitBtnZero() {
        if (!TextUtils.isEmpty(DeliverySelector.select_design_asset_id)) {
            sureSubmit();
        } else {
            cancelSubmit();
            if (mLevel <= 3) {
                DeliverySelector.select_design_file_id_map.clear();
            }
        }
    }

    /**
     * 渲染图＼设计图纸＼材料清单是否选中
     */
    public void showSubmitBtnOthers() {
        if (mLevel != -1) {
            ArrayList<String> strings = DeliverySelector.select_design_file_id_map.get(mLevel);
            if (strings != null && strings.size() > 0) {
                sureSubmit();
            } else {
                cancelSubmit();
            }
        }
    }

    /**
     * 尚未符合提交条件
     */
    private void cancelSubmit() {
        DeliverySelector.isSelected = false;
        mBtnSubmit3DPlan.setPressed(false);
        mBtnSubmit3DPlan.setClickable(false);
        mBtnSubmit3DPlan.setBackgroundResource(R.drawable.bg_common_btn_pressed);
    }

    /**
     * 确认提交
     */
    private void sureSubmit() {
        DeliverySelector.isSelected = true;
        mBtnSubmit3DPlan.setPressed(true);
        mBtnSubmit3DPlan.setClickable(true);
        mBtnSubmit3DPlan.setBackgroundResource(R.drawable.bg_common_btn_blue);
    }

    @Override
    public void onBackPressed() {
        setAdapterListener();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private Button mBtnSubmit3DPlan;
    private GridView mGridView3DPlan;

    private ArrayList<String> mDesignFileIdArrayList = new ArrayList();                  /// 用于存放选中的design_file_id的集合 .
    private ArrayList<String> mDesignFileIdArrayListOthers;
    private ArrayList<MPDesignFileBean> mDesignFileEntities;   /// 当前3D方案的所有设计图 .
    private ArrayList<Wk3DPlanListBean> mWk3DPlanListBeanArrayList;               ///用于存储有不同design_asset_id的实体类的集合 .
    private ArrayList<MPFileBean> mDeliveryFilesEntities;

    private Wk3DLevelZeroAdapter mWk3DLevelZeroAdapter;
    private Wk3DPlanAdapter mWk3DPlanAdapter;

    private String mSelectDesignAssetId;
    private String mLink;
    private int mLevel;
}
