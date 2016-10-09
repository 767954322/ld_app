package com.autodesk.shejijia.enterprise.nodeprocess.projectlists.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.VolleyError;
import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.enterprise.base.fragments.BaseFragment;
import com.autodesk.shejijia.enterprise.base.network.EnterpriseServerHttpManager;
import com.autodesk.shejijia.enterprise.common.utils.Constants;
import com.autodesk.shejijia.enterprise.nodeprocess.projectlists.adapter.TaskListAdapter;
import com.autodesk.shejijia.enterprise.nodeprocess.projectlists.entity.TaskListBean;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.network.OkJsonRequest;
import com.autodesk.shejijia.shared.components.common.utility.GsonUtil;
import com.autodesk.shejijia.shared.components.common.utility.LogUtils;
import com.autodesk.shejijia.shared.components.common.utility.SharedPreferencesUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by t_xuz on 8/25/16.
 *
 */
public class TaskListFragment extends BaseFragment{

    private RecyclerView mTaskListView;
    private List<TaskListBean.TaskList> taskLists;
    private TaskListAdapter mTaskListAdapter;
    private MemberEntity entity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_list_view;
    }

    @Override
    protected void initData() {
        entity = (MemberEntity) SharedPreferencesUtils.getObject(mContext, Constants.USER_INFO);
        LogUtils.e("project--entity", entity + "");
        if (entity != null && !TextUtils.isEmpty(entity.getHs_accesstoken())) {
            LogUtils.e("acs_token", entity.getToken());
            //get TaskLists
            getTaskLists("2016-08-08",0,10,0,"587e1e6bd9c26875535868dec8e3045c");
        }
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTaskListView = (RecyclerView)mContext.findViewById(R.id.rcy_task_list);
        //init recyclerView
        mTaskListView.setLayoutManager(new LinearLayoutManager(mContext));
        mTaskListView.setHasFixedSize(true);
        mTaskListView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initEvents() {
 /*       mTaskListView.addOnItemTouchListener(new OnRecyclerItemClickListener(mTaskListView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                ToastUtils.showShort(mContext, position + "");
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });*/
    }

    private void getTaskLists(final String findDate,
                              final int like,
                              final int limit,
                              final int offset,
                              final String token) {
        OkJsonRequest.OKResponseCallback callback = new OkJsonRequest.OKResponseCallback() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtils.d(jsonObject+"");
                String result = jsonObject.toString();
                TaskListBean taskListBean = GsonUtil.jsonToBean(result, TaskListBean.class);

                //获取当前日期(默认就是当前日期)的任务列表
                taskLists = taskListBean.getData();
                if (taskLists!=null && taskLists.size()>0) {
                    //显示任务列表到页面上
                    mTaskListAdapter = new TaskListAdapter(taskLists, R.layout.listitem_task_list_view, mContext);
                    mTaskListView.setAdapter(mTaskListAdapter);
                }

            }
        };
        EnterpriseServerHttpManager.getInstance().getTaskLists(findDate, like, limit, offset, token, callback);
    }
}
