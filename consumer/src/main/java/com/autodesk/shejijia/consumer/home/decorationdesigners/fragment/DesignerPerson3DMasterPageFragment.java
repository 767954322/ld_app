package com.autodesk.shejijia.consumer.home.decorationdesigners.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesigner3DCaseAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.Case3DBeen;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryDetail3DActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.appglobal.MemberEntity;
import com.autodesk.shejijia.shared.components.common.uielements.alertview.AlertView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
import com.autodesk.shejijia.shared.components.common.uielements.scrollview.ScrollViewListView;
import com.autodesk.shejijia.shared.components.common.utility.UIUtils;
import com.autodesk.shejijia.shared.framework.AdskApplication;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date on 16-8-9
 * @file DesignerPersonMasterPageFragment  .
 * @brief 查看设计师2D Case .
 */
public class DesignerPerson3DMasterPageFragment extends BaseFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_designer_person_master_page;
    }

    @Override
    protected void initView() {

        mFooterView = View.inflate(getActivity(), R.layout.view_empty_layout, null);
        mRlEmpty = (RelativeLayout) mFooterView.findViewById(R.id.rl_empty);
        mTvEmptyMessage = (TextView) mFooterView.findViewById(R.id.tv_empty_message);
        mListView = (ScrollViewListView) rootView.findViewById(R.id.lv_seek_detail_listview);


    }

    @Override
    protected void initData() {

    }

    //设置handler
    public void setHandler(Handler hanler){

        this.myHanler = hanler;

    }


    //获取更多数据
    public void getMore3DCase(final List<Case3DBeen.CasesBean> myData, int state) {

        //如果是刷新数据，就将该集合清空
        if (state == 0) {
            if (seekDesigner3DCaseAdapter == null){

                seekDesigner3DCaseAdapter = new SeekDesigner3DCaseAdapter(getActivity(), myData);
            }
            myDatas.clear();
            myDatas.addAll(myData);

            mListView.setAdapter(seekDesigner3DCaseAdapter);
        } else {

            myDatas.addAll(myData);
            if (myDatas !=null && myDatas.size()>0){

                seekDesigner3DCaseAdapter.addMoreData(myDatas);
                seekDesigner3DCaseAdapter.notifyDataSetChanged();
            }

        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String hs_design_id = (String) myData.get(position).getHs_design_id();
                String designer_id = myData.get(position).getDesigner_id()+"";
                MemberEntity mMemberEntity = AdskApplication.getInstance().getMemberEntity();

                /**
                 *
                 hs_desgen_id 如果是空的
                 degen_id  是不是当前登陆用户
                 是       方案尚未完成，请至网页端完善设计
                 不是     方案还在设计中，请浏览其他3D方案
                 */
                if (hs_design_id==null){

                    if (mMemberEntity!=null){
                        String acs_member_id = mMemberEntity.getAcs_member_id();
                        if (designer_id.equals(acs_member_id)){
                            // 方案尚未完成，请至网页端完善设计
                            showAlertView("方案尚未完成，请至网页端完善设计");
                        }else {
                            showAlertView("方案还在设计中，请浏览其他3D方案");
                            //方案还在设计中，请浏览其他3D方案
                        }
                    }else {
                        showAlertView("方案还在设计中，请浏览其他3D方案");
                    }

                }else {
                    Intent intent = new Intent(getActivity(), CaseLibraryDetail3DActivity.class);
                    intent.putExtra(Constant.CaseLibraryDetail.CASE_ID, myDatas.get(position).getDesign_asset_id()+"");
                    getActivity().startActivity(intent);
                }


            }
        });

        Message message = myHanler.obtainMessage();
        message.what = 0;
        myHanler.sendMessage(message);

        updateViewFromDesignerData(state);

    }

    //打开AlertView对话框
    private void showAlertView(String content) {
        new AlertView(UIUtils.getString(R.string.tip), content, null, null, new String[]{UIUtils.getString(R.string.sure)}, getActivity(), AlertView.Style.Alert, null).show();
    }

    /**
     * @param state
     */
    public void updateViewFromDesignerData(int state) {


    }


    private LinearLayout view_refresh_head;
    private PullToRefreshLayout refresh_view;
    private PullListView lv_seek_detail_listview;
    private RelativeLayout view_load_more;
    private View mFooterView;
    private Case3DBeen case3DBeen;
    private RelativeLayout mRlEmpty;
    private TextView mTvEmptyMessage;
    private int LIMIT = 10;
    private int OFFSET = 0;
    private ListView mListView;
    private Handler myHanler;
    private List<Case3DBeen.CasesBean> myDatas = new ArrayList<Case3DBeen.CasesBean>();
    private SeekDesigner3DCaseAdapter seekDesigner3DCaseAdapter;


}
