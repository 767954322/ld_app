package com.autodesk.shejijia.shared.components.im.fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.entity.ProjectInfo;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.RefreshLoadMoreListener;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.SwipeRecyclerView;
import com.autodesk.shejijia.shared.components.nodeprocess.ui.adapter.ProjectListAdapter;
import com.autodesk.shejijia.shared.framework.fragment.BaseConstructionFragment;

import java.util.ArrayList;

/**
 * Created by t_xuz on 8/25/16.
 * 施工主线－群聊列表
 */
public class GroupChatFragment extends BaseConstructionFragment implements RefreshLoadMoreListener{

    private SwipeRecyclerView mGroupChatListView;
    private TextView mEmptyView;

    public static GroupChatFragment newInstance() {
        return new GroupChatFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_groupchat_list_view;
    }

    @Override
    protected void initView() {
        mGroupChatListView = (SwipeRecyclerView)rootView.findViewById(R.id.rcy_groupChat_list);
        mEmptyView = (TextView)rootView.findViewById(R.id.tv_empty_message);
        //init recyclerView
        mGroupChatListView.getRecyclerView().setLayoutManager(new LinearLayoutManager(mContext));
        mGroupChatListView.getRecyclerView().setHasFixedSize(true);
        mGroupChatListView.getRecyclerView().setItemAnimator(new DefaultItemAnimator());
        mGroupChatListView.getSwipeRefreshLayout()
                .setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorPrimary));
        ProjectListAdapter mProjectListAdapter = new ProjectListAdapter(new ArrayList<ProjectInfo>(0), R.layout.listitem_task_list_view, activity,null);
        mGroupChatListView.setAdapter(mProjectListAdapter);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initData() {
//        mGroupChatListView.setRefreshing(true);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chat_hotspot_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.menu.chat_hotspot_menu){
            // TODO: 12/5/16 跳转到热点聊天列表
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
