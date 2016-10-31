package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.StoreInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddViewControls;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.store_sure_btn:

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) forResultStoreList);
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
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

//        dynamicCreateStoreLine();
        getStore();

    }

    /**
     * 获取店铺
     */
    public void getStore() {

        MPServerHttpManager.getInstance().getStores(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                storeInformationBean = GsonUtil.jsonToBean(jsonObject.toString(), StoreInformationBean.class);
                getStoreNameAndAdd(storeInformationBean.getStore_list());
            }
        });

    }

    /**
     * 获取店铺名字，并添加
     */

    public void getStoreNameAndAdd(List<StoreInformationBean.StoreListBean> store_list) {
        forResultStoreList = new ArrayList<>();
        arr = new String[store_list.size() + 1];
        arr[0] = "全部";
        for (int i = 0; i < store_list.size(); i++) {

            arr[i + 1] = store_list.get(i).getMall_name();
        }

        dynamicView = new DynamicAddViewControls(StoreActivity.this);
        dynamicView.dynamicData(arr);
        showStoreViewGroup.addView(dynamicView);
        dynamicView.setListener(new DynamicAddViewControls.OnButtonClickedListener() {
            @Override
            public void onButtonClicked(BtnStatusBean btnStatusBean) {

                addListForResult(btnStatusBean);
            }
        });
    }

    /**
     * 将返回的数据全部添加进入集合
     */
    public void addListForResult(BtnStatusBean btnStatusBean) {

        if (btnStatusBean.getCountOffset() == 0) {
            if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 2) {

                forResultStoreList.clear();
            } else if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 1) {
                for (int i = 0; i < storeInformationBean.getStore_list().size(); i++) {

                    forResultStoreList.add(storeInformationBean.getStore_list().get(i));

                }
            }

        } else {
            if (forResultStoreList.size() >= arr.length - 1) {

                forResultStoreList.clear();
            }

            if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 2) {
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
