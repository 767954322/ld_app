package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.adapter.AddBrandShowAdapter;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.CheckedInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendBrandsBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendSCFDBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.BtnStatusBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.MaterialCategoryBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.StoreInformationBean;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddViewContainer;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicAddView;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.DynamicPopWindow;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest.OKResponseCallback;
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

public class AddMaterialActivity extends NavigationBarActivity implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {


    private DynamicAddView[] dynamicAddViews;//二级控件集合
    private DynamicPopWindow[] dynamicPopWindows;//二级品类全窗口集合
    private DynamicAddViewContainer dymicAddViewContainer;

    private DynamicPopWindow dynamicPopWindowReuse;//多窗口复用
    private int countArrItem = 0;//对应不同二级品类和窗口
    private HorizontalScrollView one_level_category;
    private HorizontalScrollView two_level_category;
    private PullListView show_brand_listView;
    private MaterialCategoryBean materialCategoryBean;
    private Button all_material_btn;
    private Handler getAdapterDataHandler;//获取返回数据handler
    private List<RecommendBrandsBean> getAdapterResultList;
    private DynamicAddViewContainer dynamicAddViewContainer;
    private List<RecommendBrandsBean> listChecked;
    ;//选中品牌的集合
    private int brandCount = 0;//可添加品牌的数量
    private TextView remain_brand_count;
    private PullToRefreshLayout mPullToRefreshLayout;
    private AddBrandShowAdapter addBrandShowAdapter;
    private int justRefreshOrLoadMore = 1;//判断是第一次获取，2,刷新，3 加载更多
    private List<RecommendBrandsBean> listBrands = new ArrayList<>();//复用的品牌集合
    private List<CheckedInformationBean> totalList = new ArrayList<>();//清单与主材的交互集合
    private CheckedInformationBean checkedInformationBean;
    private String currentSubCategoryName = "";//当前正被点击到的二级品类的名字
    private LinearLayout add_for_listing;
    private List<BtnStatusBean> listTag;
    private List<BtnStatusBean> backListTag;//返回的标志位集合
    private BtnStatusBean[] btnStatusBeanList;//记录最后一次，该品类下的选中的二级品类信息，
    private String currentOneCategoryName;//当前选中以及品类信息
    private String[] oneArr;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//          展示二级品类全部信息
            case R.id.all_material_btn:

                if (dynamicPopWindowReuse != null) {

                    dynamicPopWindowReuse.showPopupWindow(all_material_btn);
                }
                break;
            //将总集合数据返回给清单页面
            case R.id.add_for_listing:

                //先删除总集合中空数据的bean
                deleteTotalEmptyDataItem();
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("totalList", (Serializable) totalList);
                intent.putExtras(bundle);
                setResult(102, intent);
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
        two_level_category = (HorizontalScrollView) findViewById(R.id.two_level_category);
        one_level_category = (HorizontalScrollView) findViewById(R.id.one_level_category);
        show_brand_listView = (PullListView) findViewById(R.id.show_brand_listView);
        all_material_btn = (Button) findViewById(R.id.all_material_btn);
        remain_brand_count = (TextView) findViewById(R.id.remain_brand_count);
        mPullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.pull_to_refresh_layout);
        add_for_listing = (LinearLayout) findViewById(R.id.add_for_listing);
    }

    @Override
    protected void initListener() {
        super.initListener();
        all_material_btn.setOnClickListener(this);
        mPullToRefreshLayout.setOnRefreshListener(this);
        add_for_listing.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

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
                        ToastUtil.showCustomToast(AddMaterialActivity.this, "" + brandsBeanAndTag.getRecommendBrandsBean().getBrand_name());
                        putCheckedBrandsAddList(brandsBeanAndTag.getRecommendBrandsBean());

                        break;
                    //取消选中的品牌
                    //修改本次该品类选择的标志位
                    case 2:
                        brandsBeanAndTag = (CheckedInformationBean) msg.obj;
                        backListTag = brandsBeanAndTag.getList();
                        updataCategoryTag();
                        WipeCheckedBrandsAddList(brandsBeanAndTag.getRecommendBrandsBean());
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

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                materialCategoryBean = GsonUtil.jsonToBean(jsonObject.toString(), MaterialCategoryBean.class);
                showOneCategory();
                //默认加入二级品类第一次进入集合
                listChecked = new ArrayList<>();//默认建立该品类下的品牌集合
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
                currentSubCategoryName = checkedInformationBean.getSubCategoryBean().getSub_category_3d_name();

                //默认可选择品牌数量
                showBrandRemainCount();

                ToastUtil.showCustomToast(AddMaterialActivity.this, "" + checkedInformationBean.getSubCategoryBean().getSub_category_3d_name());
            }
        });

    }

    /**
     * 根据品类获取品牌信息
     */
    public void getMaterialCategoryBrandsInformation(String category_3d_id, String category_3d_name, String sub_category_3d_id,
                                                     String sub_category_3d_name, String mall_number, String decoration_company_number,
                                                     Integer offset, Integer limit) {

        MPServerHttpManager.getInstance().getCategoryBrandsInformation(category_3d_id, category_3d_name, sub_category_3d_id, sub_category_3d_name,
                mall_number, decoration_company_number, offset, limit, new OKResponseCallback() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }

                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        RecommendSCFDBean getRecommendBrandsBean = GsonUtil.jsonToBean(jsonObject.toString(), RecommendSCFDBean.class);

                        if (justRefreshOrLoadMore == 1) {
                            listBrands.clear();
                            addDatas(getRecommendBrandsBean.getBrands());
                            showBrandsList(listBrands);
                        }
                        if (justRefreshOrLoadMore == 2) {
                            listBrands.clear();
                            addDatas(getRecommendBrandsBean.getBrands());
                            restartDatasForAdapter(null, getRecommendBrandsBean.getBrands());
                        }
                        if (justRefreshOrLoadMore == 3) {
                            mPullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                            changeCategoryBrandsDatas(getRecommendBrandsBean.getBrands());
                        }

                    }
                });
    }

    /**
     * 展示一级品类信息
     */
    public void showOneCategory() {

        dynamicAddViews = new DynamicAddView[materialCategoryBean.getCategories_3d().size()];
        dynamicPopWindows = new DynamicPopWindow[materialCategoryBean.getCategories_3d().size()];
        showTwoCategory();
        oneArr = new String[materialCategoryBean.getCategories_3d().size()];
        //获取所有一级品类name
        for (int i = 0; i < materialCategoryBean.getCategories_3d().size(); i++) {
            oneArr[i] = materialCategoryBean.getCategories_3d().get(i).getCategory_3d_name();
        }
        currentOneCategoryName = oneArr[0];
        btnStatusBeanList = new BtnStatusBean[oneArr.length];
        //默认需要添加的第一个未点击的二级品类保存
        BtnStatusBean btnStatusBeanDefault = new BtnStatusBean();
        btnStatusBeanDefault.setCountOffset(0);
        btnStatusBeanDefault.setSingleClickOrDoubleBtnCount(1);
        btnStatusBeanList[0] = btnStatusBeanDefault;
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
                countArrItem = btnStatusBean.getCountOffset();//不同的一级品类
                //选中一级品类时,将之前选择的一级品类下的二级品类信息展示出来
                for (int i = 0; i < oneArr.length; i++) {

                    if (currentOneCategoryName.equals(oneArr[i])) {

                        if (btnStatusBeanList[i] != null) {

                            adapterCategoryAll(btnStatusBeanList[i]);
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
            });

            showAllTwoCategory(materialCategoryBean.getCategories_3d().get(i).getSub_category(), i);
        }
        two_level_category.addView(dynamicAddViews[0]);
        dynamicPopWindowReuse = dynamicPopWindows[0];
        //一二级品类信息展示之后在获取品牌信息
        getMaterialCategoryBrandsInformation("", "", "", "", "", "", 0, 20);
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
        for (int i = 0; i < totalList.size(); i++) {
            String categoryName = totalList.get(i).getSubCategoryBean().getSub_category_3d_name();
            if (categoryName.equals(currentSubCategoryName)) {
                totalList.get(i).setHavedBrandsInformationBean(haveDCheckedBrandsList);
                listTag = totalList.get(i).getList();//获取总集合中该品类的Tag集合
                listTag.clear();
                for (int j = 0; j < list.size(); j++) {

                    btnStatusBean = new BtnStatusBean();
                    btnStatusBean.setCountOffset(j);
                    btnStatusBean.setSingleClickOrDoubleBtnCount(2);
                    listTag.add(j, btnStatusBean);
                }
            }
        }

        if (addBrandShowAdapter == null) {

            addBrandShowAdapter = new AddBrandShowAdapter(AddMaterialActivity.this, list, R.layout.add_brand_item, getAdapterDataHandler, listTag);
            show_brand_listView.setAdapter(addBrandShowAdapter);
            show_brand_listView.setCanRefresh(false);
        }
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
            int a = datas.size();
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
                    list = listTag;
                }
            }
            addBrandShowAdapter.changeListTag(list, datas);
        } else {
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
            for (int i = 0; i < haveDCheckedBrandsList.size(); i++) {

                listBrands.add(haveDCheckedBrandsList.get(i));
            }
            restartDatasForAdapter(listFirstTag, listBrands);
            addBrandShowAdapter.notifyDataSetChanged();
            ToastUtil.showCustomToast(AddMaterialActivity.this, "fuyong");
        } else {

            //从未获取过该品类的品牌信息
            getMaterialCategoryBrandsInformation("", "", "", "", "", "", 0, 20);
            ToastUtil.showCustomToast(AddMaterialActivity.this, "xin");
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
                    ToastUtil.showCustomToast(AddMaterialActivity.this, list.size() + "---------" + listTag.size());
                }
            }
            addDatas(list);
            addBrandShowAdapter.changeListTag(listTag, listHaned);
        }
        addBrandShowAdapter.notifyDataSetChanged();

    }

    /**
     * 获取大集合中已经存在的数据并展示
     */

    public void showHavedDatasForList() {


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
        String remainBrandCount = "还可添加" + canChooseBrandsCount + "个品牌";
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

                    ToastUtil.showCustomToast(AddMaterialActivity.this, "" + checkedBrandsInformationBean.size());
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

                    String subBrandsName = totalList.get(i).getCheckedBrandsInformationBean().get(k).getBrand_name();

                    String listUnCheckedName = listUnChecked.getBrand_name();
                    if (subBrandsName.equals(listUnCheckedName)) {

                        totalList.get(i).getCheckedBrandsInformationBean().remove(k);
                        ToastUtil.showCustomToast(AddMaterialActivity.this, "" + totalList.get(i).getCheckedBrandsInformationBean().size());
                    }
                }
            }
        }
    }

    /**
     * 删除清单中，品牌为空的元素
     */
    public void deleteTotalEmptyDataItem() {

        for (int i = 0; i < totalList.size(); i++) {
            int sizeTotal = totalList.get(i).getCheckedBrandsInformationBean().size();
            if (sizeTotal == 0) {

                totalList.remove(i);
            }
        }
        ToastUtil.showCustomToast(AddMaterialActivity.this, "" + totalList.size());
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
     * 刷新,加载
     */
    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

        justRefreshOrLoadMore = 2;
        getMaterialCategoryBrandsInformation("", "", "", "", "", "", 0, 20);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        justRefreshOrLoadMore = 3;
        getMaterialCategoryBrandsInformation("", "", "", "", "", "", 0, 20);
    }
}
