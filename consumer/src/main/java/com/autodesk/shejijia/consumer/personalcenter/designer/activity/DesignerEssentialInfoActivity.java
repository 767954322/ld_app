package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.InfoModifyEntity;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerDesignCostBean;
import com.autodesk.shejijia.consumer.personalcenter.designer.entity.DesignerInfoDetails;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.uielements.ActionSheetDialog;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.consumer.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.PictureProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-6
 * @file DesignerEssentialInfoActivity.java  .
 * @brief 设计师个人中心修改个人信息界面 .
 */
public class DesignerEssentialInfoActivity extends NavigationBarActivity implements View.OnClickListener {
    private Bitmap headPicBitmap;

    private Uri uritempFile;
    private static final int CROP_SMALL_PICTURE = 5;//截图
    private static final int CROP_SMALL_PICTURE_1 = 6;//截图

    @Override
    protected int getLayoutResId() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置竖屏
        return R.layout.activity_designer_info;
    }

    @Override
    protected void initView() {
        super.initView();
        // 注册EventBus
        EventBus.getDefault().register(this);
        reEssentialMeasureHouse = (RelativeLayout) findViewById(R.id.rl_essential_info_measure_house);
        rlEssentialProjectCost = (RelativeLayout) findViewById(R.id.rl_essential_info_design_cost);
        rlEssentialSex = (RelativeLayout) findViewById(R.id.rl_essential_info_sex);
        rlNickName = (RelativeLayout) findViewById(R.id.rl_designer_essential_info_nick_name);
        tvNickName = (TextView) findViewById(R.id.tv_designer_essential_info_nickname);
        tvMeasureHouse = (TextView) findViewById(R.id.tv_designer_essential_info_measure_cost);
        tvUserName = (TextView) findViewById(R.id.tv_designer_essential_info_user_name);
        rlLocation = (RelativeLayout) findViewById(R.id.rl_designer_essential_info_location);
        tvLocation = (TextView) findViewById(R.id.tv_designer_essential_info_location);
        piv_essential_photo = (PolygonImageView) findViewById(R.id.piv_essential_photo);
        tvTel = (TextView) findViewById(R.id.tv_designer_essential_info_tel);
        tvEmail = (TextView) findViewById(R.id.tv_designer_essential_info_mail);
        tvSex = (TextView) findViewById(R.id.tv_designer_essential_info_sex);
        tvProjectCost = (TextView) findViewById(R.id.tv_designer_essential_info_project_cost);
        tvEssentialPhoto = (TextView) findViewById(R.id.tv_essential_photo);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();

        mConsumerEssentialInfoEntity = (ConsumerEssentialInfoEntity) getIntent().getSerializableExtra(Constant.DesignerCenterBundleKey.MEMBER_INFO);
        mDesignerInfoDetails = (DesignerInfoDetails) getIntent().getSerializableExtra(Constant.DesignerCenterBundleKey.HOUSE_COST);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mImageProcessingUtil = new ImageProcessingUtil();
        mPictureProcessingUtil = new PictureProcessingUtil();

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        strDesignerId = memberEntity.getMember_id();
        strHsUid = memberEntity.getHs_uid();
        setTitleForNavbar(UIUtils.getString(R.string.essential_info));
        initInfo();
    }

    @Override
    protected void initListener() {
        super.initListener();
        reEssentialMeasureHouse.setOnClickListener(this);
        rlEssentialProjectCost.setOnClickListener(this);
        rlEssentialSex.setOnClickListener(this);
        rlNickName.setOnClickListener(this);
        rlLocation.setOnClickListener(this);
        piv_essential_photo.setOnClickListener(this);
        tvEssentialPhoto.setOnClickListener(this);
    }

    private void initInfo() {
        if (mConsumerEssentialInfoEntity == null) {
            return;
        }
        if (mDesignerInfoDetails == null) {
            return;
        }
        strCurrentProvince = mConsumerEssentialInfoEntity.getProvince_name();
        strCurrentProvinceCode = mConsumerEssentialInfoEntity.getProvince();
        mCurrentCity = mConsumerEssentialInfoEntity.getCity_name();
        mCurrentCityCode = mConsumerEssentialInfoEntity.getCity();
        mCurrentDistrict = mConsumerEssentialInfoEntity.getDistrict_name();
        mCurrentDistrictCode = mConsumerEssentialInfoEntity.getDistrict();
        if (mConsumerEssentialInfoEntity.getAvatar().isEmpty()) {
//            piv_essential_photo.setImageDrawable(getResources().getDrawable(R.drawable.icon_default_avator));
            headPicBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_default_avator);
            piv_essential_photo.setImageBitmap(headPicBitmap);
        } else {
            ImageUtils.displayAvatarImage(mConsumerEssentialInfoEntity.getAvatar(), piv_essential_photo);
        }

        if (mConsumerEssentialInfoEntity.getHitachi_account().isEmpty()) {
            tvUserName.setText(getResources().getString(R.string.no_data));
        } else {
            tvUserName.setText(mConsumerEssentialInfoEntity.getHitachi_account() + "");
        }

        if (mConsumerEssentialInfoEntity.getNick_name() == null) {
            tvNickName.setText(getResources().getString(R.string.no_data));
        } else {
            tvNickName.setText(mConsumerEssentialInfoEntity.getNick_name() + "");
        }
        String province_name = mConsumerEssentialInfoEntity.getProvince_name();
        String city_name = mConsumerEssentialInfoEntity.getCity_name();
        String district_name = mConsumerEssentialInfoEntity.getDistrict_name();
        if (TextUtils.isEmpty(province_name) || TextUtils.isEmpty(city_name) || "<null>".equals(province_name)) {
            tvLocation.setText(getResources().getString(R.string.temporarily_no_data));
        } else {
            if (TextUtils.isEmpty(district_name) || "none".equals(district_name) || "null".equals(district_name)) {
                district_name = "";
            }
            tvLocation.setText(province_name + " " + city_name + " " + district_name);
        }

        if (mConsumerEssentialInfoEntity.getMobile_number() == null || mConsumerEssentialInfoEntity.getIs_validated_by_mobile() == 0 || mConsumerEssentialInfoEntity.getIs_validated_by_mobile() == 2)

        {
            tvTel.setText(getResources().getString(R.string.no_mobile));
        } else

        {
            tvTel.setText(mConsumerEssentialInfoEntity.getMobile_number() + "");
        }

        if (mConsumerEssentialInfoEntity.getEmail() == null||mConsumerEssentialInfoEntity.getIs_email_binding()==0||mConsumerEssentialInfoEntity.getIs_email_binding()==2)
        {
            tvEmail.setText(getResources().getString(R.string.not_filled));
        } else

        {
            tvEmail.setText(mConsumerEssentialInfoEntity.getEmail() + "");
        }

        if (mConsumerEssentialInfoEntity.getGender() == 0)

        {
            tvSex.setText(getResources().getString(R.string.secret));
        } else if (mConsumerEssentialInfoEntity.getGender() == 1)

        {
            tvSex.setText(getResources().getString(R.string.girl));
        } else

        {
            tvSex.setText(getResources().getString(R.string.boy));
        }

        DesignerInfoDetails.DesignerBean designer = mDesignerInfoDetails.getDesigner();
        if (null == designer)

        {
            return;
        }

        if (designer.getMeasurement_price() == null)

        {
            tvMeasureHouse.setText(getResources().getString(R.string.not_filled));
        } else

        {
            tvMeasureHouse.setText(designer.getMeasurement_price() + getResources().getString(R.string.flow_monad_rmb));
        }

        if (designer.getDesign_price_min() == null || designer.getDesign_price_max() == null)

        {
            tvProjectCost.setText(getResources().getString(R.string.not_filled));
        } else

        {
            tvProjectCost.setText(designer.getDesign_price_min() + "-" + designer.getDesign_price_max() + getResources().getString(R.string.monad_rmb_meters));
        }

        getGender();
        //获取设计师个人设计费用区间
        getDesignerDesignCostRange();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.piv_essential_photo:
            case R.id.tv_essential_photo: /// 上传照片 .
                new ActionSheetDialog(DesignerEssentialInfoActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem(getResources().getString(R.string.autonym_camera), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        cameraPhoto();
                                    }
                                })
                        .addSheetItem(getResources().getString(R.string.autonym_album), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        systemPhoto();
                                    }
                                }).show();
                break;


            case R.id.rl_essential_info_measure_house: /// 修改量房费 .
                Intent mIntent = new Intent(DesignerEssentialInfoActivity.this, CommonEssentialInfoAmendActivity.class);
                mIntent.putExtra(Constant.PersonCenterTagKey.ESSENTIAL_INFO_TAG, Constant.PersonCenterTagKey.MEASURE_HOUSE);
                if (tvMeasureHouse.getText().equals(UIUtils.getString(R.string.has_yet_to_fill_out))) {
                    mIntent.putExtra(Constant.PersonCenterTagKey.MEASURE_CONTENT, "");
                } else {
                    String str = tvMeasureHouse.getText().toString();
                    String strTemp[];
                    strTemp = str.split("元");
                    String num = strTemp[0];
                    mIntent.putExtra(Constant.PersonCenterTagKey.MEASURE_CONTENT, num);
                }
                startActivity(mIntent);
                break;

            case R.id.rl_essential_info_design_cost: /// 修改设计费 .
                optionsPickerView.show();
                break;

            case R.id.rl_essential_info_sex: /// 修改性别 .
                pvGenderOptions.show();
                break;

            case R.id.rl_designer_essential_info_nick_name: /// 修改昵称 .
                Intent dIntent = new Intent(DesignerEssentialInfoActivity.this, CommonEssentialInfoAmendActivity.class);
                dIntent.putExtra(Constant.PersonCenterTagKey.ESSENTIAL_INFO_TAG, Constant.PersonCenterTagKey.DESIGNER_INFO);
                dIntent.putExtra(Constant.PersonCenterTagKey.DESIGNER_CONTENT, tvNickName.getText());
                startActivity(dIntent);
                break;

            case R.id.rl_designer_essential_info_location: /// 修改所在地 .
                getPCDAddress();
                break;
        }
    }

    /**
     * 修改设计费
     */
    private void setProjectCost() {
        optionsPickerView = new OptionsPickerView(this);
        for (String item : projectCosts) {
            projectCostItems.add(item);
        }
        optionsPickerView.setPicker(projectCostItems);
        optionsPickerView.setTitle("设计预算");
        optionsPickerView.setSelectOptions(0);
        optionsPickerView.setCyclic(false);
        optionsPickerView.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                strProjectCost = projectCostItems.get(options1);

//                String designPriceCode;
//                for (int i = 0; i <relate_information_list.size(); i++){
//                    String costStringName = relate_information_list.get(i).getBrand_name();
////                    String costStringUnit = relate_information_list.get(i).getDescription();
////                    String costString = costStringName + costStringUnit;
//                    if (costStringName.equals(strProjectCost)){
//
//                        designPriceCode = relate_information_list.get(i).getCode();
//                    }
//
//                }


                String temp[];
                temp = strProjectCost.split("-");
                JSONObject jsonObj = new JSONObject();
                Double dou = null;
                String str = tvMeasureHouse.getText().toString();
                if (!str.equals(getResources().getString(R.string.not_filled))) {
                    String strTemp[];
                    strTemp = str.split("元");
                    dou = Double.valueOf(strTemp[0]);
                }
                try {
                    jsonObj.put(Constant.DesignerBasicInfoKey.DESIGN_PRICE_MIN, temp[0]);
                    jsonObj.put(Constant.DesignerBasicInfoKey.DESIGN_PRICE_MAX, temp[1]);
                    if (str.equals(getResources().getString(R.string.not_filled))) {
                        jsonObj.put(Constant.DesignerBasicInfoKey.MEASUREMENT_PRICE, null);
                    } else {
                        jsonObj.put(Constant.DesignerBasicInfoKey.MEASUREMENT_PRICE, dou);
                    }
                    jsonObj.put(Constant.DesignerBasicInfoKey.STYLE_LONG_NAMES, mDesignerInfoDetails.getDesigner().getStyle_long_names());
                    jsonObj.put(Constant.DesignerBasicInfoKey.INTRODUCTION, mDesignerInfoDetails.getDesigner().getIntroduction());
                    jsonObj.put(Constant.DesignerBasicInfoKey.EXPERIENCE, mDesignerInfoDetails.getDesigner().getExperience());
                    jsonObj.put(Constant.DesignerBasicInfoKey.PERSONAL_HONOUR, mDesignerInfoDetails.getDesigner().getPersonal_honour());
                    jsonObj.put(Constant.DesignerBasicInfoKey.DIY_COUNT, mDesignerInfoDetails.getDesigner().getDiy_count());
                    jsonObj.put(Constant.DesignerBasicInfoKey.CASE_COUNT, mDesignerInfoDetails.getDesigner().getCase_count());
                    jsonObj.put(Constant.DesignerBasicInfoKey.THEME_PIC, mDesignerInfoDetails.getDesigner().getTheme_pic());
                    jsonObj.put(Constant.DesignerBasicInfoKey.DESIGN_PRICE_CODE, options1);//上传选择的设计费用价格code
                    CustomProgress.show(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.design_fees_on_cross), false, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                putAmendDesignerCostData(strDesignerId, strHsUid, jsonObj);
                tvProjectCost.setText(strProjectCost + "元/m²");
            }
        });
    }


    /**
     * @param event
     * @brief 获取回传数据，用的EventBus .
     */
    public void onEventMainThread(InfoModifyEntity event) {
        JSONObject jsonObject = new JSONObject();
        int intSex;
        pTag = event.getpTag();
        if (pTag.equals("DesignerInfo")) {
            try {
                if (tvSex.getText().equals(UIUtils.getString(R.string.boy))) {
                    intSex = 2;
                } else if (tvSex.getText().equals(UIUtils.getString(R.string.girl))) {
                    intSex = 1;
                } else {
                    intSex = 0;
                }
                jsonObject.put(Constant.PersonCenterKey.NICK_NAME, event.getmMsg());
                jsonObject.put(Constant.PersonCenterKey.GENDER, intSex);
                jsonObject.put(Constant.PersonCenterKey.HOME_PHONE, mDesignerInfoDetails.getHome_phone());
                jsonObject.put(Constant.PersonCenterKey.BIRTHDAY, mDesignerInfoDetails.getBirthday());
                jsonObject.put(Constant.PersonCenterKey.ZIP_CODE, mDesignerInfoDetails.getZip_code());
                jsonObject.put(Constant.PersonCenterKey.PROVINCE_NAME, strCurrentProvince);
                jsonObject.put(Constant.PersonCenterKey.PROVINCE, strCurrentProvinceCode);
                jsonObject.put(Constant.PersonCenterKey.CITY_NAME, mCurrentCity);
                jsonObject.put(Constant.PersonCenterKey.CITY, mCurrentCityCode);
                jsonObject.put(Constant.PersonCenterKey.DISTRICT_NAME, mCurrentDistrict);
                jsonObject.put(Constant.PersonCenterKey.DISTRICT, mCurrentDistrictCode);

                putAmendDesignerInfoData(strDesignerId, jsonObject);
                tvNickName.setText(event.getmMsg());
                CustomProgress.show(DesignerEssentialInfoActivity.this, getResources().getString(R.string.nick_name_uploading), false, null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (pTag.equals("MeasureHouse")) {
            JSONObject mJsonObject = new JSONObject();
            String str = tvProjectCost.getText().toString();

            /// 将传过来 .56 格式转换成 0.56 格式 .
            String receiveFee = event.getmMsg();
            String gatherFee[] = receiveFee.split("\\.");
            String measureFee = receiveFee;

            if (gatherFee[0].length() == 0) {
                measureFee = "0" + receiveFee;
            }

            String one = null;
            String twoTest = null;
            if (!str.equals(UIUtils.getString(R.string.has_yet_to_fill_out))) {
                String strTemp[];
                strTemp = str.split("-");
                one = strTemp[0];
                String two = strTemp[1];
                String strTTemp[];
                strTTemp = two.split("元");
                twoTest = strTTemp[0];
            }

            try {
                mJsonObject.put(Constant.DesignerBasicInfoKey.MEASUREMENT_PRICE, measureFee);
                if (str.equals(UIUtils.getString(R.string.no_data))) {
                    mJsonObject.put(Constant.DesignerBasicInfoKey.DESIGN_PRICE_MIN, null);
                    mJsonObject.put(Constant.DesignerBasicInfoKey.DESIGN_PRICE_MAX, null);
                } else {
                    mJsonObject.put(Constant.DesignerBasicInfoKey.DESIGN_PRICE_MIN, one);
                    mJsonObject.put(Constant.DesignerBasicInfoKey.DESIGN_PRICE_MAX, twoTest);
                }
                mJsonObject.put(Constant.DesignerBasicInfoKey.STYLE_LONG_NAMES, mDesignerInfoDetails.getDesigner().getStyle_long_names());
                mJsonObject.put(Constant.DesignerBasicInfoKey.INTRODUCTION, mDesignerInfoDetails.getDesigner().getIntroduction());
                mJsonObject.put(Constant.DesignerBasicInfoKey.EXPERIENCE, mDesignerInfoDetails.getDesigner().getExperience());
                mJsonObject.put(Constant.DesignerBasicInfoKey.PERSONAL_HONOUR, mDesignerInfoDetails.getDesigner().getPersonal_honour());
                mJsonObject.put(Constant.DesignerBasicInfoKey.DIY_COUNT, mDesignerInfoDetails.getDesigner().getDiy_count());
                mJsonObject.put(Constant.DesignerBasicInfoKey.CASE_COUNT, mDesignerInfoDetails.getDesigner().getCase_count());
                mJsonObject.put(Constant.DesignerBasicInfoKey.CASE_COUNT, mDesignerInfoDetails.getDesigner().getTheme_pic());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            putAmendDesignerCostData(strDesignerId, strHsUid, mJsonObject);
            tvMeasureHouse.setText(measureFee + "元");
            CustomProgress.show(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.measure_room_cross), false, null);
        }
    }

    /**
     * 修改性别
     */
    public void getGender() {
        pvGenderOptions = new OptionsPickerView(this);
        for (String item : genderString) {
            genderList.add(item);
        }
        pvGenderOptions.setPicker(genderList);
        pvGenderOptions.setSelectOptions(0);
        pvGenderOptions.setCyclic(false);
        pvGenderOptions.setTitle("性别");
        pvGenderOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String gender = genderList.get(options1);
                tvSex.setText(gender);
                int which = 0;
                switch (gender) {
                    case Constant.Gender.SECRECY:
                        which = 0;
                        break;
                    case Constant.Gender.GIRL:
                        which = 1;
                        break;
                    case Constant.Gender.BOY:
                        which = 2;
                        break;
                }
                JSONObject jsonObject = new JSONObject();
                try {
                    CustomProgress.show(DesignerEssentialInfoActivity.this, "", false, null);
                    jsonObject.put(Constant.PersonCenterKey.NICK_NAME, tvNickName.getText());
                    jsonObject.put(Constant.PersonCenterKey.GENDER, which);
                    jsonObject.put(Constant.PersonCenterKey.HOME_PHONE, mDesignerInfoDetails.getHome_phone());
                    jsonObject.put(Constant.PersonCenterKey.BIRTHDAY, mDesignerInfoDetails.getBirthday());
                    jsonObject.put(Constant.PersonCenterKey.ZIP_CODE, mDesignerInfoDetails.getZip_code());
                    jsonObject.put(Constant.PersonCenterKey.PROVINCE_NAME, strCurrentProvince);
                    jsonObject.put(Constant.PersonCenterKey.PROVINCE, strCurrentProvinceCode);
                    jsonObject.put(Constant.PersonCenterKey.CITY_NAME, mCurrentCity);
                    jsonObject.put(Constant.PersonCenterKey.CITY, mCurrentCityCode);
                    jsonObject.put(Constant.PersonCenterKey.DISTRICT_NAME, mCurrentDistrict);
                    jsonObject.put(Constant.PersonCenterKey.DISTRICT, mCurrentDistrictCode);
                    tvSex.setText(genderString[which]);

                    putAmendDesignerInfoData(strDesignerId, jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取省市区地址
     */
    private void getPCDAddress() {
        if (!TextUtils.isEmpty(strCurrentProvince)
                || !TextUtils.isEmpty(mCurrentCity)) {
            mChangeAddressDialog = AddressDialog.getInstance(strCurrentProvince + " " + mCurrentCity + " " + mCurrentDistrict);
        } else {
            mChangeAddressDialog = AddressDialog.getInstance("尚未填写");
        }
        mChangeAddressDialog.show(getFragmentManager(), "mChangeAddressDialog");
        mChangeAddressDialog
                .setAddressListener(new AddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province, String provinceCode, String city, String cityCode, String area, String areaCode) {
                        strCurrentProvince = province;
                        strCurrentProvinceCode = provinceCode;
                        mCurrentCity = city;
                        mCurrentCityCode = cityCode;
                        // 由于有些地区没有区这个字段，将含有区域得字段name改为none，code改为0
                        mCurrentDistrict = area;
                        mCurrentDistrictCode = areaCode;

                        JSONObject jsonObject = new JSONObject();
                        int intSex;
                        try {

                            if (tvSex.getText().equals(UIUtils.getString(R.string.boy))) {
                                intSex = 2;
                            } else if (tvSex.getText().equals(UIUtils.getString(R.string.girl))) {
                                intSex = 1;
                            } else {
                                intSex = 0;
                            }
                            jsonObject.put(Constant.PersonCenterKey.NICK_NAME, tvNickName.getText());
                            jsonObject.put(Constant.PersonCenterKey.GENDER, intSex);
                            jsonObject.put(Constant.PersonCenterKey.HOME_PHONE, mDesignerInfoDetails.getHome_phone());
                            jsonObject.put(Constant.PersonCenterKey.BIRTHDAY, mDesignerInfoDetails.getBirthday());
                            jsonObject.put(Constant.PersonCenterKey.ZIP_CODE, mDesignerInfoDetails.getZip_code());
                            jsonObject.put(Constant.PersonCenterKey.PROVINCE_NAME, province);
                            jsonObject.put(Constant.PersonCenterKey.PROVINCE, provinceCode);
                            jsonObject.put(Constant.PersonCenterKey.CITY_NAME, city);
                            jsonObject.put(Constant.PersonCenterKey.CITY, cityCode);
                            jsonObject.put(Constant.PersonCenterKey.DISTRICT_NAME, area);
                            jsonObject.put(Constant.PersonCenterKey.DISTRICT, areaCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CustomProgress.show(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.area_changes), false, null);

                        putAmendDesignerInfoData(strDesignerId, jsonObject);

                        area = UIUtils.getNoStringIfEmpty(area);
                        address = strCurrentProvince + " " + mCurrentCity + " " + area;
                        tvLocation.setText(address);
                        mChangeAddressDialog.dismiss();
                    }

                });
    }

    /**
     * @brief Open the system photo album .
     */
    private void systemPhoto() {
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SYS_INTENT_REQUEST);
    }

    /**
     * @brief Call the camera take pictures .
     */
    private void cameraPhoto() {
        String sdStatus = Environment.getExternalStorageState();
        /// Detection of sd is available .
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            MyToast.show(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.autonym_sd_disabled));
            return;
        }
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        startActivityForResult(intent, CAMERA_INTENT_REQUEST);
    }

    /**
     * 图片截取
     *
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SYS_INTENT_REQUEST://系统相册
                    if (data != null) {
                        // 照片的原始资源地址
                        Uri originalUri = data.getData();
                        cropImageUri(originalUri, 300, 300, CROP_SMALL_PICTURE);
                    }
                    break;
                case CAMERA_INTENT_REQUEST://相机
                    cropImageUri(uritempFile, 300, 300, CROP_SMALL_PICTURE);
                    break;
                case CROP_SMALL_PICTURE://截图
                    if (uritempFile != null) {
                        Bitmap bitmap = null;
                        File file;
                        try {
                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                            file = saveBitmap2File(this, "headpic.png", bitmap);
                            piv_essential_photo.setImageBitmap(bitmap);
                            piv_essential_photo.invalidate();
                            try {
                                CustomProgress.show(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.head_on_the_cross), false, null);
                                putFile2Server(file);
                            } catch (Exception e) {
                                CustomProgress.cancelDialog();
                                e.printStackTrace();
                            }
                        } catch (FileNotFoundException e) {
                            ToastUtil.showCustomToast(this, "找不到图片");
                            e.printStackTrace();
                        }
                        if (bitmap != null) {
                            bitmap.recycle();
                        }

                    }
                    break;
//                case CROP_SMALL_PICTURE_1://截图
//                    if (uritempFile != null) {
//                        Bitmap bitmap;
//                        File file;
//                        try {
//                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
//                            file = saveBitmap2File(this, "headpic.png", bitmap);
//                            piv_essential_photo.setImageBitmap(bitmap);
//                            bitmap.recycle();
//                            try {
//                                CustomProgress.show(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.head_on_the_cross), false, null);
//                                putFile2Server(file);
//                            } catch (Exception e) {
//                                CustomProgress.cancelDialog();
//                                e.printStackTrace();
//                            }
//                        } catch (FileNotFoundException e) {
//                            ToastUtil.showCustomToast(this, "找不到图片");
//                            e.printStackTrace();
//                        }
//
//                    }
//                    break;
                default:
                    break;
            }
        }
//
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * bitmap 转为file 缓存文件
     *
     * @param context
     * @param filename
     * @param bitmap
     * @return
     */
    private File saveBitmap2File(Context context, String filename, Bitmap bitmap) {
        File f = new File(context.getCacheDir(), filename);
        try {
            f.createNewFile();

            //将bitmap转为array数组
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);
            byte[] bitmapdata = bos.toByteArray();

            //讲数组写入到文件
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }


    /**
     * @param intent
     * @return
     * @biref 解决小米手机上获取图片路径为null的情况
     */
    public Uri getUri(Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.ImageColumns._ID},
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    /**
     * upload file
     *
     * @param file 文件
     * @throws Exception
     */
    public void putFile2Server(File file) throws Exception {
        if (file.exists() && file.length() > 0) {
            MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
            OkHttpClient okHttpClient = initClient();

            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
            String xToken = null;

            if (memberEntity != null)
                xToken = memberEntity.getHs_accesstoken();

            RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);
            MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
            builder.addFormDataPart("file", file.getName(), fileBody);

            RequestBody reqBody = builder.build();
            com.squareup.okhttp.Request.Builder reqBuilder = new com.squareup.okhttp.Request.Builder();
            reqBuilder.url(UrlConstants.URL_PUT_AMEND_DESIGNER_INFO_PHOTO);
            reqBuilder.header(Constant.NetBundleKey.X_TOKEN, Constant.NetBundleKey.X_TOKEN_PREFIX + xToken);
            reqBuilder.put(reqBody);

            okHttpClient.newCall(reqBuilder.build()).enqueue(new Callback() {
                @Override
                public void onFailure(com.squareup.okhttp.Request request, IOException e) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        public void run() {
                            dealResult(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.uploaded_failed));
                        }
                    });
                    CustomProgress.cancelDialog();
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    if (response.isSuccessful()) {
                        dealResult(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.uploaded_successfully));
                    } else {
                        dealResult(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.uploaded_failed));
//                        throw new IOException("Unexpected code " + response);
                    }
                    CustomProgress.cancelDialog();
                }
            });

        } else {
            MyToast.show(this, UIUtils.getString(R.string.file_does_not_exist));
        }
    }

    /**
     * init the client
     *
     * @return
     */
    @NonNull
    private OkHttpClient initClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(120, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(120, TimeUnit.SECONDS);
        return okHttpClient;
    }

    /**
     * deal with the result of upload
     *
     * @param activity
     * @param string
     */
    private void dealResult(final Activity activity, final String string) {
        CustomProgress.cancelDialog();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                MyToast.show(activity, string);
//                piv_essential_photo.setImageBitmap(headPicBitmap);
            }
        });
    }

    /**
     * @brief To get photos bitmap after taking pictures .
     */
    public Bitmap cameraCamera(Intent data) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String name = formatter.format(System.currentTimeMillis()) + ".jpg";
        Bundle bundle = data.getExtras();
        /// Access to data returned by the camera, and converted to Bitmap image format .
        Bitmap bitmap = (Bitmap) bundle.get("data");

        FileOutputStream b = null;
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path + "/myImage/");
        /// Test folder exists, create the folder does not exist, it .
        if (!file.exists() && !file.isDirectory())
            file.mkdirs();
        String fileName = file.getPath() + "/" + name;
        try {
            b = new FileOutputStream(fileName);
            /// The data to a file .
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
            strCameraFilePath = fileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b == null)
                    return bitmap;
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


    //获取量房费用设计区间，
    public void getDesignerDesignCostRange() {

        MPServerHttpManager.getInstance().getDesignerDesignCost(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                String designerDesignCostData = GsonUtil.jsonToString(jsonObject);
                DesignerDesignCostBean mDecorationListBean = GsonUtil.jsonToBean(designerDesignCostData, DesignerDesignCostBean.class);
                relate_information_list = mDecorationListBean.getRelate_information_list();
                //projectCosts
                projectCosts = new String[relate_information_list.size()];
                for (int i = 0; i < relate_information_list.size(); i++) {
                    String costStringName = relate_information_list.get(i).getName();
//                    String costStringUnit = relate_information_list.get(i).getDescription();
//                    String costString = costStringName + costStringUnit;
                    projectCosts[i] = costStringName;

                }
                setProjectCost();
            }
        });


    }

    /**
     * @param strDesignerId
     * @param hs_uid
     * @param jsonObject
     * @brief 更新设计师扩展信息
     */
    public void putAmendDesignerCostData(String strDesignerId, String hs_uid, JSONObject jsonObject) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            String jsonString;

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                jsonString = GsonUtil.jsonToString(jsonObject);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                MyToast.show(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.fail));
            }
        };
        MPServerHttpManager.getInstance().putAmendDesignerCostData(strDesignerId, hs_uid, jsonObject, callback);
    }

    /**
     * @param strDesignerId
     * @brief 更新设计师基础信息
     * @brief Modify the designer information .
     */
    public void putAmendDesignerInfoData(String strDesignerId, JSONObject jsonObject) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            String jsonString;

            @Override
            public void onResponse(JSONObject jsonObject) {
                CustomProgress.cancelDialog();
                jsonString = GsonUtil.jsonToString(jsonObject);
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                MyToast.show(DesignerEssentialInfoActivity.this, UIUtils.getString(R.string.fail));
            }
        };
        MPServerHttpManager.getInstance().putAmendDesignerInfoData(strDesignerId, jsonObject, callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); // 反注册EventBus
    }

    @Override
    protected void onPause() {
        super.onPause();
        CustomProgress.cancelDialog();
    }

    private static final int SYS_INTENT_REQUEST = 0XFF01;
    private static final int CAMERA_INTENT_REQUEST = 0XFF02;

    private TextView tvLocation;
    private TextView tvMeasureHouse;
    private TextView tvUserName;
    private TextView tvNickName;
    private TextView tvTel;
    private TextView tvEmail;
    private TextView tvSex;
    private TextView tvProjectCost;
    private TextView tvEssentialPhoto;
    private RelativeLayout reEssentialMeasureHouse;
    private RelativeLayout rlEssentialProjectCost;
    private RelativeLayout rlLocation;
    private RelativeLayout rlEssentialSex;
    private RelativeLayout rlNickName;
    private PolygonImageView piv_essential_photo;
    private OptionsPickerView optionsPickerView;
    private OptionsPickerView pvGenderOptions;
    private AddressDialog mChangeAddressDialog;

    private String strDesignerId;
    private String strHsUid;
    private String strCameraFilePath;
    private String strProjectCost;
    private String strCurrentProvince, mCurrentCity, mCurrentDistrict;
    private String strCurrentProvinceCode, mCurrentCityCode, mCurrentDistrictCode;
    private String pTag;
    private String[] genderString = {"保密", "女", "男"};
    //private String[] projectCosts = {"30-60", "61-120", "121-200", "201-600", "601-1000"};
    private String[] projectCosts;
    private ArrayList<String> projectCostItems = new ArrayList<>();
    private ArrayList<String> genderList = new ArrayList<>();

    private DesignerInfoDetails mDesignerInfoDetails;
    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;
    private List<DesignerDesignCostBean.RelateInformationListBean> relate_information_list;

    private String address;
    private PictureProcessingUtil mPictureProcessingUtil;
    private ImageProcessingUtil mImageProcessingUtil;
}

