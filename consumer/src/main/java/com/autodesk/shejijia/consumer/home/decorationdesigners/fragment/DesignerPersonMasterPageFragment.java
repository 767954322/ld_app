package com.autodesk.shejijia.consumer.home.decorationdesigners.fragment;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerDetailAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerDetailHomeBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryNewActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date on 16-8-9
 * @file DesignerPersonMasterPageFragment  .
 * @brief 查看设计师2D Case .
 */
public class DesignerPersonMasterPageFragment extends BaseFragment {

    public DesignerPersonMasterPageFragment() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_designer_person_master_page;
    }

    @Override
    protected void initView() {
        mFooterView = View.inflate(getActivity(), R.layout.view_empty_layout, null);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
        //   mPullToRefreshLayout = ((PullToRefreshLayout)rootView.findViewById(R.id.refresh_view));
        mListView = (ListView) rootView.findViewById(R.id.lv_seek_detail_listview);
    }

    @Override
    protected void initData() {
    }

    public void getMore2DCaseData(SeekDesignerDetailBean mSeekDesignerDetailBean, int state) {
        this.mSeekDesignerDetailBean = mSeekDesignerDetailBean;
        updateViewFromDesignerData(state);
    }

    public void setHandler(android.os.Handler handler){
        this.myHandler = handler;

    }


    /**
     * 设计师的信息更新
     *
     * @param state 　更新状态
     */
    private void updateViewFromDesignerData(int state) {
        //如果是刷新数据，就将该集合清空
        if (state == 0) {
            mCasesEntityArrayList.clear();
        }

        mCasesEntityArrayList.addAll(mSeekDesignerDetailBean.getCases());
        if (mCasesEntityArrayList.size() < 1) {
           // getEmptyAlertView(UIUtils.getString(R.string.case_is_empty)).show();
        }
//        hideFooterView(mCasesEntityArrayList);
        if (mSeekDesignerDetailAdapter == null) {

            mSeekDesignerDetailAdapter = new SeekDesignerDetailAdapter(getActivity(), mCasesEntityArrayList, getActivity());
            mListView.setAdapter(mSeekDesignerDetailAdapter);

        } else {
            mSeekDesignerDetailAdapter.addMoreData(mCasesEntityArrayList);
            mSeekDesignerDetailAdapter.notifyDataSetChanged();

        }
        mSeekDesignerDetailAdapter.setOnItemCaseLibraryClickListener(new SeekDesignerDetailAdapter.OnItemCaseLibraryClickListener() {
            @Override
            public void OnItemCaseLibraryClick(int position) {
                /**
                 * 单击某个item进入查看详情
                 *
                 * @param position item的位置
                 */
                String case_id = mCasesEntityArrayList.get(position).getId();
                Intent intent = new Intent(getActivity(), CaseLibraryNewActivity.class);
                intent.putExtra(Constant.CaseLibraryDetail.CASE_ID, case_id);
                startActivity(intent);
            }
        });
//        setListViewHeightBasedOnChildren(mListView);

        Message message = myHandler.obtainMessage();
        message.what = 0;
        myHandler.sendMessage(message);
    }

    /**
     * 案例库为空时候显示的提示框
     */
    private AlertView getEmptyAlertView(String content) {
        return new AlertView(UIUtils.getString(R.string.tip), content, null, null, null, getActivity(),
                AlertView.Style.Alert, null).setCancelable(true);
    }

    /**
     * 计算listView的高
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) {
            return;
        }


        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;


        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {


            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);


            if (listItem != null) {
                // 计算子项View 的宽高
                listItem.measure(0, 0);
                // 统计所有子项的总高度
                totalHeight += listItem.getMeasuredHeight();
            }
        }


        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);

    }


    private LinearLayout view_refresh_head;
    private PullToRefreshLayout refresh_view;
    private PullListView lv_seek_detail_listview;
    private RelativeLayout view_load_more;
    private View mFooterView;
    private RelativeLayout mRlEmpty;
    private TextView mTvEmptyMessage;
    private int LIMIT = 10;
    private int OFFSET = 0;
    private int width;
    private android.os.Handler myHandler;
    private ArrayList<SeekDesignerDetailBean.CasesEntity> mCasesEntityArrayList = new ArrayList<>();
    private SeekDesignerDetailAdapter mSeekDesignerDetailAdapter;
    private ListView mListView;
    private SeekDesignerDetailBean mSeekDesignerDetailBean;
    private DesignerDetailHomeBean seekDesignerDetailHomeBean;
    private PullToRefreshLayout mPullToRefreshLayout;
    private String mMemberType, mDesignerId, mHsUid;


}
