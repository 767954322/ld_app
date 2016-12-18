package com.autodesk.shejijia.enterprise.personalcenter;
import android.net.Uri;
import android.os.Bundle;
import com.autodesk.shejijia.shared.framework.BasePresenter;
import com.autodesk.shejijia.shared.framework.BaseView;

/**
 * Created by luchongbin on 16-12-16.
 */

public interface PersonalCenterContract {
    interface View extends BaseView {
        void updatePersonalHeadPictureView(String avatar);

    }
    interface Presenter extends BasePresenter {

        void uploadPersonalHeadPicture(Bundle bundle, String requestTag);
        void getPersonalHeadPicture(String requestTag);
        void systemPhoto(int requestCode);
        void cameraPhoto(int requestCode);
        void cropImageUri(Uri uri, int outputX, int outputY, int requestCode);
        Uri getUritempFile();

    }
}
