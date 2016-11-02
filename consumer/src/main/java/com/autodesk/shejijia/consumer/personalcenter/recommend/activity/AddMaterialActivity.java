package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

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


    private DynamicAddView[] dynamicAddViews;//二级控件集合
    private DynamicPopWindow[] dynamicPopWindows;//二级品类全窗口集合
    private DynamicAddViewContainer dymicAddViewContainer;

    private DynamicPopWindow dynamicPopWindowReuse;//多窗口复用
    private int countArrItem = 0;//对应不同二级品类和窗口
    private HorizontalScrollView one_level_category;
    private HorizontalScrollView two_level_category;
    private ListView show_brand_listView;
    private MaterialCategoryBean materialCategoryBean;
    private Button all_material_btn;
    private Handler getAdapterDataHandler;//获取返回数据handler
    private List<ShowBrandsBean.BrandsBean> getAdapterResultList;
    private DynamicAddViewContainer dynamicAddViewContainer;
    private List<ShowBrandsBean.BrandsBean> listChecked;//选中品牌的集合
    private int brandCount = 0;//可添加品牌的数量
    private TextView remain_brand_count;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.all_material_btn:

                if (dynamicPopWindowReuse != null){

                    dynamicPopWindowReuse.showPopupWindow(all_material_btn);
                }

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
        one_level_category = (HorizontalScrollView) findViewById(R.id.one_level_category);
        show_brand_listView = (ListView) findViewById(R.id.show_brand_listView);
        all_material_btn = (Button) findViewById(R.id.all_material_btn);
        remain_brand_count = (TextView) findViewById(R.id.remain_brand_count);
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
                ShowBrandsBean.BrandsBean brandsBean;
                switch (msg.what){
                    //选中的品牌
                    case 1:

                        brandsBean = (ShowBrandsBean.BrandsBean) msg.obj;
                        listChecked.add(brandsBean);
                        for (int i=0;i < listChecked.size();i++){


                            ToastUtil.showCustomToast(AddMaterialActivity.this,""+listChecked.get(i).getBrand_name());
                        }
                        break;
                    //取消选中的品牌
                    case 2:
                        brandsBean = (ShowBrandsBean.BrandsBean) msg.obj;
                        for (int i=0;i<listChecked.size();i++){

                            String brandName = listChecked.get(i).getBrand_name();
                            if (brandName.equals(brandsBean.getBrand_name())){

                                listChecked.remove(i);
                            }

                        }
                        for (int i= 0;i<listChecked.size();i++){

                            ToastUtil.showCustomToast(AddMaterialActivity.this,""+listChecked.get(i).getBrand_name());
                        }
                        break;

                    default:

                        break;
                }

                showBrandRemainCount();

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

        dynamicAddViews = new DynamicAddView[materialCategoryBean.getCategories_3d().size()];
        dynamicPopWindows = new DynamicPopWindow[materialCategoryBean.getCategories_3d().size()];
        showTwoCategory();
        String[] oneArr = new String[materialCategoryBean.getCategories_3d().size()];
        //获取所有一级品类name
        for (int i = 0;i<materialCategoryBean.getCategories_3d().size();i++){
            oneArr[i] = materialCategoryBean.getCategories_3d().get(i).getCategory_3d_name();
        }
        dynamicAddViewContainer = new DynamicAddViewContainer(this,one_level_category.getHeight());
        dynamicAddViewContainer.setListener(new DynamicAddViewContainer.OnButtonClickedListener() {
            @Override
            public void onButtonClicked(BtnStatusBean btnStatusBean) {

                two_level_category.removeAllViews();
                two_level_category.addView(dynamicAddViews[btnStatusBean.getCountOffset()]);
                if (dynamicPopWindowReuse.isShowing()){

                    dynamicPopWindowReuse.showPopupWindow(all_material_btn);
                }
                dynamicPopWindowReuse = dynamicPopWindows[btnStatusBean.getCountOffset()];
                countArrItem = btnStatusBean.getCountOffset();//不同的一级品类
            }
        });
        //增加默认选项
        dynamicAddViewContainer.dynamicAddData(oneArr);
        one_level_category.addView(dynamicAddViewContainer);
        BtnStatusBean btnStatusBean = new BtnStatusBean();
        btnStatusBean.setCountOffset(0);
        dynamicPopWindows[countArrItem].setButtonCheckedStatus(btnStatusBean);
        dynamicAddViews[countArrItem].setButtonCheckedStatus(btnStatusBean);
    }


    /**
     * 展示二级品类信息
     * */
    public void showTwoCategory(){
        DynamicAddView dynamicAddView;
        for (int i=0;i < materialCategoryBean.getCategories_3d().size();i++){

            dynamicAddView = new DynamicAddView(this);
            dynamicAddViews[i] = dynamicAddView;
            dynamicAddView.dynamicAddData(materialCategoryBean.getCategories_3d().get(i).getSub_category());
            dynamicAddView.setListener(new DynamicAddView.OnButtonClickedListener() {
                @Override
                public void onButtonClicked(BtnStatusBean btnStatusBean) {
                    if (dynamicPopWindows[countArrItem] != null){
                        dynamicPopWindows[countArrItem].setButtonCheckedStatus(btnStatusBean);
                    }
                }
            });

            showAllTwoCategory(materialCategoryBean.getCategories_3d().get(i).getSub_category(),i);
        }
        two_level_category.addView(dynamicAddViews[0]);
        dynamicPopWindowReuse = dynamicPopWindows[0];

    }
    /**
     * 展示二级品类全部
     * */
    public void showAllTwoCategory(List<MaterialCategoryBean.Categories3dBean.SubCategoryBean> list, final int count){
        DynamicPopWindow dynamicPopwindow;
        dynamicPopwindow = new DynamicPopWindow(this,null,R.style.Transparent_Dialog);
        dynamicPopWindows[count] = dynamicPopwindow;
        dynamicPopwindow.addDataForView(list);
        dynamicPopwindow.setListener(new DynamicPopWindow.onButtonPopWindowClickedListener() {
            @Override
            public void onButtonOnClick(BtnStatusBean btnStatusBean) {
                dynamicAddViews[countArrItem].setButtonCheckedStatus(btnStatusBean);
            }
        });
    }

    /**
     * 展示品牌数据
     * */
    public void showBrandsList(List<ShowBrandsBean.BrandsBean> list){
        listChecked = new ArrayList<>();
        AddBrandShowAdapter addBrandShowAdapter = new AddBrandShowAdapter(AddMaterialActivity.this,list,R.layout.add_brand_item,getAdapterDataHandler);
        show_brand_listView.setAdapter(addBrandShowAdapter);

    }

    /**
     * 展示剩余品牌添加数
     * */
    public void showBrandRemainCount(){

        String remainBrandCount = "还可添加"+ (6 - listChecked.size())  +"个品牌";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(remainBrandCount);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.BLUE),4,5,spannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);


        remain_brand_count.setText(spannableStringBuilder);
    }


}
