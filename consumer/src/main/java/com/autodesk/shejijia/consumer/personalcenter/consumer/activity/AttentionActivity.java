package com.autodesk.shejijia.consumer.personalcenter.consumer.activity;


import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.consumer.adapter.AttentionAdapter;
import com.autodesk.shejijia.consumer.personalcenter.consumer.entity.AttentionEntity;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.ListViewForScrollView;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.activity.NavigationBarActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttentionActivity extends NavigationBarActivity {



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_attention;
    }

    @Override
    protected void initView() {
        super.initView();
        lv_attention = (ListView) findViewById(R.id.lv_attention);
    }

    @Override
    protected void initExtraBundle() {
        super.initExtraBundle();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setTitleForNavbar(UIUtils.getString(R.string.my_attention));

        attentionAdapter = new AttentionAdapter(this,attentionList);
        lv_attention.setAdapter(attentionAdapter);
        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();
        String acs_member_id = memberEntity.getAcs_member_id();
        deleteAttention(acs_member_id,0,100);
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    public void deleteAttention(String member_id, int limit, int offset) {
        OkJsonRequest.OKResponseCallback okResponseCallback = new OkJsonRequest.OKResponseCallback() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                String userInfo = GsonUtil.jsonToString(jsonObject);
                attentionEntity = GsonUtil.jsonToBean(userInfo, AttentionEntity.class);
                attentionList.addAll(attentionEntity.getDesigner_list());
                attentionAdapter.notifyDataSetChanged();

            }

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

        };
        MPServerHttpManager.getInstance().deleteAttention(member_id,limit,offset,okResponseCallback);
    }

    private AttentionEntity attentionEntity;
    private AttentionAdapter attentionAdapter;
    private ListView lv_attention;
    public ArrayList<AttentionEntity.DesignerListBean> attentionList = new ArrayList<>();

}
