package com.autodesk.shejijia.consumer.home.decorationdesigners.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesiger3DCaseAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerDetailAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.Case3DBeen;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerDetailHomeBean;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.SeekDesignerDetailBean;
import com.autodesk.shejijia.consumer.home.decorationlibrarys.activity.CaseLibraryDetail3DActivity;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullListView;
import com.autodesk.shejijia.shared.components.common.uielements.pulltorefresh.PullToRefreshLayout;
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
        mListView = (ListView) rootView.findViewById(R.id.lv_seek_detail_listview);


    }

    @Override
    protected void initData() {

    }




    //获取更多数据
    public void getMore3DCase(Case3DBeen case3DBeen, int state) {
        this.case3DBeen = case3DBeen;
        datas = new ArrayList<Case3DBeen.CasesBean>();


        //如果是刷新数据，就将该集合清空
        if (state == 0) {
            datas.clear();
            for (int i = 0; i < case3DBeen.getCases().size();i++){
                datas.add(case3DBeen.getCases().get(i));

            }
            seekDesigner3DCaseAdapter = new SeekDesiger3DCaseAdapter(getActivity(),datas);
            mListView.setAdapter(seekDesigner3DCaseAdapter);
        }else {

            for (int i = 0; i < case3DBeen.getCases().size();i++){
                datas.add(case3DBeen.getCases().get(i));

            }
            seekDesigner3DCaseAdapter.addMoreData(datas);

        }

        Log.i("yaoxuehua",""+datas.size());

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),CaseLibraryDetail3DActivity.class);
                intent.putExtra(Constant.CaseLibraryDetail.CASE_ID, datas.get(position).getDesign_asset_id());
                getActivity().startActivity(intent);

            }
        });


        updateViewFromDesignerData(state);

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
    private List<Case3DBeen.CasesBean> datas;
    private SeekDesiger3DCaseAdapter seekDesigner3DCaseAdapter;


}
