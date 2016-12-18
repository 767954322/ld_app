package com.autodesk.shejijia.enterprise.personalcenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import com.autodesk.shejijia.enterprise.personalcenter.datamodel.PersonalCenterRemoteDataSource;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.listener.ResponseCallback;
import com.autodesk.shejijia.shared.components.common.utility.UserInfoUtils;

import java.io.File;
import java.util.Random;


/**
 * Created by luchongbin on 16-12-16.
 */

public class PersonalCenterPresenter implements PersonalCenterContract.Presenter {
    private Context mContext;
    private PersonalCenterRemoteDataSource mPersonalCenterRemoteDataSource;
    private PersonalCenterContract.View mPersonalCenterContract;
    private Uri mUritempFile;

    public PersonalCenterPresenter(Context mContext) {
        this.mContext = mContext;
        this.mPersonalCenterContract = (PersonalCenterContract.View) mContext;
        this.mPersonalCenterRemoteDataSource = PersonalCenterRemoteDataSource.getInstance();
    }

    @Override
    public void uploadPersonalHeadPicture(File file) {
        String acsMemberId = UserInfoUtils.getAcsMemberId(mContext);
        mPersonalCenterRemoteDataSource.uploadPersonalHeadPicture(file,acsMemberId);
    }

    @Override
    public void getPersonalHeadPicture(String requestTag) {
        String acsMemberId = UserInfoUtils.getAcsMemberId(mContext);
        mPersonalCenterRemoteDataSource.getPersonalHeadPicPicture(requestTag, acsMemberId, new ResponseCallback<String, ResponseError>() {
            @Override
            public void onSuccess(String avatar) {
                mPersonalCenterContract.updatePersonalHeadPictureView(avatar);
            }

            @Override
            public void onError(ResponseError error) {
            }
        });
    }

    @Override
    public void systemPhoto(int requestCode) {
        mUritempFile = getRandomUritempFile();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity)mContext).startActivityForResult(intent, requestCode);

    }

    @Override
    public void cameraPhoto(int requestCode) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        mUritempFile = getRandomUritempFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUritempFile);
        ((Activity)mContext).startActivityForResult(intent, requestCode);

    }

    @Override
    public void cropImageUri(Uri uri,int outputX,int outputY,int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        ((Activity)mContext).startActivityForResult(intent, requestCode);
    }
    @Override
    public Uri getUritempFile(){
        return mUritempFile;
    }
    private Uri getRandomUritempFile(){
        Random random = new Random();
        int randomNumber = Math.abs(random.nextInt(100));
        return Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" +randomNumber+ "small.png");

    }
}
