package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.StoreInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddViewControls;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-10-21 .
 * @file StoreActivity.java .
 * @brief 展示店铺页面 .
 */

public class StoreActivity extends NavigationBarActivity implements View.OnClickListener {


    private LinearLayout showStoreViewGroup;
    private String[] arr;
    private Button storeSureBtn;
    private DynamicAddViewControls dynamicView;
    private StoreInformationBean storeInformationBean;
    private List<StoreInformationBean.StoreListBean> store_list;
    private List<StoreInformationBean.StoreListBean> forResultStoreList;
    private int RESULT_OK = 101;
    private String[] storeNameArr;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.store_sure_btn:

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(JsonConstants.JSON_STORE_NAME_INFOR_CHECK_LIST, (ArrayList<? extends Parcelable>) forResultStoreList);
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.store_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        setTitleForNavbar(UIUtils.getString(R.string.store_show_title));
        showStoreViewGroup = (LinearLayout) findViewById(R.id.ll_store);
        storeSureBtn = (Button) findViewById(R.id.store_sure_btn);

    }

    @Override
    protected void initListener() {
        super.initListener();
        storeSureBtn.setOnClickListener(this);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        String[] storeNameArrList = intent.getStringArrayExtra(JsonConstants.JSON_STORE_NAME_INFOR_LIST);
        if (storeNameArrList.length > 10) {
            storeNameArr = new String[storeNameArrList.length - 4];

            for (int i = 0; i < storeNameArrList.length - 4; i++) {
                storeNameArr[i] = storeNameArrList[i];
            }
        }else {
            storeNameArr = new String[storeNameArrList.length];
            for (int i = 0; i < storeNameArrList.length; i++) {
                storeNameArr[i] = storeNameArrList[i];
            }
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

//        dynamicCreateStoreLine();
        CustomProgress.showDefaultProgress(StoreActivity.this);
        getStore();

    }

    /**
     * 获取店铺
     */
    public void getStore() {

        MPServerHttpManager.getInstance().getStores(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                CustomProgress.cancelDialog();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                CustomProgress.cancelDialog();
                storeInformationBean = GsonUtil.jsonToBean(jsonObject.toString(), StoreInformationBean.class);
                List<StoreInformationBean.StoreListBean> store_list = storeInformationBean.getStore_list();
                getStoreNameAndAdd(storeInformationBean.getStore_list());
            }
        });

    }

    /**
     * 获取店铺名字，并添加
     */

    public void getStoreNameAndAdd(List<StoreInformationBean.StoreListBean> store_list) {
        forResultStoreList = new ArrayList<>();
        arr = new String[store_list.size()+1];
        arr[0] = UIUtils.getString(R.string.all_button_name);
        for (int i = 0; i < store_list.size(); i++) {

            arr[i + 1] = store_list.get(i).getMall_name();
        }

        dynamicView = new DynamicAddViewControls(StoreActivity.this);
        dynamicView.dynamicData(arr);
        showStoreViewGroup.addView(dynamicView);
        dynamicView.checkedStoreForData(storeNameArr);
        dynamicView.setListener(new DynamicAddViewControls.OnButtonClickedListener() {
            @Override
            public void onButtonClicked(BtnStatusBean btnStatusBean) {

                addListForResult(btnStatusBean);
            }
        });
        addStoreForList();
    }

    /**
     * 将已经选中的店铺加入到集合
     */
    public void addStoreForList() {

        TextView[] textViews = dynamicView.getBtnStatustextViews();
        for (int i = 0; i < textViews.length; i++) {
            BtnStatusBean btnStatusBean = (BtnStatusBean) textViews[i].getTag();

            if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 2) {

                addListForResult(btnStatusBean);
            }

        }
    }

    /**
     * 将返回的数据全部添加进入集合
     */
    public void addListForResult(BtnStatusBean btnStatusBean) {

        if (btnStatusBean.getCountOffset() == 0) {
            if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 2) {

                forResultStoreList.clear();
                for (int i = 0; i < storeInformationBean.getStore_list().size(); i++) {

                    forResultStoreList.add(storeInformationBean.getStore_list().get(i));
                }
            }

        } else {
            if (forResultStoreList.size() >= arr.length - 1) {

                forResultStoreList.clear();
            }

            if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 1) {
                String TestForResultStoreList;
                String TestStoreInformationBean;
                for (int i = 0; i < forResultStoreList.size(); i++) {
                    //将取消的店铺移除
                    TestForResultStoreList = forResultStoreList.get(i).getMall_name();
                    TestStoreInformationBean = storeInformationBean.getStore_list().get(btnStatusBean.getCountOffset() - 1).getMall_name();
                    if (TestForResultStoreList.equals(TestStoreInformationBean)) {

                        forResultStoreList.remove(i);
                    }
                }

            } else {

                forResultStoreList.add(storeInformationBean.getStore_list().get(btnStatusBean.getCountOffset() - 1));
            }
        }
    }

}
