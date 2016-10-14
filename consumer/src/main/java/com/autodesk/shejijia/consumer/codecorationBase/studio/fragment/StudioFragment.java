package com.autodesk.shejijia.consumer.codecorationBase.studio.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
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
import com.autodesk.shejijia.consumer.utils.ApiStatusUtil;
import com.autodesk.shejijia.consumer.utils.WkFlowStateMap;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.consumer.uielements.AnimationUtils;
import com.autodesk.shejijia.consumer.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.ImageUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author .
 * @version 1.0 .
 * @date 16-8-16
 * @file StudioFragment.java  .
 * @brief 六大产品-工作室 .
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

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                    case 0:
                        if (now_order != null) {

                            AnimationUtils.getInstance().clearAnimationControl(now_order);
                            AnimationUtils.getInstance().setAnimationShow(now_order);
                        }
                        break;
                }
            }
        };

        //首次获取数据
        getWorkRoomData("91", 0, 25);
        if (WkFlowStateMap.sixProductsPicturesBean != null) {

            String pictureName = WkFlowStateMap.sixProductsPicturesBean.getAndroid().getStudio().get(0).getBanner();
            ImageUtils.loadImageIcon(img_header, pictureName);
        }
    }

    @Override
    public void onDestroyView()
    {
        if(work_room_refresh_view != null)
            work_room_refresh_view.setOnRefreshListener(null);
        super.onDestroyView();
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
                                jsonObject.put(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_NAME, name);//姓名
                                jsonObject.put(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_MOBILE, phoneNumber);//电话
                                jsonObject.put(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_TYPE, 2);//工作室类型
                                jsonObject.put(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_ID, member_id);//消费者ID
                                jsonObject.put(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_UID, hs_uid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                            try {
                                jsonObject.put(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_NAME, name);
                                jsonObject.put(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_MOBILE, phoneNumber);
                                jsonObject.put(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_TYPE, 2);
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
                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
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
                        //清空原来的数据
                        if (designerRetrieveRspAll != null && designerRetrieveRspAll.size() != 0) {

                            designerRetrieveRspAll.clear();
                        }
                        for (int i = 0; i < designerListBeen.size(); i++) {

                            designerRetrieveRspAll.add(designerListBeen.get(i));

                        }
                        upDataForView(designerRetrieveRspAll);
                        work_room_refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED);

                    }
                }

                if (isFirst) {

                    controlAnimation();//加载或者刷新更多时候也要消失按钮,完成后,慢慢显现
                }
                isFirst = true;
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

                if (position > 0) {

                    Intent intent = new Intent(activity, WorkRoomDetailActivity.class);

                    intent.putExtra(Constant.workRoomListFragment.WORK_ROOM_LIST_CONSUMER_HS_UID, designerRetrieveRspAll.get(position - 1).getHs_uid());
                    activity.startActivity(intent);

                }
            }
        });

        work_room_listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                                   @Override
                                                   public void onScrollStateChanged(AbsListView view, int scrollState) {

                                                       switch (scrollState) {


                                                           case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://当屏幕停止滚动时

                                                               controlAnimation();

                                                               break;
                                                           case AbsListView.OnScrollListener.SCROLL_STATE_FLING://惯性滑动时


                                                               AnimationUtils.getInstance().setAnimationDismiss(now_order);

                                                               break;
                                                           case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://手指触摸滑动时

                                                               AnimationUtils.getInstance().setAnimationDismiss(now_order);

                                                               break;

                                                       }

                                                   }

                                                   @Override
                                                   public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


//                                                       //滑动到底部的时候
//                                                       if (work_room_listView.getLastVisiblePosition() == work_room_listView.getCount() - 1) {
//
//
//                                                           controlAnimation();
//                                                       }
//                                                       //滑动到顶部了
//                                                       if (work_room_listView.getFirstVisiblePosition() == 0) {
//
//
//                                                           controlAnimation();
//
//                                                       }

                                                   }

                                               }

        );


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private int LIMIT = 10;

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        OFFSET = 10;
        isLoadMore = false;
        getWorkRoomData("91", 0, 25);
        AnimationUtils.getInstance().setAnimationDismiss(now_order);
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

        isLoadMore = true;
        OFFSET = OFFSET + 25;
        getWorkRoomData("91", OFFSET, LIMIT);
        AnimationUtils.getInstance().setAnimationDismiss(now_order);

    }


    /**
     * 上传立即预约信息
     */
    public void upOrderDataForService(JSONObject jsonObject) {


        MPServerHttpManager.getInstance().upWorkRoomOrderData(jsonObject, new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                ApiStatusUtil.getInstance().apiStatuError(volleyError, getActivity());
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

    //延时一段时间显现按钮
    public void controlAnimation() {

        final TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Message message = handler.obtainMessage();
                message.what = 0;
                handler.sendMessage(message);
                if (timer != null) {

                    timer.cancel();
                    timer = null;
                }
            }
        };
        if (timer == null) {

            timer = new Timer();
            timer.schedule(timerTask, 1500, 5000);
        }

    }


    private ListView work_room_listView;
    private View viewHead;
    private TextView now_order;
    private ImageView img_header;
    private Handler handler;
    private Timer timer;

    private PullToRefreshLayout work_room_refresh_view;
    private WorkRoomListBeen workRoomListBeen;
    private List<WorkRoomListBeen.DesignerListBean> designerListBeen;
    private List<WorkRoomListBeen.DesignerListBean> designerRetrieveRspAll = new ArrayList<WorkRoomListBeen.DesignerListBean>();

    private WorkRoomAdapter workRoomAdapter;
    private String str = "workroom";
    private int OFFSET = 0;
    private boolean isLoginUserJust = false;
    private boolean isLoadMore = false;
    private boolean isFirst = false;
}
