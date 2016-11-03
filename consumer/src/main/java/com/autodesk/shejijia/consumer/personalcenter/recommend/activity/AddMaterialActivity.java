package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.AddBrandShowAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MaterialCategoryBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.StoreInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddViewContainer;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddView;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicPopWindow;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest.OKResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-10-25 .
 * @file AddMaterialActivity.java .
 * @brief 添加主材页面 .
 */

public class AddMaterialActivity extends NavigationBarActivity implements View.OnClickListener {


    private DynamicAddView dynamicAddView;
    private DynamicAddViewContainer dymicAddViewContainer;

    private DynamicPopWindow dynamicPopwindow;
    private HorizontalScrollView one_level_category;
    private HorizontalScrollView two_level_category;
    private ListView show_brand_listView;
    private MaterialCategoryBean materialCategoryBean;
    private Button all_material_btn;
    private Handler getAdapterDataHandler;//获取返回数据handler
    private List<RecommendBrandsBean> getAdapterResultList;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.all_material_btn:

                dynamicPopwindow.showPopupWindow(all_material_btn);

                break;

        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.add_material_activity;
    }

    @Override
    protected void initView() {

        setTitleForNavbar(UIUtils.getString(R.string.add_material));
        setTitleForNavButton(ButtonType.RIGHT, UIUtils.getString(R.string.store_show_title));
        setTextColorForRightNavButton(UIUtils.getColor(R.color.tx_ef));
        two_level_category = (HorizontalScrollView) findViewById(R.id.two_level_category);
//        one_level_category = (HorizontalScrollView) findViewById(R.id.one_level_category);
        show_brand_listView = (ListView) findViewById(R.id.show_brand_listView);
        all_material_btn = (Button) findViewById(R.id.all_material_btn);
    }

    @Override
    protected void initListener() {
        super.initListener();
        all_material_btn.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        getCategoryInformation();
        getMaterialCategoryBrandsInformation("","","","","","",0,20);
        getAdapterResultList = new ArrayList<>();
        getAdapterDataHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);


            }
        };

    }

    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMaterialActivity.this, StoreActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    /**
     * 获取店面回传回来的信息
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){

            case 101:
                Bundle bundle = data.getExtras();
                List<StoreInformationBean.StoreListBean> forResultStoreList = (List<StoreInformationBean.StoreListBean>) bundle.get("list");
                ToastUtil.showCustomToast(AddMaterialActivity.this,""+forResultStoreList.size());
                break;
            default:
                break;
        }
    }

    /**
     * 获取一级二级品类信息
     * */
    public void getCategoryInformation(){

        MPServerHttpManager.getInstance().getMaterialCategoryInformation(new OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                materialCategoryBean = GsonUtil.jsonToBean(jsonObject.toString(),MaterialCategoryBean.class);
                showOneCategory();
            }
        });

    }

    /**
     * 根据品类获取品牌信息
     * */
    public void getMaterialCategoryBrandsInformation(String category_3d_id, String category_3d_name, String sub_category_3d_id,
                                                     String sub_category_3d_name, String mall_number, String decoration_company_number,
                                                     Integer offset, Integer limit ){

        MPServerHttpManager.getInstance().getCategoryBrandsInformation(category_3d_id, category_3d_name, sub_category_3d_id, sub_category_3d_name,
                mall_number, decoration_company_number, offset, limit, new OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                RecommendSCFDBean showBrandsBean =  GsonUtil.jsonToBean(jsonObject.toString(),RecommendSCFDBean.class);
                showBrandsList(showBrandsBean.getBrands());
            }
        });
    }


    /**
     * 展示一级品类信息
     * */
    public void showOneCategory(){


        showTwoCategory(materialCategoryBean.getCategories_3d().get(0).getSub_category());
    }


    /**
     * 展示二级品类信息
     * */
    public void showTwoCategory(List<MaterialCategoryBean.Categories3dBean.SubCategoryBean> list){

        dynamicAddView = new DynamicAddView(this);
        dynamicAddView.dynamicAddData(list);
        dynamicAddView.setListener(new DynamicAddView.OnButtonClickedListener() {
            @Override
            public void onButtonClicked(BtnStatusBean btnStatusBean) {
                if (dynamicPopwindow != null){
                    dynamicPopwindow.setButtonCheckedStatus(btnStatusBean);
                }
            }
        });
        two_level_category.addView(dynamicAddView);

        showAllTwoCategory(list);
    }
    /**
     * 展示二级品类全部
     * */
    public void showAllTwoCategory(List<MaterialCategoryBean.Categories3dBean.SubCategoryBean> list){

        dynamicPopwindow = new DynamicPopWindow(this,null,R.style.Transparent_Dialog);
        dynamicPopwindow.addDataForView(list);
        dynamicPopwindow.setListener(new DynamicPopWindow.onButtonPopWindowClickedListener() {
            @Override
            public void onButtonOnClick(BtnStatusBean btnStatusBean) {
                ToastUtil.showCustomToast(AddMaterialActivity.this,""+btnStatusBean.getCountOffset());
                dynamicAddView.setButtonCheckedStatus(btnStatusBean);
            }
        });
    }

    /**
     * 展示品牌数据
     * */
    public void showBrandsList(List<RecommendBrandsBean> list){

        AddBrandShowAdapter addBrandShowAdapter = new AddBrandShowAdapter(AddMaterialActivity.this,list,R.layout.add_brand_item);
        show_brand_listView.setAdapter(addBrandShowAdapter);

    }
}
