package com.autodesk.shejijia.shared.components.common.uielements;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by wenhulin on 11/10/16.
 */

// TODO Do real customizing
public class ConProgressDialog extends Dialog{

    private Context context;

    private ProgressDialog dialog;

    public ConProgressDialog(Context context) {
        super(context);
        this.context = context;
        dialog = new ProgressDialog(context);
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void setMessage(CharSequence message) {
        dialog.setMessage(message);
    }

    @Override
    public void show() {
        dialog.show();
    }

    @Override
    public void cancel() {
        dialog.cancel();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }
}
