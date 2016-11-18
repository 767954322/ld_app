package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.AddBrandShowAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.CheckedInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MaterialCategoryBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendMallsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.StoreInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddView;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddViewContainer;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicPopWindow;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicScrollView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest.OKResponseCallback;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.SingleClickUtils;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version v1.0 .
 * @date 16-10-25 .
 * @file AddMaterialActivity.java .
 * @brief 添加主材页面 .
 */

public class AddMaterialActivity extends NavigationBarActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener, DynamicScrollView.MyScrollViewListener {


    private DynamicAddView[] dynamicAddViews;//二级控件集合
    private DynamicPopWindow[] dynamicPopWindows;//二级品类全窗口集合
    private DynamicAddViewContainer dymicAddViewContainer;

    private DynamicPopWindow dynamicPopWindowReuse;//多窗口复用
    private int countArrItem = 0;//对应不同二级品类和窗口
    private HorizontalScrollView one_level_category;
    private DynamicScrollView two_level_category;
    private PullListView show_brand_listView;
    private MaterialCategoryBean materialCategoryBean;
    private TextView all_material_btn;
    private Handler getAdapterDataHandler;//获取返回数据handler
    private List<RecommendBrandsBean> getAdapterResultList;
    private DynamicAddViewContainer dynamicAddViewContainer;
    private List<RecommendBrandsBean> listChecked;
    //选中品牌的集合
    private int brandCount = 0;//可添加品牌的数量
    private TextView remain_brand_count;
    private PullToRefreshLayout mPullToRefreshLayout;
    private AddBrandShowAdapter addBrandShowAdapter;
    private int justRefreshOrLoadMore = 1;//判断是第一次获取，2,刷新，3 加载更多
    private List<RecommendBrandsBean> listBrands = new ArrayList<>();//复用的品牌集合
    private List<CheckedInformationBean> totalList = new ArrayList<>();//清单与主材的交互集合
    private List<CheckedInformationBean> totalListRaplace;//总集合替身
    private CheckedInformationBean checkedInformationBean;
    private RelativeLayout add_for_listing;
    private LinearLayout rl_btn;
    private LinearLayout layout_empty_recommend_view;//空品牌时，展现提示
    private LinearLayout ll_please_click_right;
    private TextView tv_empty_title;
    private List<BtnStatusBean> listTag;
    private List<BtnStatusBean> backListTag;//返回的标志位集合
    private BtnStatusBean[] btnStatusBeanList;//记录最后一次，该品类下的选中的二级品类信息，
    private String[] oneArr;
    private List<RecommendSCFDBean> mRecommendSCFDList;//清单中已经拥有的品类和品牌集合
    private StoreInformationBean storeInformationBean;//店铺信息
    private String currentCheckedOneCategoryId;
    private String currentOneCategoryName;//当前选中以及品类信息
    private String currentCheckedTwoCategoryId;
    private String currentSubCategoryName = "";//当前正被点击到的二级品类的名字
    private String currentStoreIdTotal;
    private WindowManager windowManager;
    private int currentDistance = 0;//当前item所在的位置
    private int currentClickItemLocation = 0;//当前点击的位置
    private String[] storeIdArr;


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //将总集合数据返回给清单页面
            case R.id.add_for_listing:

                //先删除总集合中空数据的bean
                deleteTotalEmptyDataItem();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("totalList", (Serializable) totalListRaplace);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
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
        two_level_category = (DynamicScrollView) findViewById(R.id.two_level_category);
        one_level_category = (HorizontalScrollView) findViewById(R.id.one_level_category);
        show_brand_listView = (PullListView) findViewById(R.id.show_brand_listView);
        remain_brand_count = (TextView) findViewById(R.id.remain_brand_count);
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pull_to_refresh_layout);
        add_for_listing = (RelativeLayout) findViewById(R.id.add_for_listing);
        rl_btn = (LinearLayout) findViewById(R.id.rl_btn);
        layout_empty_recommend_view = (LinearLayout) findViewById(R.id.empty_view);
        ll_please_click_right = (LinearLayout) findViewById(R.id.ll_please_click_right);
        ll_please_click_right.setVisibility(View.GONE);
        tv_empty_title = (TextView) findViewById(R.id.tv_empty_title);

        windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
    }

    @Override
    protected void initListener() {
        super.initListener();
//        all_material_btn.setOnClickListener(this);
        two_level_category.setMyScrollViewListener(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
        add_for_listing.setOnClickListener(this);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        Intent intent = getIntent();
        mRecommendSCFDList = (List<RecommendSCFDBean>) intent.getSerializableExtra(JsonConstants.RECOMMENDBRANDSCFDBEAN);
        setTitleForNavbar(intent.getStringExtra(JsonConstants.JSON_PROJECT_NAME));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        tv_empty_title.setText("没有可添加的品牌");
        add_for_listing.setBackgroundColor(UIUtils.getColor(R.color.gray));
        add_for_listing.setClickable(false);
        CustomProgress.showDefaultProgress(AddMaterialActivity.this);
        getCategoryInformation();
        getAdapterResultList = new ArrayList<>();
        getAdapterDataHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                CheckedInformationBean brandsBeanAndTag;
                switch (msg.what) {
                    //选中的品牌
                    //修改本次该品类选择的标志位
                    case 1:

                        brandsBeanAndTag = (CheckedInformationBean) msg.obj;
                        backListTag = brandsBeanAndTag.getList();
                        updataCategoryTag();
                        putCheckedBrandsAddList(brandsBeanAndTag.getRecommendBrandsBean());
                        isCanAddForLIst();//检验总集合中是否有选中数据

                        break;
                    //取消选中的品牌
                    //修改本次该品类选择的标志位
                    case 2:
                        brandsBeanAndTag = (CheckedInformationBean) msg.obj;
                        backListTag = brandsBeanAndTag.getList();
                        updataCategoryTag();
                        WipeCheckedBrandsAddList(brandsBeanAndTag.getRecommendBrandsBean());
                        isCanAddForLIst();//检验总集合中是否有选中数据
                        break;
                    default:

                        break;
                }

                showBrandRemainCount();
            }
        };
    }

    /**
     * 进入店铺选择
     */
    @Override
    protected void rightNavButtonClicked(View view) {
        super.rightNavButtonClicked(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMaterialActivity.this, StoreActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 获取店面回传回来的信息
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {

            case 101:
                Bundle bundle = data.getExtras();
                List<StoreInformationBean.StoreListBean> forResultStoreList = (List<StoreInformationBean.StoreListBean>) bundle.get("list");
                CustomProgress.showDefaultProgress(AddMaterialActivity.this);
                //将返回的数据再次筛选显示
                justRefreshOrLoadMore = 2;
                firstGetBrandsInformation(forResultStoreList);
                break;
            default:
                break;
        }
    }

    /**
     * 获取一级二级品类信息
     */
    public void getCategoryInformation() {

        MPServerHttpManager.getInstance().getMaterialCategoryInformation(new OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                CustomProgress.cancelDialog();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                materialCategoryBean = GsonUtil.jsonToBean(jsonObject.toString(), MaterialCategoryBean.class);
                //默认加入二级品类第一次进入集合
                listChecked = new ArrayList<>();//默认建立该品类下的品牌集合
                //将清单传来的数据整合到总集合，并增加默认集合
                setTotalListForListData();
                isCanAddForLIst();//检验总集合中是否有选中数据
                showOneCategory();
                //默认可选择品牌数量
                showBrandRemainCount();

            }
        });

    }

    /**
     * 根据品类获取品牌信息
     */
    public void getMaterialCategoryBrandsInformation(String category_3d_id, String category_3d_name, String sub_category_3d_id,
                                                     String sub_category_3d_name, String mall_number, Integer offset, Integer limit) {

        MPServerHttpManager.getInstance().getCategoryBrandsInformation(category_3d_id, category_3d_name, sub_category_3d_id, sub_category_3d_name,
                mall_number, offset, limit, new OKResponseCallback() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        CustomProgress.cancelDialog();
                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        RecommendSCFDBean getRecommendBrandsBean = GsonUtil.jsonToBean(jsonObject.toString(), RecommendSCFDBean.class);

                        //第一次初始化的获得的品牌
                        if (justRefreshOrLoadMore == 1) {
                            listBrands.clear();
                            if (getRecommendBrandsBean.getBrands() != null) {

                                addDatas(getRecommendBrandsBean.getBrands());
                            }
                            showBrandsList(listBrands);
                        }
                        //初始化后,再次获取数据新建,或复用已经拥有的数据
                        if (justRefreshOrLoadMore == 2) {
                            listBrands.clear();
                            if (getRecommendBrandsBean.getBrands() != null) {

                                addDatas(getRecommendBrandsBean.getBrands());
                                restartDatasForAdapter(null, getRecommendBrandsBean.getBrands());
                            } else {
                                restartDatasForAdapter(null, listBrands);
                            }
                        }
                        //加载更多时,展示数据
                        if (justRefreshOrLoadMore == 3) {
                            mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                            if (getRecommendBrandsBean.getBrands() != null) {

                                changeCategoryBrandsDatas(getRecommendBrandsBean.getBrands());
                            } else {
                            }
                        }
                        CustomProgress.cancelDialog();
                    }
                });
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

                storeInformationBean = GsonUtil.jsonToBean(jsonObject.toString(), StoreInformationBean.class);
                List<StoreInformationBean.StoreListBean> store_list = storeInformationBean.getStore_list();
                firstGetBrandsInformation(store_list);
            }
        });
    }

    /**
     * 第一次获取品牌信息
     */
    public void firstGetBrandsInformation(List<StoreInformationBean.StoreListBean> store_list) {

        String storeId;
        storeIdArr = new String[store_list.size()];
        for (int i = 0; i < store_list.size(); i++) {

            storeId = store_list.get(i).getMall_number();
            storeIdArr[i] = storeId;
            if (i == 0) {
                currentStoreIdTotal = storeId;
            } else {

                currentStoreIdTotal = currentStoreIdTotal + "," + storeId;
            }
        }
        //一二级品类信息展示之后在获取品牌信息
        getMaterialCategoryBrandsInformation(currentCheckedOneCategoryId, currentOneCategoryName, currentCheckedTwoCategoryId, currentSubCategoryName, currentStoreIdTotal, 0, 20);
    }

    /**
     * 展示一级品类信息
     */
    public void showOneCategory() {

        //多个二级自定义控件集
        dynamicAddViews = new DynamicAddView[materialCategoryBean.getCategories_3d().size()];
        //多个二级展示全部那控件集
        dynamicPopWindows = new DynamicPopWindow[materialCategoryBean.getCategories_3d().size()];
        showTwoCategory();
        oneArr = new String[materialCategoryBean.getCategories_3d().size()];
        //获取所有一级品类name
        for (int i = 0; i < materialCategoryBean.getCategories_3d().size(); i++) {
            oneArr[i] = materialCategoryBean.getCategories_3d().get(i).getCategory_3d_name();
        }
        currentOneCategoryName = oneArr[0];
        currentCheckedOneCategoryId = materialCategoryBean.getCategories_3d().get(0).getCategory_3d_id();
        btnStatusBeanList = new BtnStatusBean[oneArr.length];
        //默认需要添加的第一个未点击的二级品类保存
        BtnStatusBean btnStatusBeanDefault;
        //默认每个二级品类选中第一个
        for (int i = 0; i < oneArr.length; i++) {

            btnStatusBeanDefault = new BtnStatusBean();
            btnStatusBeanDefault.setCountOffset(0);
            btnStatusBeanDefault.setSingleClickOrDoubleBtnCount(1);
            btnStatusBeanList[i] = btnStatusBeanDefault;
        }
        dynamicAddViewContainer = new DynamicAddViewContainer(this, one_level_category.getHeight());
        dynamicAddViewContainer.setListener(new DynamicAddViewContainer.OnButtonClickedListener() {
            @Override
            public void onButtonClicked(BtnStatusBean btnStatusBean) {

                two_level_category.removeAllViews();
                two_level_category.addView(dynamicAddViews[btnStatusBean.getCountOffset()]);
                if (dynamicPopWindowReuse.isShowing()) {

                    dynamicPopWindowReuse.showPopupWindow(all_material_btn);
                }
                dynamicPopWindowReuse = dynamicPopWindows[btnStatusBean.getCountOffset()];
                //当前一级主材信息
                currentOneCategoryName = materialCategoryBean.getCategories_3d().get(btnStatusBean.getCountOffset()).getCategory_3d_name();
                currentCheckedOneCategoryId = materialCategoryBean.getCategories_3d().get(btnStatusBean.getCountOffset()).getCategory_3d_id();
                countArrItem = btnStatusBean.getCountOffset();//不同的一级品类
                //选中一级品类时,将之前选择的一级品类下的二级品类信息展示出来
                for (int i = 0; i < oneArr.length; i++) {

                    if (currentOneCategoryName.equals(oneArr[i])) {

                        if (btnStatusBeanList[i] != null) {

                            adapterCategoryAll(btnStatusBeanList[i]);
                            dynamicPopWindows[countArrItem].setButtonCheckedStatus(btnStatusBeanList[i]);
                            dynamicAddViews[countArrItem].setButtonCheckedStatus(btnStatusBeanList[i]);

                        }
                    }
                }
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
     */
    public void showTwoCategory() {
        DynamicAddView dynamicAddView;
        for (int i = 0; i < materialCategoryBean.getCategories_3d().size(); i++) {

            dynamicAddView = new DynamicAddView(this);
            dynamicAddViews[i] = dynamicAddView;
            dynamicAddView.dynamicAddData(materialCategoryBean.getCategories_3d().get(i).getSub_category());
            dynamicAddView.setListener(new DynamicAddView.OnButtonClickedListener() {

                @Override
                public void onButtonClicked(BtnStatusBean btnStatusBean) {
                    CustomProgress.showDefaultProgress(AddMaterialActivity.this);
                    if (dynamicPopWindows[countArrItem] != null) {
                        dynamicPopWindows[countArrItem].setButtonCheckedStatus(btnStatusBean);
                    }
                    //新建品类集合
                    adapterCategoryAll(btnStatusBean);
                    for (int i = 0; i < oneArr.length; i++) {

                        if (currentOneCategoryName.equals(oneArr[i])) {
                            btnStatusBeanList[i] = btnStatusBean;
                        }
                    }
                }

                @Override
                public void onGetCurrentClickLocation(int x, int y, int xForRight, int width, BtnStatusBean btnStatusBean) {
                    currentClickItemLocation = x;
                    int totalCount = dynamicAddViews[countArrItem].getItemCount();
                    two_level_category.useCurrentDistanceScroll(x, currentDistance, two_level_category.getHeight(), two_level_category.getChildAt(0).getMeasuredWidth(), totalCount, btnStatusBean.getCountOffset());
                }
            });

            showAllTwoCategory(materialCategoryBean.getCategories_3d().get(i).getSub_category(), i);
        }
        two_level_category.addView(dynamicAddViews[0]);
        int height = this.getWindowManager().getDefaultDisplay().getHeight();
        two_level_category.setAdapterForItem(height);
        dynamicPopWindowReuse = dynamicPopWindows[0];
        //一二级品类信息展示之后在获取,店铺,品牌信息
        getStore();
        addAllWindowBtn();
    }

    /**
     * 展示二级品类全部
     */
    public void showAllTwoCategory(List<MaterialCategoryBean.Categories3dBean.SubCategoryBean> list, final int count) {
        DynamicPopWindow dynamicPopwindow;
        dynamicPopwindow = new DynamicPopWindow(this, null, R.style.Transparent_Dialog);
        dynamicPopWindows[count] = dynamicPopwindow;
        dynamicPopwindow.addDataForView(list);
        dynamicPopwindow.setListener(new DynamicPopWindow.onButtonPopWindowClickedListener() {
            @Override
            public void onButtonOnClick(BtnStatusBean btnStatusBean) {
                dynamicAddViews[countArrItem].setButtonCheckedStatus(btnStatusBean);
                int[] viewLocation = dynamicAddViews[countArrItem].getLocationNumber(btnStatusBean.getCountOffset());
                int length = dynamicAddViews[countArrItem].getItemCount();
                two_level_category.useCurrentDistanceScroll(viewLocation[0], currentDistance, two_level_category.getHeight(), two_level_category.getChildAt(0).getMeasuredWidth(), length, btnStatusBean.getCountOffset());
                //复用
                adapterCategoryAll(btnStatusBean);
                for (int i = 0; i < oneArr.length; i++) {

                    if (currentOneCategoryName.equals(oneArr[i])) {

                        btnStatusBeanList[i] = btnStatusBean;
                    }
                }
            }
        });
    }

    /**
     * 展示品牌数据
     */
    public void showBrandsList(List<RecommendBrandsBean> list) {
        //增加已经存在的该品类下的集合,并加入到大集合
        List<RecommendBrandsBean> haveDCheckedBrandsList = new ArrayList<RecommendBrandsBean>();
        for (int i = 0; i < listBrands.size(); i++) {

            haveDCheckedBrandsList.add(listBrands.get(i));
        }
        /**
         *增加本次数据的标志位，
         * 该品类的标志位
         */
        BtnStatusBean btnStatusBean;
        List<BtnStatusBean> listTagFor = null;
        for (int i = 0; i < totalList.size(); i++) {
            String categoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
            if (categoryName.equals(currentSubCategoryName)) {
                totalList.get(i).setHavedBrandsInformationBean(haveDCheckedBrandsList);
                listTagFor = totalList.get(i).getList();//获取总集合中该品类的Tag集合
                listTagFor.clear();
                for (int j = 0; j < list.size(); j++) {

                    btnStatusBean = new BtnStatusBean();
                    btnStatusBean.setCountOffset(j);
                    btnStatusBean.setSingleClickOrDoubleBtnCount(2);
                    listTagFor.add(j, btnStatusBean);
                }
            }
        }
        upDataTotalListTag(list);
        if (addBrandShowAdapter == null) {
            addBrandShowAdapter = new AddBrandShowAdapter(AddMaterialActivity.this, list, R.layout.add_brand_item, getAdapterDataHandler, listTagFor);
            show_brand_listView.setAdapter(addBrandShowAdapter);
            show_brand_listView.setCanRefresh(false);
        }
        //背景显示
        setEmptyBg(list);
    }

    /**
     * 复用已经点击其他的二级品类，进行重新加载以及刷新数据
     */
    public void restartDatasForAdapter(List<BtnStatusBean> list, List<RecommendBrandsBean> datas) {

        if (list == null) {
            /**
             *增加本次数据的标志位，
             * 该品类的标志位
             */
            BtnStatusBean btnStatusBean;
            for (int i = 0; i < totalList.size(); i++) {
                String categoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
                if (categoryName.equals(currentSubCategoryName)) {
                    totalList.get(i).setHavedBrandsInformationBean(datas);
                    List<BtnStatusBean> listTag = totalList.get(i).getList();//获取总集合中该品类的Tag集合
                    listTag.clear();
                    for (int j = 0; j < datas.size(); j++) {

                        btnStatusBean = new BtnStatusBean();
                        btnStatusBean.setCountOffset(j);
                        btnStatusBean.setSingleClickOrDoubleBtnCount(2);
                        listTag.add(j, btnStatusBean);
                    }
                    upDataTotalListTag(datas);
                    list = listTag;
                    showBrandRemainCount();
                }
            }
            if (datas != null) {

                setEmptyBg(datas);
                addBrandShowAdapter.changeListTag(list, datas);
            }
        } else {
            setEmptyBg(datas);
            addBrandShowAdapter.changeListTag(list, datas);
        }
        addBrandShowAdapter.notifyDataSetChanged();
    }

    /**
     * 复用二级品类点击以及二级品类点击全部的监听
     */
    public void adapterCategoryAll(BtnStatusBean btnStatusBean) {

        checkedInformationBean = new CheckedInformationBean();
        /**
         * 获取二级品类bean加入到总bean
         * */
        MaterialCategoryBean.Categories3dBean.SubCategoryBean subCategoryBean =
                materialCategoryBean.getCategories_3d().get(countArrItem).getSub_category().get(btnStatusBean.getCountOffset());
        List<RecommendBrandsBean> checkedBrandsInformationBean = new ArrayList<RecommendBrandsBean>();//新建所需要的品牌集合
        currentSubCategoryName = subCategoryBean.getSub_category_3d_name();
        currentCheckedTwoCategoryId = subCategoryBean.getSub_category_3d_id();
        List<BtnStatusBean> listFirstTag = new ArrayList<>();//增加标志位集合
        //增加已经存在的该品类下的集合
        List<RecommendBrandsBean> haveDCheckedBrandsList = new ArrayList<RecommendBrandsBean>();
        checkedInformationBean.setHavedBrandsInformationBean(haveDCheckedBrandsList);
        checkedInformationBean.setList(listFirstTag);
        checkedInformationBean.setSubCategoryBean(subCategoryBean);
        checkedInformationBean.setCheckedBrandsInformationBean(checkedBrandsInformationBean);
        boolean isAddToList = false;//判断是否加入总集合
        boolean isHavedBrandsList = false;//是否已经存在品牌集合
        for (int i = 0; i < totalList.size(); i++) {
            String subCategoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
            if (subCategoryName.equals(currentSubCategoryName)) {
                isAddToList = true;
                if (totalList.get(i).getHavedBrandsInformationBean().size() > 0) {
                    isHavedBrandsList = true;
                    haveDCheckedBrandsList = totalList.get(i).getHavedBrandsInformationBean();
                    listFirstTag = totalList.get(i).getList();//集合中的标志位信息
                } else {
                    isHavedBrandsList = false;
                }
                break;
            } else {

                isAddToList = false;
            }
        }
        //有相同的便不再加入集合
        if (!isAddToList) {
            totalList.add(checkedInformationBean);
        }

        if (totalList.size() == 0) {

            totalList.add(checkedInformationBean);
        }

        justRefreshOrLoadMore = 2;//切换品类是会走初次加载过程
        //判断该品类下是否已经存在数据，如果存在复用，如果不存在，调取接口加载更多
        if (isHavedBrandsList) {
            listBrands.clear();//清空之前的数据
            //将集合中的数据填入需要的集合，
            //根据当前的店铺id筛选已经有的品牌
            getStoreBrandsList(haveDCheckedBrandsList);
            restartDatasForAdapter(listFirstTag, listBrands);
            addBrandShowAdapter.notifyDataSetChanged();
        } else {
            //从未获取过该品类的品牌信息
            getMaterialCategoryBrandsInformation(currentCheckedOneCategoryId, currentOneCategoryName, currentCheckedTwoCategoryId, currentSubCategoryName, currentStoreIdTotal, 0, 20);
        }
        showBrandRemainCount();
    }

    /**
     * 改变不同品类下的数据
     */
    public void changeCategoryBrandsDatas(List<RecommendBrandsBean> list) {

        if (justRefreshOrLoadMore == 3) {
            //找到所需要添加入的标志位集合
            BtnStatusBean btnStatusBean;
            List<RecommendBrandsBean> listHaned = null;
            List<BtnStatusBean> listTag = null;
            int lengthCount = 0;//已经存在的标志位的集合size
            for (int i = 0; i < totalList.size(); i++) {
                String categoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
                if (categoryName.equals(currentSubCategoryName)) {

                    listHaned = totalList.get(i).getHavedBrandsInformationBean();
                    lengthCount = listHaned.size();
                    listTag = totalList.get(i).getList();//获取总集合中该品类的Tag集合
                    for (int j = 0; j < list.size(); j++) {

                        btnStatusBean = new BtnStatusBean();
                        btnStatusBean.setCountOffset(j + lengthCount);
                        btnStatusBean.setSingleClickOrDoubleBtnCount(2);
                        listTag.add(btnStatusBean);
                        listHaned.add(list.get(i));
                    }
                }
            }
            addDatas(list);
            addBrandShowAdapter.changeListTag(listTag, listHaned);
        }
        addBrandShowAdapter.notifyDataSetChanged();

    }

    /**
     * 展示剩余品牌添加数
     */
    public void showBrandRemainCount() {

        //当前还可以选择的品牌数量
        int canChooseBrandsCount = 6;
        //获取当前选中的品类的品牌集合中选中了多少
        for (int i = 0; i < totalList.size(); i++) {
            String categoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
            if (categoryName.equals(currentSubCategoryName)) {
                //获取品牌集合的标志位
                List<BtnStatusBean> listTag = totalList.get(i).getList();//获取总集合中该品类的Tag集合
                BtnStatusBean btnStatusBean;
                for (int j = 0; j < listTag.size(); j++) {
                    btnStatusBean = listTag.get(j);
                    if (btnStatusBean.getSingleClickOrDoubleBtnCount() == 1) {

                        canChooseBrandsCount = canChooseBrandsCount - 1;
                    }
                }
            }
        }
        String remainBrandCount = UIUtils.getString(R.string.add) + canChooseBrandsCount + UIUtils.getString(R.string.brand);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(remainBrandCount);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.BLUE), 4, 5, spannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);
        remain_brand_count.setText(spannableStringBuilder);
    }

    /**
     * 将数据添加进入集合复用
     */
    public void addDatas(List<RecommendBrandsBean> list) {

        for (int i = 0; i < list.size(); i++) {

            listBrands.add(i, list.get(i));
        }
    }

    /**
     * 将选择的品牌数据添加如集合
     */
    public void putCheckedBrandsAddList(RecommendBrandsBean brandsBean) {

        for (int i = 0; i < totalList.size(); i++) {
            if (totalList.get(i).getSubCategoryBean() != null && totalList.get(i).getSubCategoryBean().getSub_category_3d_name() != null) {


                String subCategoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();

                if (subCategoryName.equals(currentSubCategoryName)) {
                    List<RecommendBrandsBean> checkedBrandsInformationBean = totalList.get(i).getCheckedBrandsInformationBean();
                    checkedBrandsInformationBean.add(brandsBean);

                }
            }
        }
    }

    /**
     * 将已经选择的品牌删除出集合
     */
    public void WipeCheckedBrandsAddList(RecommendBrandsBean listUnChecked) {

        for (int i = 0; i < totalList.size(); i++) {
            String subCategoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
            if (subCategoryName.equals(currentSubCategoryName)) {

                for (int k = 0; k < totalList.get(i).getCheckedBrandsInformationBean().size(); k++) {

                    String subBrandsName = totalList.get(i).getCheckedBrandsInformationBean().get(k).getName();
                    String listUnCheckedName = listUnChecked.getName();
                    if (subBrandsName.equals(listUnCheckedName)) {

                        totalList.get(i).getCheckedBrandsInformationBean().remove(k);
                    }
                }
            }
        }
    }

    /**
     * 删除清单中，品牌为空的元素
     * 暂时先不用
     */
    public void deleteTotalEmptyDataItem() {

        totalListRaplace = new ArrayList<>();//总集合替身
        for (int i = 0; i < totalList.size(); i++) {
            int sizeTotal = totalList.get(i).getCheckedBrandsInformationBean().size();
            if (sizeTotal != 0) {

                totalListRaplace.add(totalList.get(i));
            }
        }
    }

    /**
     * 修改该品类中选中的标志位，或取消的标志位
     */
    public void updataCategoryTag() {

        for (int i = 0; i < totalList.size(); i++) {

            String catagoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
            if (catagoryName.equals(currentSubCategoryName)) {

                totalList.get(i).setList(backListTag);
            }
        }
    }

    /**
     * 根据清单传过来的数据来修改总集合中的标志位
     */
    public void upDataTotalListTag(List<RecommendBrandsBean> datas) {

        for (int i = 0; i < totalList.size(); i++) {
            String categoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
            for (int j = 0; j < mRecommendSCFDList.size(); j++) {

                String categoryForList = mRecommendSCFDList.get(j).getSub_category_3d_name();
                if (categoryForList.equals(categoryName) && categoryName.equals(currentSubCategoryName)) {
                    //取出来该品类的tag来做修改
                    List<BtnStatusBean> listTagForTotal = totalList.get(i).getList();
                    List<RecommendBrandsBean> listInformationSendList = mRecommendSCFDList.get(j).getBrands();
                    List<RecommendBrandsBean> havedBrandsInformationBeanList = totalList.get(i).getHavedBrandsInformationBean();
                    String brandsName, sendBrandsName;
                    BtnStatusBean btnStatusBean;

                    for (int k = 0; k < datas.size(); k++) {
                        brandsName = havedBrandsInformationBeanList.get(k).getName();
                        btnStatusBean = listTagForTotal.get(k);
                        for (int h = 0; h < listInformationSendList.size(); h++) {
                            sendBrandsName = listInformationSendList.get(h).getName();
                            if (brandsName.equals(sendBrandsName)) {
                                btnStatusBean.setSingleClickOrDoubleBtnCount(1);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 将清单中所有的数据品牌整合进入集合
     */
    public void setTotalListForListData() {

        for (int i = 0; i < mRecommendSCFDList.size(); i++) {
            checkedInformationBean = new CheckedInformationBean();
            MaterialCategoryBean.Categories3dBean.SubCategoryBean subCategoryBean = new MaterialCategoryBean.Categories3dBean.SubCategoryBean();
            List<RecommendBrandsBean> recommendBrandsBeenList = new ArrayList<RecommendBrandsBean>();
            List<BtnStatusBean> listFirstTag = new ArrayList<>();//增加标志位集合
            //增加已经存在的该品类下的集合
            List<RecommendBrandsBean> haveDCheckedBrandsList = new ArrayList<RecommendBrandsBean>();
            String categoryForListName = mRecommendSCFDList.get(i).getSub_category_3d_name();
            String categoryId = mRecommendSCFDList.get(i).getSub_category_3d_id();
            subCategoryBean.setSub_category_3d_name(categoryForListName);
            subCategoryBean.setSub_category_3d_id(categoryId);
            for (int j = 0; j < mRecommendSCFDList.get(i).getBrands().size(); j++) {

                recommendBrandsBeenList.add(mRecommendSCFDList.get(i).getBrands().get(j));
            }
            checkedInformationBean.setHavedBrandsInformationBean(haveDCheckedBrandsList);
            checkedInformationBean.setSubCategoryBean(subCategoryBean);
            checkedInformationBean.setCheckedBrandsInformationBean(recommendBrandsBeenList);
            checkedInformationBean.setList(listFirstTag);
            totalList.add(checkedInformationBean);
        }
        boolean isHaveNumberOne = false;
        for (int i = 0; i < totalList.size(); i++) {

            String categoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
            String categoryNameFirst = materialCategoryBean.getCategories_3d().get(countArrItem).getSub_category().get(0).getSub_category_3d_name();
            if (categoryName.equals(categoryNameFirst)) {
                isHaveNumberOne = true;
                break;
            } else {
                isHaveNumberOne = false;
            }
        }
        //如果集合中没有背景墙则加入集合中
        if (!isHaveNumberOne) {
            checkedInformationBean = new CheckedInformationBean();
            MaterialCategoryBean.Categories3dBean.SubCategoryBean subCategoryBean = materialCategoryBean.getCategories_3d().get(countArrItem).getSub_category().get(0);
            List<RecommendBrandsBean> recommendBrandsBeenList = new ArrayList<RecommendBrandsBean>();
            List<BtnStatusBean> listFirstTag = new ArrayList<>();//增加标志位集合
            //增加已经存在的该品类下的集合
            List<RecommendBrandsBean> haveDCheckedBrandsList = new ArrayList<RecommendBrandsBean>();
            checkedInformationBean.setHavedBrandsInformationBean(haveDCheckedBrandsList);
            checkedInformationBean.setSubCategoryBean(subCategoryBean);
            checkedInformationBean.setCheckedBrandsInformationBean(recommendBrandsBeenList);
            checkedInformationBean.setList(listFirstTag);
            totalList.add(checkedInformationBean);
        }
        currentSubCategoryName = materialCategoryBean.getCategories_3d().get(countArrItem).getSub_category().get(0).getSub_category_3d_name();
    }

    /**
     * 加入全部按钮
     */
    public void addAllWindowBtn() {

        int width = this.getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams layoutParamsButton = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsButton.topMargin = width / 50;
        layoutParamsButton.bottomMargin = width / 50;
        all_material_btn = new TextView(this);
        all_material_btn.setText(UIUtils.getString(R.string.my_bid_all));
        all_material_btn.setMinWidth(width / 5);
        all_material_btn.setGravity(Gravity.CENTER);
        all_material_btn.setPadding(width / 25, 0, width / 25, 0);
        all_material_btn.setTextColor(UIUtils.getColor(R.color.text_item_name));
        all_material_btn.setBackgroundResource(R.drawable.material_add_bg);
        all_material_btn.setLayoutParams(layoutParamsButton);
        all_material_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!SingleClickUtils.isFastDoubleClickMoreShort()) {

                    if (dynamicPopWindowReuse != null) {

                        dynamicPopWindowReuse.showPopupWindow(all_material_btn);
                    }
                }
            }
        });
        rl_btn.addView(all_material_btn);
        rl_btn.invalidate();

    }

    /**
     * 筛选当前店铺选择品牌，
     */
    public void getStoreBrandsList(List<RecommendBrandsBean> haveDCheckedBrandsList) {

        for (int i = 0; i < haveDCheckedBrandsList.size(); i++) {

            List<RecommendMallsBean> malls = haveDCheckedBrandsList.get(i).getMalls();
            String storeId;
            boolean isAdd = false;
            for (int j = 0; j < malls.size(); j++) {

                storeId = malls.get(j).getMall_number();
                for (int h = 0; h < storeIdArr.length; h++) {

                    if (storeId.equals(storeIdArr[h])) {

                        isAdd = true;
                        listBrands.add(haveDCheckedBrandsList.get(i));
                        break;
                    } else {
                        isAdd = false;
                    }
                }
                if (isAdd) {
                    break;
                }

            }
        }
    }

    /**
     * 检验是否可添加至清单
     */
    public void isCanAddForLIst() {
        boolean isCanShow = false;
        for (int i = 0; i < totalList.size(); i++) {
            List<RecommendBrandsBean> recommendBrandsBeenList = totalList.get(i).getCheckedBrandsInformationBean();
            if (recommendBrandsBeenList.size() > 0) {
                isCanShow = true;
                break;
            } else {
                isCanShow = false;
            }
        }
        if (isCanShow) {

            add_for_listing.setBackgroundColor(UIUtils.getColor(R.color.my_project_title_pointer_color));
            add_for_listing.setClickable(true);
        } else {
            add_for_listing.setBackgroundColor(UIUtils.getColor(R.color.gray));
            add_for_listing.setClickable(false);
        }
    }

    /**
     * 没有品牌时,显示空白页面
     */
    public void setEmptyBg(List<RecommendBrandsBean> list) {

        if (list != null && list.size() > 0) {

            layout_empty_recommend_view.setVisibility(View.GONE);
        } else {
            layout_empty_recommend_view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 刷新,加载
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        justRefreshOrLoadMore = 2;
        getMaterialCategoryBrandsInformation("", "", "", "", "", 0, 20);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        justRefreshOrLoadMore = 3;
        CustomProgress.showDefaultProgress(AddMaterialActivity.this);
        getMaterialCategoryBrandsInformation("", "", "", "", "", 0, 20);
    }

    @Override
    public void onScrollChange(DynamicScrollView scrollView, int x, int y, int oldx, int oldy) {

        currentDistance = x;

    }
}
