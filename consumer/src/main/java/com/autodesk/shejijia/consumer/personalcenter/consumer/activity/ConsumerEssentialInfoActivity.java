package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.UrlConstants;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerEssentialInfoEntity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.ConsumerQrEntity;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.InfoModifyEntity;
import com.autodesk.shejijia.consumer.personalcenter.designer.activity.CommonEssentialInfoAmendActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;
import com.autodesk.shejijia.shared.components.common.utility.ImageProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.PictureProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.components.common.uielements.ActionSheetDialog;
import com.autodesk.shejijia.shared.components.common.uielements.AddressDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.reusewheel.utils.OptionsPickerView;
import com.autodesk.shejijia.shared.components.common.uielements.viewgraph.PolygonImageView;
import com.autodesk.shejijia.shared.components.common.tools.zxing.encoding.EncodingHandler;
import com.google.gson.Gson;
import com.google.zxing.WriterException;

import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

        setTextColorForRightNavButton(UIUtils.getColor(R.color.black));

        showState();

        /**
         * 邮箱
         */
        setTvString(mTvEmail, email);
        setGender();
    }

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
        avatar = mConsumerEssentialInfoEntity.getAvatar();
        user_name = mConsumerEssentialInfoEntity.getHitachi_account();
        mobile_number = mConsumerEssentialInfoEntity.getMobile_number();
        nick_name = mConsumerEssentialInfoEntity.getNick_name();
        gender = mConsumerEssentialInfoEntity.getGender();
        email = mConsumerEssentialInfoEntity.getEmail();

        province = mConsumerEssentialInfoEntity.getProvince();
        province_name = mConsumerEssentialInfoEntity.getProvince_name();
        city = mConsumerEssentialInfoEntity.getCity();
        city_name = mConsumerEssentialInfoEntity.getCity_name();
        district = mConsumerEssentialInfoEntity.getDistrict();
        district_name = mConsumerEssentialInfoEntity.getDistrict_name();
        if (!TextUtils.isEmpty(avatar)) {
            ImageUtils.displayAvatarImage(avatar, mConsumeHeadIcon);
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
        if (TextUtils.isEmpty(mobile_number)) {
            mTvConsumerPhone.setText(getResources().getString(R.string.no_mobile));
        } else {
            mTvConsumerPhone.setText(mobile_number);
        }

        /**
         * 所在地
         */
        if (TextUtils.isEmpty(province_name)) {
            mTvConsumeAddress.setText(getResources().getString(R.string.no_data));
        } else {
            if (district_name.isEmpty() || "none".equals(district_name)) {
                mTvConsumeAddress.setText(province_name + " " + city_name + " ");
            } else {
                mTvConsumeAddress.setText(province_name + " " + city_name + " " + district_name);
            }
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
                showPopupWindow();
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
            mTv.setText(UIUtils.getString(R.string.no_data));
        } else {
            mTv.setText(content);
        }
    }

    /**
     * 获取省市区
     */
    private void getPCDAddress() {
        mChangeAddressDialog = new AddressDialog();
        mChangeAddressDialog.show(getFragmentManager(), "mChangeAddressDialog");
        mChangeAddressDialog
                .setAddressListener(new AddressDialog.OnAddressCListener() {
                    @Override
                    public void onClick(String province_name_1, String province, String city_name_1, String city_1, String district_name_1, String district_1) {
                        province_name = province_name_1;
                        ConsumerEssentialInfoActivity.this.province = province;

                        city_name = city_name_1;
                        city = city_1;

                        district_name = TextUtils.isEmpty(district_name_1) ? "" : district_name_1;
                        district = TextUtils.isEmpty(district_1) || TextUtils.isEmpty(district_name) || "none".equals(district_name) ? "" : district_1;

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
                        mTvConsumeAddress.setText(province_name + " " + city_name + " " + district_name);
                        mChangeAddressDialog.dismiss();
                    }
                });
    }

    /**
     * Open the system photo album .
     */
    private void systemPhoto() {
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
            Toast.makeText(this, getResources().getString(R.string.autonym_sd_disabled), Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
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
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        });
        rl_consumer_zxing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
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
        KLog.d(TAG, qrJson);

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

            putAmendConsumerInfoData(member_id, jsonObject);
            mTvNickname.setText(event.getmMsg());
            CustomProgress.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.nickname_on_the_cross), false, null);
        }
    }

    // TODO REFACTORING
//    /**
//     * 上传头像
//     *
//     * @param file 文件路径
//     * @throws Exception
//     */
//    public void putFileToServer(File file) throws Exception {
//        if (file.exists() && file.length() > 0) {
//            AsyncHttpClient client = new AsyncHttpClient();
//
//            MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
//            String xToken = null;
//
//            if (memberEntity != null)
//                xToken = memberEntity.getHs_accesstoken();
//
//            client.addHeader(Constant.NetBundleKey.X_TOKEN, Constant.NetBundleKey.X_TOKEN_PREFIX + xToken);
//            RequestParams params = new RequestParams();
//            params.put("file", file);
//            client.put(UrlConstants.URL_PUT_AMEND_DESIGNER_INFO_PHOTO, params, new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(int i, Header[] headers, byte[] bytes) {
//                    MyToast.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.uploaded_successfully));
//                    CustomProgress.cancelDialog();
//                }
//
//                @Override
//                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                    MyToast.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.uploaded_failed));
//                    CustomProgress.cancelDialog();
//                }
//            });
//        } else {
//            Toast.makeText(this, UIUtils.getString(R.string.file_does_not_exist), Toast.LENGTH_SHORT).show();
//        }
//    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SYS_INTENT_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                String imageFilePath = null;
                Uri uri = getUri(data);//解决方案
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(uri, proj, null, null, null);
                if (cursor != null) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    imageFilePath = cursor.getString(column_index);// 图片在的路径
                }

                Object[] object = pictureProcessingUtil.judgePicture(imageFilePath);
                File tempFile = new File(imageFilePath);
                Bitmap _bitmap = (Bitmap) object[1];

                File newFile = imageProcessingUtil.compressFileSize(tempFile);
                //TODO REFACTORING
               // putFileToServer(newFile);
                CustomProgress.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.head_on_the_cross), false, null);
                mConsumeHeadIcon.setImageBitmap(_bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_INTENT_REQUEST && resultCode == RESULT_OK && data != null) {
            CustomProgress.show(ConsumerEssentialInfoActivity.this, UIUtils.getString(R.string.head_on_the_cross), false, null);
            Bitmap bitmap = cameraCamera(data);
            Bitmap bit = pictureProcessingUtil.compressionBigBitmap(bitmap, true);
            File file = new File(cameraFilePath);
            try {
                // TODO REFACTORING

//                putFileToServer(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mConsumeHeadIcon.setImageBitmap(bit);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
