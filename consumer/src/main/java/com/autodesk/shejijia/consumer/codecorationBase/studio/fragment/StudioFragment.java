package com.autodesk.shejijia.consumer.codecorationBase.studio.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.studio.adapter.WorkRoomAdapter;
import com.autodesk.shejijia.consumer.codecorationBase.studio.dialog.OrderDialog;
import com.autodesk.shejijia.consumer.codecorationBase.studio.entity.WorkRoomListBeen;
import com.autodesk.shejijia.consumer.codecorationBase.studio.activity.WorkRoomDetailActivity;
import com.autodesk.shejijia.consumer.manager.MPServerHttpManager;
import com.autodesk.shejijia.consumer.personalcenter.workflow.activity.FlowEstablishContractActivity;
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作室
 */
public class StudioFragment extends BaseFragment implements View.OnClickListener, PullToRefreshLayout.OnRefreshListener {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_studio;
    }

    @Override
    protected void initView() {

        work_room_refresh_view = (PullToRefreshLayout) rootView.findViewById(R.id.work_room_refresh_view);
        work_room_listView = (ListView) rootView.findViewById(R.id.work_room_listView);
        now_order = (TextView) rootView.findViewById(R.id.now_order);
        viewHead = LayoutInflater.from(activity).inflate(R.layout.fragment_work_room, null);
        img_header = (ImageView) viewHead.findViewById(R.id.img_header);

        work_room_listView.addHeaderView(viewHead);
    }

    @Override
    protected void initListener() {
        super.initListener();
        now_order.setOnClickListener(this);
        work_room_refresh_view.setOnRefreshListener(this);

    }

    @Override
    protected void initData() {


        isLoginUserJust = isLoginUser();
        getWorkRoomData("91", 0, 10);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.now_order:
                //工作室用户信息弹框
                OrderDialog orderDialog = new OrderDialog(activity, R.style.add_dialog);
                orderDialog.setListenser(new OrderDialog.CommitListenser() {
                    @Override
                    public void commitListener(String name, String phoneNumber) {

                        JSONObject jsonObject = new JSONObject();
                        if (isLoginUserJust) {
                            String member_id = AdskApplication.getInstance().getMemberEntity().getAcs_member_id();
                            String hs_uid = AdskApplication.getInstance().getMemberEntity().getHs_uid();
                            try {
                                jsonObject.put("consumer_name", name);//姓名
                                jsonObject.put("consumer_mobile", phoneNumber);//电话
                                jsonObject.put("type", 2);//工作室类型
                                jsonObject.put("customer_id", member_id);//消费者ID
                                jsonObject.put("consumer_uid", hs_uid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            try {
                                jsonObject.put("consumer_name", name);
                                jsonObject.put("consumer_mobile", phoneNumber);
                                jsonObject.put("type", 2);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        upOrderDataForService(jsonObject);
                    }
                });
                orderDialog.show();
                break;

        }

    }
    //获取工作室数据

    public void getWorkRoomData(String type, int offset, int limit) {

        MPServerHttpManager.getInstance().getWorkRoomList(new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                new AlertView(UIUtils.getString(R.string.tip), UIUtils.getString(R.string.network_error), null, new String[]{UIUtils.getString(R.string.sure)}, null, getActivity(),
//                        AlertView.Style.Alert, null).show();
                work_room_refresh_view.loadmoreFinish(PullToRefreshLayout.FAIL);
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                String info = GsonUtil.jsonToString(jsonObject);


                workRoomListBeen = GsonUtil.jsonToBean(info, WorkRoomListBeen.class);
                designerListBeen = workRoomListBeen.getDesigner_list();
                if (designerListBeen != null) {

                    if (isLoadMore) {
                        for (int i = 0; i < designerListBeen.size(); i++) {

                            designerRetrieveRspAll.add(designerListBeen.get(i));

                        }
                        workRoomAdapter.addMoreData(designerRetrieveRspAll);
                        work_room_refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        isLoadMore = false;
                    } else {
                        for (int i = 0; i < designerListBeen.size(); i++) {

                            designerRetrieveRspAll.add(designerListBeen.get(i));

                        }
                        upDataForView(designerRetrieveRspAll);

                    }
                }
                work_room_refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        }, type, offset, limit);
    }

    //给控件赋值
    public void upDataForView(final List<WorkRoomListBeen.DesignerListBean> designerListBeenList) {

        workRoomAdapter = new WorkRoomAdapter(activity, designerListBeenList);
        work_room_listView.setAdapter(workRoomAdapter);
        work_room_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position >0){

                    Intent intent = new Intent(activity, WorkRoomDetailActivity.class);

                    intent.putExtra("hs_uid", designerRetrieveRspAll.get(position - 1).getHs_uid());
                    activity.startActivity(intent);

                }
            }
        });

    }

    private int LIMIT = 10;

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        isLoadMore = true;
        OFFSET = OFFSET + 10;
        getWorkRoomData("91", OFFSET, LIMIT);

    }


    /**
     * 上传立即预约信息
     */
    public void upOrderDataForService(JSONObject jsonObject) {



        MPServerHttpManager.getInstance().upWorkRoomOrderData(jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                ApiStatusUtil.getInstance().apiStatuError(volleyError,getActivity());
                Toast.makeText(activity, R.string.work_room_commit_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject jsonObject) {

                Toast.makeText(activity, R.string.work_room_commit_successful, Toast.LENGTH_SHORT).show();
            }
        });

    }

    //判断该用户是否登陆了
    public boolean isLoginUser() {

        MemberEntity memberEntity = AdskApplication.getInstance().getMemberEntity();

        if (memberEntity == null) {

            return false;//未登录
        } else {

            return true;//已登录
        }


    }


    private ListView work_room_listView;
    private View viewHead;
    private TextView now_order;
    private ImageView img_header;

    private PullToRefreshLayout work_room_refresh_view;
    private WorkRoomListBeen workRoomListBeen;
    private List<WorkRoomListBeen.DesignerListBean> designerListBeen;
    private List<WorkRoomListBeen.DesignerListBean> designerRetrieveRspAll = new ArrayList<WorkRoomListBeen.DesignerListBean>();

    private WorkRoomAdapter workRoomAdapter;
    private String str = "workroom";
    private int OFFSET = 0;
    private boolean isLoginUserJust = false;
    private boolean isLoadMore = false;
}
