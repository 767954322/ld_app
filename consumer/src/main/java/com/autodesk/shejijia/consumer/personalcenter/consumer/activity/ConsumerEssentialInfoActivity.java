package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.ConsumerApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerQrEntity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.InfoModifyEntity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.CommonEssentialInfoAmendActivity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.ToastUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.tools.wheel.CityDataHelper;
import com.autodesk.shejijia.shared.components.common.tools.zxing.encoding.EncodingHandler;
import com.autodesk.shejijia.consumer.uielements.ActionSheetDialog;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.consumer.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.SingleClickUtils;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.consumer.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.PictureProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
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
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-6
 * @file ConsumerEssentialInfoActivity.java  .
 * @brief 消费者个人中心基本信息.
 */
public class ConsumerEssentialInfoActivity extends NavigationBarActivity implements View.OnClickListener {

    private static final int SYS_INTENT_REQUEST = 0XFF01;
    private static final int CAMERA_INTENT_REQUEST = 0XFF02;
    private Bitmap headPicBitmap;

    private Uri uritempFile;
    private static final int CROP_SMALL_PICTURE = 5;//截图
    private static final int CROP_SMALL_PICTURE_1 = 6;//截图

    private int is_validated_by_mobile;
    private int is_validated_by_email;
    private CityDataHelper mCityDataHelper;
    private SQLiteDatabase mDb;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_essential_info;
    }

    @Override
    protected void initView() {
        super.initView();
        mConsumeNickname = (RelativeLayout) findViewById(R.id.rl_consume_essential_nickname);
        mConsumeAddress = (RelativeLayout) findViewById(R.id.rl_consume_essential_address);
        mTvUploadPicture = (TextView) findViewById(R.id.tv_consume_essential_upload_picture);
        mTvNickname = (TextView) findViewById(R.id.tv_consume_essential_nickname);
        mTvConsumeAddress = (TextView) findViewById(R.id.tv_consume_essential_address);
        mRlConsumerQrCode = (RelativeLayout) findViewById(R.id.rl_consumer_qrcode);
        mRlSex = (RelativeLayout) findViewById(R.id.rl_consume_essential_sex);
        mTvConsumerPhone = (TextView) findViewById(R.id.tv_consumer_phone);
        mTvConsumerName = (TextView) findViewById(R.id.tv_consumer_name);
        mConsumeHeadIcon = (PolygonImageView) findViewById(R.id.piv_essential_consumer_photo);
        mTvSex = (TextView) findViewById(R.id.tv_consumer_essential_info_sex);
        mTvEmail = (TextView) findViewById(R.id.tv_designer_essential_info_email);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
        mConsumerEssentialInfoEntity = (ConsumerEssentialInfoEntity) getIntent().getExtras().get(Constant.ConsumerPersonCenterFragmentKey.CONSUMER_PERSON);
//        if (mConsumerEssentialInfoEntity != null) {
//           // getConsumerInfoData(member_id);
//        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.essential_info));

        EventBus.getDefault().register(this);
        imageProcessingUtil = new ImageProcessingUtil();
        pictureProcessingUtil = new PictureProcessingUtil();
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        member_id = memberEntity.getAcs_member_id();
        hs_uid = memberEntity.getHs_uid();

        if (memberEntity != null) {
            getConsumerInfoData(member_id);
        }

        setTextColorForRightNavButton(UIUtils.getColor(R.color.black));

        showState();


        setGender();
    }

    /**
     * 获取个人基本信息
     *
     * @param member_Id
     * @brief For details on consumers .
     */
    public void getConsumerInfoData(String member_Id) {
        MPServerHttpManager.getInstance().getConsumerInfoData(member_Id, new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String jsonString = GsonUtil.jsonToString(jsonObject);
                mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);
                showState();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
//                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, ConsumerEssentialInfoActivity.this,
//                            AlertView.Style.Alert, null).show();
                ApiStatusUtil.getInstance().apiStatuError(volleyError, ConsumerEssentialInfoActivity.this);
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mConsumerEssentialInfoEntity != null) {
//            getConsumerInfoData(member_id);
//        }
//    }

//    /**
//     * 获取个人基本信息
//     *
//     * @param member_Id
//     * @brief For details on consumers .
//     */
//    public void getConsumerInfoData(String member_Id) {
//        MPServerHttpManager.getInstance().getConsumerInfoData(member_Id, new OkJsonRequest.OKResponseCallback() {
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                String jsonString = GsonUtil.jsonToString(jsonObject);
//                mConsumerEssentialInfoEntity = GsonUtil.jsonToBean(jsonString, ConsumerEssentialInfoEntity.class);
//                showState();
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                MPNetworkUtils.logError(TAG, volleyError);
//                    new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, ConsumerEssentialInfoActivity.this,
//                            AlertView.Style.Alert, null).show();
//            }
//        });
//    }

    @Override
    protected void setTextColorForRightNavButton(int color) {
        super.setTextColorForRightNavButton(color);

        TextView rightTextView = (TextView) findViewById(R.id.nav_right_textView);
        rightTextView.setText("baocun");
    }

    public void showState() {

        if (mConsumerEssentialInfoEntity == null) {
            return;
        }
        is_validated_by_mobile = mConsumerEssentialInfoEntity.getIs_validated_by_mobile();
        is_validated_by_email = mConsumerEssentialInfoEntity.getIs_email_binding();
        avatar = mConsumerEssentialInfoEntity.getAvatar();
        user_name = mConsumerEssentialInfoEntity.getHitachi_account();
        mobile_number = mConsumerEssentialInfoEntity.getMobile_number();
        nick_name = mConsumerEssentialInfoEntity.getNick_name();
        gender = mConsumerEssentialInfoEntity.getGender();
        email = mConsumerEssentialInfoEntity.getEmail();

//        province_name = mConsumerEssentialInfoEntity.getProvince_name();
//        city_name = mConsumerEssentialInfoEntity.getCity_name();
//        district_name = mConsumerEssentialInfoEntity.getDistrict_name();
        province = mConsumerEssentialInfoEntity.getProvince();
        city = mConsumerEssentialInfoEntity.getCity();
        district = mConsumerEssentialInfoEntity.getDistrict();

        mCityDataHelper = CityDataHelper.getInstance(this);
        mDb = mCityDataHelper.openDataBase();
        province_name = mCityDataHelper.getProvinceName(mDb, province);
        city_name = mCityDataHelper.getCityName(mDb, city);

        if (!TextUtils.isEmpty(district)) {
            if (!TextUtils.isEmpty(district.trim())) {
                district_name = mCityDataHelper.getDistrictName(mDb, district);
            } else {
                district_name = "";
            }
        } else {
            district_name = "";
        }


        mDb.close();

        if (!TextUtils.isEmpty(avatar)) {
            ImageUtils.displayAvatarImage(avatar, mConsumeHeadIcon);
        } else {
            headPicBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_default_avator);
            mConsumeHeadIcon.setImageBitmap(headPicBitmap);
        }

        /**
         * 用户名
         */
        setTvString(mTvConsumerName, user_name);

        /**
         * 昵称
         */
        setTvString(mTvNickname, nick_name);

        /**
         * 手机
         */
        if (0 == is_validated_by_mobile || 2 == is_validated_by_mobile)
            mobile_number = "";
        mTvConsumerPhone.setText(getResources().getString(R.string.no_mobile));
        if (1 == is_validated_by_mobile) {
            if (TextUtils.isEmpty(mobile_number)) {
                mobile_number = "";
                mTvConsumerPhone.setText(getResources().getString(R.string.no_mobile));
            } else {
                mTvConsumerPhone.setText(mobile_number);
            }
        }

        if (0 == is_validated_by_email || 2 == is_validated_by_email) {
            mTvEmail.setText(getResources().getString(R.string.no_email));
        } else {
            mTvEmail.setText(email);
        }


        /**
         * 所在地
         */
        if (TextUtils.isEmpty(province_name) || TextUtils.isEmpty(city_name) || province_name.equals("<null>") || city_name.equals("<null>")) {
            mTvConsumeAddress.setText(getResources().getString(R.string.temporarily_no_data));
        } else {
            if (TextUtils.isEmpty(district_name)
                    || "none".equals(district_name)
                    || "null".equals(district_name)) {
                district_name = "";
            }
            mTvConsumeAddress.setText(province_name + " " + city_name + " " + district_name);
        }

        /**
         * 性别
         */
        switch (gender) {
            case 0:
                mTvSex.setText(UIUtils.getString(R.string.secret));
                break;
            case 1:
                mTvSex.setText(UIUtils.getString(R.string.girl));
                break;
            default:
                mTvSex.setText(UIUtils.getString(R.string.boy));
                break;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        mConsumeNickname.setOnClickListener(this);
        mConsumeAddress.setOnClickListener(this);
        mConsumeHeadIcon.setOnClickListener(this);
        mTvUploadPicture.setOnClickListener(this);
        mRlConsumerQrCode.setOnClickListener(this);
        mRlSex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_consume_essential_nickname: /// 修改昵称 .
                Intent mIntent = new Intent(ConsumerEssentialInfoActivity.this, CommonEssentialInfoAmendActivity.class);
                mIntent.putExtra(Constant.PersonCenterTagKey.CONSUMER_CONTENT, mTvNickname.getText());
                mIntent.putExtra(Constant.PersonCenterTagKey.ESSENTIAL_INFO_TAG, Constant.PersonCenterTagKey.CONSUMER_INFO);
                startActivity(mIntent);
                break;
            case R.id.rl_consume_essential_address: /// 修改地址 .
                getPCDAddress();
                break;
            case R.id.piv_essential_consumer_photo:
            case R.id.tv_consume_essential_upload_picture: /// 上传头像 .
                new ActionSheetDialog(ConsumerEssentialInfoActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem(getResources().getString(R.string.photograph), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        cameraPhoto();
                                    }
                                })
                        .addSheetItem(getResources().getString(R.string.photo_album), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        systemPhoto();
                                    }
                                }).show();
                break;

            case R.id.rl_consumer_qrcode: /// 生成二维码 .
                if (!SingleClickUtils.isFastDoubleClickShort()) {

                    showPopupWindow();
                }
                break;

            case R.id.rl_consume_essential_sex: /// 修改性别 .
                pvGenderOptions.show();
                break;
        }
    }

    /**
     * 为TextView设置字符串内容
     *
     * @param mTv     控件
     * @param content 内容
     */
    private void setTvString(TextView mTv, String content) {
        if (TextUtils.isEmpty(content)) {
            mTv.setText(UIUtils.getString(R.string.not_filled));
        } else {
            mTv.setText(content);
        }
    }

    /**
     * 获取省市区
     */
    private void getPCDAddress() {
        if (!TextUtils.isEmpty(province_name)
                || !TextUtils.isEmpty(city_name)) {
            mChangeAddressDialog = AddressDialog.getInstance(province_name + " " + city_name + " " + district_name, UIUtils.getString(R.string.please_select_addresses));
        } else {
            mChangeAddressDialog = AddressDialog.getInstance("尚未填写");
        }

        mChangeAddressDialog.show(getFragmentManager(), "mChangeAddressDialog");

        mChangeAddressDialog
                .setAddressListener(new AddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province_name_1, String province_1, String city_name_1, String city_1, String district_name_1, String district_1) {
                        province_name = province_name_1;
                        city_name = city_name_1;
                        province = province_1;
                        city = city_1;

                        // 由于有些地区没有区这个字段，将含有区域得字段name改为none，code改为0
                        district_name = district_name_1;
                        district = district_1;

                        JSONObject jsonObject = new JSONObject();
                        int intSex;
                        try {
                            if (mTvSex.getText().equals(UIUtils.getString(R.string.boy))) {
                                intSex = 2;
                            } else if (mTvSex.getText().equals(UIUtils.getString(R.string.girl))) {
                                intSex = 1;
                            } else {
                                intSex = 0;
                            }
                            jsonObject.put(Constant.PersonCenterKey.NICK_NAME, mTvNickname.getText());
                            jsonObject.put(Constant.PersonCenterKey.GENDER, intSex);
                            jsonObject.put(Constant.PersonCenterKey.HOME_PHONE, mConsumerEssentialInfoEntity.getHome_phone());
                            jsonObject.put(Constant.PersonCenterKey.BIRTHDAY, mConsumerEssentialInfoEntity.getBirthday());
                            jsonObject.put(Constant.PersonCenterKey.ZIP_CODE, mConsumerEssentialInfoEntity.getZip_code());
                            jsonObject.put(Constant.PersonCenterKey.PROVINCE_NAME, province_name);
                            jsonObject.put(Constant.PersonCenterKey.PROVINCE, ConsumerEssentialInfoActivity.this.province);
                            jsonObject.put(Constant.PersonCenterKey.CITY_NAME, city_name);
                            jsonObject.put(Constant.PersonCenterKey.CITY, city);
                            jsonObject.put(Constant.PersonCenterKey.DISTRICT_NAME, district_name);
                            jsonObject.put(Constant.PersonCenterKey.DISTRICT, district);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CustomProgress.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.information_on_the_cross), false, null);
                        putAmendConsumerInfoData(member_id, jsonObject);

                        district_name_1 = UIUtils.getNoStringIfEmpty(district_name);
                        String address = province_name + " " + city_name + " " + district_name_1;

                        mTvConsumeAddress.setText(address);
                        mChangeAddressDialog.dismiss();
                    }
                });
    }

    /**
     * Open the system photo album .
     */
    private void systemPhoto() {
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SYS_INTENT_REQUEST);
    }

    /**
     * Call the camera take pictures .
     */
    private void cameraPhoto() {
        String sdStatus = Environment.getExternalStorageState();
        /// Detection of sd is available .
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            MyToast.show(this, UIUtils.getString(R.string.autonym_sd_disabled));
            return;
        }
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        startActivityForResult(intent, CAMERA_INTENT_REQUEST);
    }

    /**
     * To get photos bitmap after taking pictures .
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
            cameraFilePath = fileName;
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

    /**
     * 解决小米手机上获取图片路径为null的情况
     *
     * @param intent 意图
     * @return 返回照片路径
     */
    public Uri getUri(android.content.Intent intent) {
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
     * 修改性别
     */
    public void setGender() {
        String[] genderString = {"保密", "女", "男"};
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
                mTvSex.setText(gender);
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
                    CustomProgress.show(ConsumerEssentialInfoActivity.this, "", false, null);
                    jsonObject.put(Constant.PersonCenterKey.NICK_NAME, mTvNickname.getText());
                    jsonObject.put(Constant.PersonCenterKey.GENDER, which);
                    jsonObject.put(Constant.PersonCenterKey.HOME_PHONE, mConsumerEssentialInfoEntity.getHome_phone());
                    jsonObject.put(Constant.PersonCenterKey.BIRTHDAY, mConsumerEssentialInfoEntity.getBirthday());
                    jsonObject.put(Constant.PersonCenterKey.ZIP_CODE, mConsumerEssentialInfoEntity.getZip_code());
                    jsonObject.put(Constant.PersonCenterKey.PROVINCE_NAME, province_name);
                    jsonObject.put(Constant.PersonCenterKey.PROVINCE, province);
                    jsonObject.put(Constant.PersonCenterKey.CITY_NAME, city_name);
                    jsonObject.put(Constant.PersonCenterKey.CITY, city);
                    jsonObject.put(Constant.PersonCenterKey.DISTRICT_NAME, district_name);
                    jsonObject.put(Constant.PersonCenterKey.DISTRICT, district);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                putAmendConsumerInfoData(member_id, jsonObject);
            }
        });
    }

    /**
     * 弹出生成的消费者信息的二维码
     */
    private void showPopupWindow() {
        /// Popup layout content .
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.activity_consumer_zxing, null);
        mPopupWindow = new PopupWindow(contentView, RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(dw);
        ImageButton ibnQrCancel = (ImageButton) contentView.findViewById(R.id.ibn_consumer_qr_cancel);
        /// The qr code of consumers .
        ImageView iv_consumer_zxing = (ImageView) contentView.findViewById(R.id.iv_consumer_qr);
        RelativeLayout rl_consumer_zxing = (RelativeLayout) contentView.findViewById(R.id.rl_consumer_qr);
        iv_consumer_zxing.setImageDrawable(generateQR());
        mPopupWindow.setOutsideTouchable(false); /// Click on the outside can't disappear .
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(findViewById(R.id.ll_consumer_container), Gravity.LEFT + Gravity.TOP, 0, 0);
        ibnQrCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }
            }
        });
        rl_consumer_zxing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }


            }
        });
        /// mPopupWindow ScaleAnimation .
        ScaleAnimation sa = new ScaleAnimation(0.3f, 1.0f, 0.3f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(150);
        contentView.startAnimation(sa);
    }

    /**
     * 生成含有信息的二维码
     */
    private Drawable generateQR() {
        if (android.text.TextUtils.isEmpty(mobile_number)) {
            mobile_number = "";
        }

        String memberType = null;
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity != null) {
            memberType = memberEntity.getMember_type();
        }

        ConsumerQrEntity consumerQrEntity = new ConsumerQrEntity(mobile_number, memberType, member_id, hs_uid, nick_name, avatar);
        String qrJson = new Gson().toJson(consumerQrEntity);
        LogUtils.i(TAG, qrJson);

        /// if the information is  Incomplete ,you can't generate qr code .
        if (null != consumerQrEntity) {
            Bitmap qrCodeBitmap = null;
            try {
                qrCodeBitmap = EncodingHandler.createQRCode(qrJson, 220);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            return new BitmapDrawable(qrCodeBitmap);
        } else {
            Toast.makeText(ConsumerEssentialInfoActivity.this, getResources().getString(R.string.qr_Incomplete), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void onEventMainThread(InfoModifyEntity event) {
        String pTag = event.getpTag();
        JSONObject jsonObject = new JSONObject();
        int intSex;
        if (pTag.equals("ConsumerInfo")) {
            try {
                if (mTvSex.getText().equals(UIUtils.getString(R.string.boy))) {
                    intSex = 2;
                } else if (mTvSex.getText().equals(UIUtils.getString(R.string.girl))) {
                    intSex = 1;
                } else {
                    intSex = 0;
                }
                nick_name = event.getmMsg();
                jsonObject.put(Constant.PersonCenterKey.NICK_NAME, event.getmMsg());
                jsonObject.put(Constant.PersonCenterKey.GENDER, intSex);
                jsonObject.put(Constant.PersonCenterKey.HOME_PHONE, mConsumerEssentialInfoEntity.getHome_phone());
                jsonObject.put(Constant.PersonCenterKey.BIRTHDAY, mConsumerEssentialInfoEntity.getBirthday());
                jsonObject.put(Constant.PersonCenterKey.ZIP_CODE, mConsumerEssentialInfoEntity.getZip_code());
                jsonObject.put(Constant.PersonCenterKey.PROVINCE_NAME, province_name);
                jsonObject.put(Constant.PersonCenterKey.PROVINCE, province);
                jsonObject.put(Constant.PersonCenterKey.CITY_NAME, city_name);
                jsonObject.put(Constant.PersonCenterKey.CITY, city);
                jsonObject.put(Constant.PersonCenterKey.DISTRICT_NAME, district_name);
                jsonObject.put(Constant.PersonCenterKey.DISTRICT, district);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomProgress.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.nickname_on_the_cross), false, null);
            putAmendConsumerInfoData(member_id, jsonObject);
            mTvNickname.setText(event.getmMsg());
        }
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
                            dealResult(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.uploaded_failed));
                        }
                    });
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    if (response.isSuccessful()) {
                        dealResult(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.uploaded_successfully));
                    } else {
                        dealResult(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.uploaded_failed));
//                        throw new IOException("Unexpected code " + response);
                    }
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
//                mConsumeHeadIcon.setImageBitmap(headPicBitmap);

            }
        });
    }


    /**
     * 修改消费者个人信息
     *
     * @param member_id
     * @param jsonObject
     */
    public void putAmendConsumerInfoData(String member_id, JSONObject jsonObject) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            String jsonString;

            @Override
            public void onResponse(JSONObject jsonObject) {
                jsonString = GsonUtil.jsonToString(jsonObject);
                MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                memberEntity.setNick_name(nick_name);
                ConsumerApplication.setMemberEntity(memberEntity);
                CustomProgress.cancelDialog();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError);
                CustomProgress.cancelDialog();
                Toast.makeText(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.fail), Toast.LENGTH_SHORT).show();
            }
        };
        MPServerHttpManager.getInstance().putAmendConsumerInfoData(member_id, jsonObject, callback);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == SYS_INTENT_REQUEST && resultCode == RESULT_OK && data != null) {
//            try {
//                Uri uri = data.getData();
//                /**
//                 * 解决上传图片找不到路径问题
//                 */
//                String imageFilePath = PhotoPathUtils.getPath(ConsumerEssentialInfoActivity.this, uri);
//                if (TextUtils.isEmpty(imageFilePath)) {
//                    return;
//                }
//                Object[] object = pictureProcessingUtil.judgePicture(imageFilePath);
//                File tempFile = new File(imageFilePath);
//                headPicBitmap = (Bitmap) object[1];
////                Bitmap _bitmap = (Bitmap) object[1];
//
//                File newFile = imageProcessingUtil.compressFileSize(tempFile);
//                putFile2Server(newFile);
//                CustomProgress.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.head_on_the_cross), false, null);
////                mConsumeHeadIcon.setImageBitmap(headPicBitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (requestCode == CAMERA_INTENT_REQUEST && resultCode == RESULT_OK && data != null) {
//            CustomProgress.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.head_on_the_cross), false, null);
//            Bitmap bitmap = cameraCamera(data);
////            Bitmap bit = pictureProcessingUtil.compressionBigBitmap(bitmap, true);
//            headPicBitmap = PictureProcessingUtil.compressionBigBitmap(bitmap, true);
//            File file = new File(cameraFilePath);
//            try {
//                putFile2Server(file);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
////            mConsumeHeadIcon.setImageBitmap(headPicBitmap);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

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
                            mConsumeHeadIcon.setImageBitmap(bitmap);
                            mConsumeHeadIcon.invalidate();
                            bitmap.recycle();
                            try {
                                CustomProgress.show(this, UIUtils.getString(R.string.head_on_the_cross), false, null);
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
//                    if (data != null) {
//                        Uri uri = data.getData();
//                        uritempFile = uri;
//                    }
//                    if (uritempFile != null) {
//                        Bitmap bitmap;
//                        File file;
//                        try {
//                            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
//                            file = saveBitmap2File(this, "headpic.png", bitmap);
//                            mConsumeHeadIcon.setImageBitmap(bitmap);
//                            bitmap.recycle();
//                            try {
//                                CustomProgress.show(this, UIUtils.getString(R.string.head_on_the_cross), false, null);
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
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); // 反注册EventBus
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && null == mPopupWindow) {
            finish();
            return super.onKeyDown(keyCode, event);
        } else if (keyCode == KeyEvent.KEYCODE_BACK && null != mPopupWindow) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        return super.onKeyDown(keyCode, event);
    }


    /// 控件.
    private TextView mTvNickname, mTvConsumeAddress;
    private TextView mTvConsumerPhone;
    private TextView mTvConsumerName;
    private TextView mTvSex;
    private TextView mTvUploadPicture;
    private TextView mTvEmail;
    private PolygonImageView mConsumeHeadIcon;
    private RelativeLayout mConsumeNickname, mConsumeAddress;
    private RelativeLayout mRlSex;
    private RelativeLayout mRlConsumerQrCode; /// qr code Scan  .
    private PopupWindow mPopupWindow; /// Qr code floating window .
    private AddressDialog mChangeAddressDialog;
    private OptionsPickerView pvGenderOptions;

    ///变量.
    private String cameraFilePath;
    private String member_id;
    private String avatar;
    private String nick_name;
    private String hs_uid;
    private String mobile_number;
    private String user_name;
    private String email;
    private String province, province_name;
    private String city, city_name;
    private String district, district_name;
    private int gender;

    ///集合，类．
    private ConsumerEssentialInfoEntity mConsumerEssentialInfoEntity;

    private PictureProcessingUtil pictureProcessingUtil;
    private ImageProcessingUtil imageProcessingUtil;
    private ArrayList<String> genderList = new ArrayList<>();
}