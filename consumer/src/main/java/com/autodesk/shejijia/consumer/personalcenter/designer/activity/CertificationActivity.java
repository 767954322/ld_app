package com.autodesk.shejijia.consumer.personalcenter.designer.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.FileManager;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.manager.constants.JsonConstants;
import com.autodesk.shejijia.consumer.utils.IDCardUtils;
import com.autodesk.shejijia.consumer.utils.PhotoPathUtils;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.ActionSheetDialog;
import com.autodesk.shejijia.shared.components.common.uielements.CustomProgress;
import com.autodesk.shejijia.shared.components.common.uielements.MyToast;
import com.autodesk.shejijia.shared.components.common.uielements.TextViewContent;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.components.common.utility.PictureProcessingUtil;
import com.autodesk.shejijia.shared.components.common.utility.RegexUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Malidong .
 * @version 1.0 .
 * @date 16-6-6
 * @file CertificationActivity.java  .
 * @brief 实名认证提交信息 .
 */
public class CertificationActivity extends NavigationBarActivity implements View.OnClickListener {


    /**
     * @param mobiles
     * @brief 验证姓名输入得正则方法 .
     */
    public static boolean isName(String mobiles) {
        Pattern p = Pattern.compile(RegexUtil.NAME_REGEX);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * @param mobiles 手机号码
     * @return 手机号码是否正确
     * @brief 验证手机号得正则方法 .
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile(RegexUtil.PHONE_REGEX);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * @param papers 身份证号码
     * @return 身份证号码是否正确
     * @brief 验证身份证号 .
     */
    public static boolean isPapersNo(String papers) {
        Pattern p = Pattern.compile(RegexUtil.ID_CARD_REGEX);
        Matcher m = p.matcher(papers);
        return m.matches();
    }

    /**
     * @param ev
     * @return
     * @brief 隐藏软键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * @param v
     * @param event
     * @return
     * @brief 隐藏软键盘 .
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_autonym_commit: /// 提交按键 .
                getName = et_autonym_name.getText().toString();
                getPhone = et_autonym_phone.getText().toString();
                getIdentity = et_autonym_identity.getText().toString();
                boolean isName = isName(getName);
                boolean isMobile = isMobileNO(getPhone);

                boolean isPaper = IDCardUtils.IDCardValidate(getIdentity);
                if (isName) {
                    if (isMobile) {
                        if (isPaper) {
                            if (ObjPositive != null) {
                                if (ObjBack != null) {
                                    if (ObjHead != null) {
                                        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
                                        postRealNameData(getName, getPhone, getIdentity, "0", ObjPositive, ObjBack, ObjHead, memberEntity.getHs_uid());
                                    } else {
                                        MyToast.show(CertificationActivity.this, UIUtils.getString(R.string.no_holder_upload_my_front));
                                    }
                                } else {
                                    MyToast.show(CertificationActivity.this, UIUtils.getString(R.string.no_upload_id_opposite));
                                }
                            } else {
                                MyToast.show(CertificationActivity.this, UIUtils.getString(R.string.no_upload_id_positive));
                            }
                        } else {
                            MyToast.show(CertificationActivity.this, UIUtils.getString(R.string.id_number_input_is_not_correct));
                        }
                    } else {
                        MyToast.show(CertificationActivity.this, UIUtils.getString(R.string.mobile_phone_number_input_is_not_correct));
                    }
                } else {
                    MyToast.show(CertificationActivity.this, UIUtils.getString(R.string.no_input_name));
                }
                break;

            case R.id.img_autonym_front: /// 身份证正面 .
                state = 0;
                new ActionSheetDialog(CertificationActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem(UIUtils.getString(R.string.autonym_camera), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        cameraPhoto();
                                    }
                                })
                        .addSheetItem(UIUtils.getString(R.string.autonym_album), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        systemPhoto();
                                    }
                                }).show();
                break;

            case R.id.img_autonym_contrary: /// 身份证反面 .
                state = 1;
                new ActionSheetDialog(CertificationActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem(UIUtils.getString(R.string.autonym_camera), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        cameraPhoto();
                                    }
                                })
                        .addSheetItem(UIUtils.getString(R.string.autonym_album), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        systemPhoto();
                                    }
                                }).show();
                break;

            case R.id.img_autonym_permit: /// 手持身份证 .
                state = 2;
                new ActionSheetDialog(CertificationActivity.this)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem(UIUtils.getString(R.string.autonym_camera), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        cameraPhoto();
                                    }
                                })
                        .addSheetItem(UIUtils.getString(R.string.autonym_album), ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        systemPhoto();
                                    }
                                }).show();
                break;

        }
    }

    /**
     * @param true_name
     * @param mobile_number
     * @param birthday
     * @param audit_status
     * @param positive_photo
     * @param back_photo
     * @param head_photo
     * @param hs_uid
     * @brief 发送实名认证信息 .
     */
    public void postRealNameData(String true_name,
                                 String mobile_number,
                                 String birthday,
                                 String audit_status,
                                 JSONObject positive_photo,
                                 JSONObject back_photo,
                                 JSONObject head_photo,
                                 String hs_uid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonConstants.JSON_REAL_NAME_TRUE_NAME, true_name);
            jsonObject.put(JsonConstants.JSON_REAL_NAME_MOBILE_NUMBER, mobile_number);
            jsonObject.put(JsonConstants.JSON_REAL_NAME_BIRTHDAY, birthday);
//            jsonObject.put(JsonConstants.JSON_REAL_NAME_AUDIT_STATUS, audit_status);
            jsonObject.put(JsonConstants.JSON_REAL_NAME_POSITIVE_PHOTO, positive_photo);
            jsonObject.put(JsonConstants.JSON_REAL_NAME_BACK_PHOTO, back_photo);
            jsonObject.put(JsonConstants.JSON_REAL_NAME_HEAD_PHOTO, head_photo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                /// 认证信息发送成功后直接跳转认证信息状态类中 .
                Intent intent = new Intent(CertificationActivity.this, AttestationInfoActivity.class);
                intent.putExtra("AUDIT_STATUS", "4");
                startActivity(intent);
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MPNetworkUtils.logError(TAG, volleyError, true);
                Toast.makeText(CertificationActivity.this, UIUtils.getString(R.string.fail), Toast.LENGTH_SHORT).show();
            }
        };
        MPServerHttpManager.getInstance().postRealNameData(jsonObject, hs_uid, callback);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_autonym_attestation;
    }

    @Override
    protected void initView() {
        super.initView();
        pictureProcessingUtil = new PictureProcessingUtil();
        btn_autonym_commit = (Button) findViewById(R.id.btn_autonym_commit);
        img_autonym_front = (ImageView) findViewById(R.id.img_autonym_front);
        img_autonym_contrary = (ImageView) findViewById(R.id.img_autonym_contrary);
        img_autonym_permit = (ImageView) findViewById(R.id.img_autonym_permit);
        et_autonym_name = (TextViewContent) findViewById(R.id.et_autonym_name);
        et_autonym_phone = (TextViewContent) findViewById(R.id.et_autonym_phone);
        et_autonym_identity = (TextViewContent) findViewById(R.id.et_autonym_identity);
        tv_positive_photo = (TextView) findViewById(R.id.tv_positive_photo);
        tv_refuse_popup = (TextView) findViewById(R.id.tv_refuse_popup);
        tv_back_photo = (TextView) findViewById(R.id.tv_back_photo);
        tv_head_photo = (TextView) findViewById(R.id.tv_head_photo);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        tv_refuse_popup.requestFocus();

        setTitleForNavbar(UIUtils.getString(R.string.autonym_title));

    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_autonym_commit.setOnClickListener(this);
        et_autonym_name.setOnClickListener(this);
        et_autonym_phone.setOnClickListener(this);
        et_autonym_identity.setOnClickListener(this);
        img_autonym_front.setOnClickListener(this);
        img_autonym_contrary.setOnClickListener(this);
        img_autonym_permit.setOnClickListener(this);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * @brief 获取照片后执行的操作
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SYS_INTENT_REQUEST && resultCode == RESULT_OK && data != null) {
            try {
                Uri uri = data.getData();
                /**
                 * 解决上传图片找不到路径问题
                 */
                String imageFilePath = PhotoPathUtils.getPath(CertificationActivity.this, uri);

                if (TextUtils.isEmpty(imageFilePath)) {
                    return;
                }

                Object[] object = pictureProcessingUtil.judgePicture(imageFilePath); /// PictureProcessingUtil 压缩处理图片工具 .
                FileInputStream fis = new FileInputStream(imageFilePath);
                Bitmap _bitmap = (Bitmap) object[1];

                if (state == IMG_AUTONYM_FRONT) { /// 身份证正面 .
                    File file = new File(imageFilePath);
                    FileManager.getInstance().setHandler(handler);
                    FileManager.getInstance().getUploadDownloadServer(file, 0);
                    tv_positive_photo.setText(UIUtils.getString(R.string.autonym_uploading));
                    CustomProgress.show(CertificationActivity.this, UIUtils.getString(R.string.autonym_uploading), false, null);
                    img_autonym_front.setImageBitmap(_bitmap);
                } else if (state == IMG_AUTONYM_CONTRARY) { /// 身份证反面 .
                    File file = new File(imageFilePath);
                    FileManager.getInstance().setHandler(handler);
                    FileManager.getInstance().getUploadDownloadServer(file, 1);
                    tv_back_photo.setText(UIUtils.getString(R.string.autonym_uploading));
                    CustomProgress.show(CertificationActivity.this, UIUtils.getString(R.string.autonym_uploading), false, null);
                    img_autonym_contrary.setImageBitmap(_bitmap);
                } else if (state == IMG_AUTONYM_PERMIT) { /// 手持身份证 .
                    File file = new File(imageFilePath);
                    FileManager.getInstance().setHandler(handler);
                    FileManager.getInstance().getUploadDownloadServer(file, 2);
                    tv_head_photo.setText(UIUtils.getString(R.string.autonym_uploading));
                    CustomProgress.show(CertificationActivity.this, UIUtils.getString(R.string.autonym_uploading), false, null);
                    img_autonym_permit.setImageBitmap(_bitmap);
                }
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_INTENT_REQUEST && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(getPhotoPath(), getBitmapOption(2)); /// 将图片的长和宽缩小味原来的1/2 .

            if (state == IMG_AUTONYM_FRONT) {
                File file = new File(getPhotoPath());
                FileManager.getInstance().setHandler(handler);
                FileManager.getInstance().getUploadDownloadServer(file, 0);
                tv_positive_photo.setText(UIUtils.getString(R.string.autonym_uploading));
                CustomProgress.show(CertificationActivity.this, UIUtils.getString(R.string.autonym_uploading), false, null);
                img_autonym_front.setImageBitmap(bitmap);
            } else if (state == IMG_AUTONYM_CONTRARY) {
                File file = new File(getPhotoPath());
                FileManager.getInstance().setHandler(handler);
                FileManager.getInstance().getUploadDownloadServer(file, 1);
                tv_back_photo.setText(UIUtils.getString(R.string.autonym_uploading));
                CustomProgress.show(CertificationActivity.this, UIUtils.getString(R.string.autonym_uploading), false, null);
                img_autonym_contrary.setImageBitmap(bitmap);
            } else if (state == IMG_AUTONYM_PERMIT) {
                File file = new File(getPhotoPath());
                FileManager.getInstance().setHandler(handler);
                FileManager.getInstance().getUploadDownloadServer(file, 2);
                tv_head_photo.setText(UIUtils.getString(R.string.autonym_uploading));
                CustomProgress.show(CertificationActivity.this, UIUtils.getString(R.string.autonym_uploading), false, null);
                img_autonym_permit.setImageBitmap(bitmap);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @brief 打开系统相册 .
     */
    private void systemPhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SYS_INTENT_REQUEST);
    }

    /**
     * @brief 调用相机拍照 .
     */
    private void cameraPhoto() {
        /// Jump to the camera UI .
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        File out = new File(getPhotoPath());
        Uri uri = Uri.fromFile(out);
        /// After get photographed uncompressed the original image, and stored in the uri path .
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, CAMERA_INTENT_REQUEST);
    }

    /**
     * @brief 获取原图片保存路径 .
     */
    private String getPhotoPath() {
        String path = Environment.getExternalStorageDirectory().getPath();
        File file = new File(path + attestationPhbimage);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        String fileName = file.getPath() + "/" + imageTest;
        return fileName;
    }

    private BitmapFactory.Options getBitmapOption(int inSampleSize) { /// 图片压缩一半 .
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String file_id = bundle.getString("file_id");
            String file_name = bundle.getString("file_name");
            String file_url = bundle.getString("file_url");
            LogUtils.i(TAG, "file_url     " + file_url);
            handlerState = bundle.getInt("handlerState");

            if (handlerState == 0) {
                ObjPositive = new JSONObject();
                try {
                    ObjPositive.put(JsonConstants.JSON_REAL_NAME_FILE_ID, file_id);
                    ObjPositive.put(JsonConstants.JSON_REAL_NAME_FILE_NAME, file_name);
                    ObjPositive.put(JsonConstants.JSON_REAL_NAME_FILE_URL, file_url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomProgress.cancelDialog();
                tv_positive_photo.setText(UIUtils.getString(R.string.autonym_no_identity_front));
            }

            if (handlerState == 1) {
                ObjBack = new JSONObject();
                try {
                    ObjBack.put(JsonConstants.JSON_REAL_NAME_FILE_ID, file_id);
                    ObjBack.put(JsonConstants.JSON_REAL_NAME_FILE_NAME, file_name);
                    ObjBack.put(JsonConstants.JSON_REAL_NAME_FILE_URL, file_url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomProgress.cancelDialog();
                tv_back_photo.setText(UIUtils.getString(R.string.autonym_no_identity_contrary));
            }

            if (handlerState == 2) {
                ObjHead = new JSONObject();
                try {
                    ObjHead.put(JsonConstants.JSON_REAL_NAME_FILE_ID, file_id);
                    ObjHead.put(JsonConstants.JSON_REAL_NAME_FILE_NAME, file_name);
                    ObjHead.put(JsonConstants.JSON_REAL_NAME_FILE_URL, file_url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CustomProgress.cancelDialog();
                tv_head_photo.setText(UIUtils.getString(R.string.autonym_no_self_identity));
            }
        }
    };

    private static final int SYS_INTENT_REQUEST = 0XFF01;
    private static final int CAMERA_INTENT_REQUEST = 0XFF02;
    private static final int IMG_AUTONYM_FRONT = 0;
    private static final int IMG_AUTONYM_CONTRARY = 1;
    private static final int IMG_AUTONYM_PERMIT = 2;

    private TextView tv_positive_photo;
    private TextView tv_back_photo;
    private TextView tv_head_photo;
    private TextView tv_title;
    private TextView tv_refuse_popup;
    private ImageView img_autonym_front;
    private ImageView img_autonym_contrary;
    private ImageView img_autonym_permit;
    private Button btn_autonym_commit;
    private LinearLayout ll_back;
    private TextViewContent et_autonym_name;
    private TextViewContent et_autonym_phone;
    private TextViewContent et_autonym_identity;

    private String getName;
    private String getPhone;
    private String getIdentity;
    private String imageTest = "imageTest.jpg";
    private String attestationPhbimage = "/myImage/";
    private int state = -1; /// Which one image display .
    private int handlerState = -1;

    private JSONObject ObjPositive;
    private JSONObject ObjHead;
    private JSONObject ObjBack;
    private PictureProcessingUtil pictureProcessingUtil;
}
