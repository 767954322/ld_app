package com.autodesk.shejijia.consumer.home.decorationdesigners.fragment;

import android.os.Handler;
import android.os.Message;
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
import com.autodesk.shejijia.shared.components.common.uielements.scrollview.ScrollViewListView;
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

        mListView = (ScrollViewListView) rootView.findViewById(R.id.lv_seek_appraise_detail_listview);
        rating_star = (RatingBar) rootView.findViewById(R.id.rating_star);
        appraise_ll = (LinearLayout) rootView.findViewById(R.id.appraise_ll);
        mListView.getLastVisiblePosition();
    }

    @Override
    protected void initData() {

    }

    //展示数据
    public void updateListView(List<AppraiseDesignBeen.EstimatesBean> estimates, DesignerDetailHomeBean seekDesignerDetailHomeBean) {
        if (mSeekDesignerAppraiseAdapter == null) {

            mSeekDesignerAppraiseAdapter = new SeekDesignerAppraiseAdapter(getActivity(), estimates);
        }
        estimatesList.clear();
        estimatesList.addAll(estimates);
        mListView.setAdapter(mSeekDesignerAppraiseAdapter);



        if (seekDesignerDetailHomeBean != null && seekDesignerDetailHomeBean.getDesigner() != null &&

                seekDesignerDetailHomeBean.getDesigner().getEvalution_avg_scores() != null) {

            rating_star.setRating(Float.parseFloat(seekDesignerDetailHomeBean.getDesigner().getEvalution_avg_scores()));

        }

        if (estimates.size() > 0) {

            appraise_ll.setVisibility(View.VISIBLE);
        }

        Message message = myHandler.obtainMessage();
        message.what = 0;
        myHandler.sendMessage(message);


    }

    //加载更多数据
    public void loadMoreData(List<AppraiseDesignBeen.EstimatesBean> estimates) {
        if (estimates != null && estimates.size() > 0) {

            estimatesList.addAll(estimates);
            mSeekDesignerAppraiseAdapter.addMoreData(estimatesList);
            mSeekDesignerAppraiseAdapter.notifyDataSetChanged();
        }

        Message message = myHandler.obtainMessage();
        message.what = 0;
        myHandler.sendMessage(message);

    }
    //设置handler
    public void setHandler(Handler handler){

        this.myHandler = handler;

    }



    private ListView mListView;
    private AppraiseDesignBeen mAppraiseDesignBeen;
    private RatingBar rating_star;
    private LinearLayout appraise_ll;
    private List<AppraiseDesignBeen.EstimatesBean> estimates;
    private SeekDesignerAppraiseAdapter mSeekDesignerAppraiseAdapter;
    private String designerId;
    private List<AppraiseDesignBeen.EstimatesBean> estimatesList = new ArrayList<AppraiseDesignBeen.EstimatesBean>();

    private Handler myHandler;
}
