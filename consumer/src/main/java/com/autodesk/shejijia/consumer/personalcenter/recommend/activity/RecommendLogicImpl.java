package com.autodesk.shejijia.consumer.personalcenter.recommend.activity;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.recommend.entity.RecommendEntity;
import com.autodesk.shejijia.consumer.personalcenter.recommend.view.RecommendView;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.MPNetworkUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;

import org.json.JSONObject;

/**
 * @User: 蜡笔小新
 * @date: 16-10-26
 * @GitHub: https://github.com/meikoz
 * des:　列表页网络请求
 */

public class RecommendLogicImpl {
    RecommendView mRecommendView;

    public RecommendLogicImpl(RecommendView view) {
        this.mRecommendView = view;
    }

    public void onLoadRecommendListData(boolean isDesign, final int offset, int limit, int status) {
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        if (memberEntity == null) {
            return;
        }
        String member_id = memberEntity.getAcs_member_id();
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (!TextUtils.isEmpty(jsonObject.toString())) {
                    RecommendEntity entity = GsonUtil.jsonToBean(jsonObject.toString(), RecommendEntity.class);
                    mRecommendView.onLoadDataSuccess(offset,entity);
                }
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mRecommendView.onLoadFailer();
            }
        };
        MPServerHttpManager.getInstance().getRecommendList(isDesign, member_id, offset, limit, status, callback);
    }
}
