package com.autodesk.shejijia.shared.components.form.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.shared.components.common.entity.Project;
import com.autodesk.shejijia.shared.components.common.network.ConstructionHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.form.contract.ProjectIdCodeContract;
import com.autodesk.shejijia.shared.components.form.ui.activity.ProjectInfoActivity;
import com.autodesk.shejijia.shared.components.form.ui.activity.QRCodeActivity;

import org.json.JSONObject;


/**
 * Created by t_aij on 16/10/21.
 */

public class ProjectIdCodePresenter implements ProjectIdCodeContract.Presenter{
    private ProjectIdCodeContract.View mView;
    private Context mContext;
    private String mToken;

    public ProjectIdCodePresenter(Context context, ProjectIdCodeContract.View view) {
        mView = view;
        mContext = context;

        mToken = "587e1e6bd9c26875535868dec8e3045c";
        //获取token
//        MemberEntity entity = (MemberEntity) SharedPreferencesUtils.getObject(mContext, Constants.USER_INFO);
//        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
//            XToken = "587e1e6bd9c26875535868dec8e3045c";
//            XToken = entity.getHs_accesstoken();
        mView.setNavigationBar();
    }



    @Override
    public void ConfirmProject() {
        String projectId = mView.getProjectId();
        if (TextUtils.isEmpty(projectId)) {
            mView.showError(null);
            return;
        }
        Long id = Long.valueOf(projectId);
        ConstructionHttpManager.getInstance().getProjectDetails(id, mToken, false,new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mView.showError(volleyError.toString());
            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                Project projectBean = GsonUtil.jsonToBean(jsonObject.toString(), Project.class);
                Intent intent = new Intent(mContext,ProjectInfoActivity.class);
                intent.putExtra("projectBean",projectBean);
                mContext.startActivity(intent);
                mView.dismiss();  //自身界面的消失
            }
        });

//        FormRepository.getInstance().getProjectDetails(id, new LoadDataCallback<ProjectBean>() {
//            @Override
//            public void onLoadSuccess(ProjectBean data) {
//                Intent intent = new Intent(mContext,ProjectInfoActivity.class);
//                intent.putExtra("projectBean",data);
//                mContext.startActivity(intent);
//                mView.dismiss();  //自身界面的消失
//            }
//
//            @Override
//            public void onLoadFailed(String errorMsg) {
//                mView.showError(errorMsg);
//            }
//        });
    }

    @Override
    public void enterCode() {
        // TODO: 16/10/25 进入扫码页面
        Intent intent = new Intent(mContext, QRCodeActivity.class);
        //这个方式必须是activity才可以调用,因为这是有返回值的
        mContext.startActivity(intent);
        mView.dismiss();

    }

}
