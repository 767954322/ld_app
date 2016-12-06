package com.autodesk.shejijia.shared.components.nodeprocess.utility;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ResponseError;
import com.autodesk.shejijia.shared.components.common.uielements.ConProgressDialog;
import com.autodesk.shejijia.shared.components.common.utility.LoginUtils;

/**
 * Class description
 *
 * @author wenhulin
 * @since 16/12/6
 */

public class DialogHelper {
    private Activity mActivity;
    private ConProgressDialog mProgressDialog;
    public DialogHelper(Activity context) {
        mActivity = context;
    }

    public void showNetError(final ResponseError error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.alert_dialog__default_title);
        builder.setMessage(error.getMessage());
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //token 失效，重新登录
                if (error.getStatus() == 401) {
                    LoginUtils.doLogout(mActivity);
                    LoginUtils.doLogin(mActivity);
                    mActivity.finish();
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showError(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(R.string.alert_dialog__default_title);
        builder.setMessage(error);
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ConProgressDialog(mActivity);
        }

        mProgressDialog.setMessage(mActivity.getString(R.string.loading));
        mProgressDialog.show();
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void showUpLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ConProgressDialog(mActivity);
        }

        mProgressDialog.setMessage(mActivity.getString(R.string.autonym_uploading));
        mProgressDialog.show();
    }

    public void hideUpLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }
}
