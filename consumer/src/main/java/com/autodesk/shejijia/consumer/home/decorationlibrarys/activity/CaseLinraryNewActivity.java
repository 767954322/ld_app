package com.autodesk.shejijia.consumer.home.decorationlibrarys.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

public class CaseLinraryNewActivity extends NavigationBarActivity implements AbsListView.OnScrollListener, View.OnClickListener {
    private ListView caseLibraryNew;
    private RelativeLayout rlThumbUp;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_case_linrary_new;
    }


    @Override
    protected void initView() {
        super.initView();
        rlThumbUp = (RelativeLayout) findViewById(R.id.rl_thumb_up);
        caseLibraryNew = (ListView) findViewById(R.id.case_library_new);
    }


    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }


    @Override
    protected void initListener() {
        super.initListener();
        rlThumbUp.setOnClickListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_thumb_up:
                sendThumbUp("1553716");
                break;
            default:
                break;
        }
    }


    /**
     * 发送点赞请求
     *
     * @param assetId
     */
    public void sendThumbUp(String assetId) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("yxw", jsonObject.toString());
//                String info = GsonUtil.jsonToString(jsonObject);
//                caseDetailBean = GsonUtil.jsonToBean(info, CaseDetailBean.class);
//                updateViewFromData();
            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {
               // int statusCode = volleyError.networkResponse.statusCode;
               // Log.d("yxw", "-----------"+statusCode);
//                MPNetworkUtils.logError(TAG, volleyError);
//                mLookMore.setVisibility(View.GONE);
//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, CaseLibraryDetailActivity.this,
//                        AlertView.Style.Alert, null).show();
            }
        };
        MPServerHttpManager.getInstance().sendThumbUpRequest(assetId, okResponseCallback);
    }
}
