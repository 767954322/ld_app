package com.autodesk.shejijia.consumer.home.decorationdesigners.fragment;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerAppraiseAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.AppraiseDesignBeen;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.DesignerDetailHomeBean;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxuehua .
 * @version 1.0 .
 * @date on 16-8-9
 * @file DesignerAppraiseFragment  .
 * @brief 查看设计师appraiseFragment评价.
 */
public class DesignerAppraiseFragment extends BaseFragment {

    public DesignerAppraiseFragment() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_designer_appraise;
    }

    @Override
    protected void initView() {

        mListView = (ListView) rootView.findViewById(R.id.lv_seek_appraise_detail_listview);
        rating_star = (RatingBar) rootView.findViewById(R.id.rating_star);
        appraise_ll = (LinearLayout) rootView.findViewById(R.id.appraise_ll);
        mListView.getLastVisiblePosition();
    }

    @Override
    protected void initData() {

    }
//展示数据
    public void updateListView(List<AppraiseDesignBeen.EstimatesBean> estimates,DesignerDetailHomeBean seekDesignerDetailHomeBean) {
        mSeekDesignerAppraiseAdapter = new SeekDesignerAppraiseAdapter(getActivity(), estimates);
        estimatesList.clear();
        estimatesList.addAll(estimates);
        mListView.setAdapter(mSeekDesignerAppraiseAdapter);

        if (seekDesignerDetailHomeBean != null && seekDesignerDetailHomeBean.getDesigner() != null){
            //综合评分
            rating_star.setRating(Float.parseFloat(seekDesignerDetailHomeBean.getDesigner().getEvalution_avg_scores()));
            if (seekDesignerDetailHomeBean.getDesigner().getEvalution_avg_scores() != null){

                appraise_ll.setVisibility(View.VISIBLE);
            }
        }


    }

    //加载更多数据
    public void loadMoreData(List<AppraiseDesignBeen.EstimatesBean> estimates) {

        estimatesList.addAll(estimates);
        mSeekDesignerAppraiseAdapter.addMoreData(estimatesList);
    }

    private ListView mListView;
    private AppraiseDesignBeen mAppraiseDesignBeen;
    private RatingBar rating_star;
    private LinearLayout appraise_ll;
    private List<AppraiseDesignBeen.EstimatesBean> estimates;
    private SeekDesignerAppraiseAdapter mSeekDesignerAppraiseAdapter;
    private String designerId;
    private List<AppraiseDesignBeen.EstimatesBean> estimatesList = new ArrayList<AppraiseDesignBeen.EstimatesBean>();
}
