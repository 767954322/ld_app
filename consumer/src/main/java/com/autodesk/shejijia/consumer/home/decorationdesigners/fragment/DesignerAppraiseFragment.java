package com.autodesk.shejijia.consumer.home.decorationdesigners.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.home.decorationdesigners.adapter.SeekDesignerAppraiseAdapter;
import com.autodesk.shejijia.consumer.home.decorationdesigners.entity.AppraiseDesignBeen;
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
        mListView.getLastVisiblePosition();
    }

    @Override
    protected void initData() {
//        getEstimateList(String designer_id, int limit, int offset, OkJsonRequest.OKResponseCallback callback) {

//            getEstimateList();

    }
//展示数据
    public void updateListView(List<AppraiseDesignBeen.EstimatesBean> estimates) {
        mSeekDesignerAppraiseAdapter = new SeekDesignerAppraiseAdapter(getActivity(), estimates);
        estimatesList.clear();
        estimatesList.addAll(estimates);
        mListView.setAdapter(mSeekDesignerAppraiseAdapter);
        setListViewHeightBasedOnChildren(mListView);
    }

    //加载更多数据
    public void loadMoreData(List<AppraiseDesignBeen.EstimatesBean> estimates) {

        estimatesList.addAll(estimates);
        mSeekDesignerAppraiseAdapter.addMoreData(estimatesList);
        setListViewHeightBasedOnChildren(mListView);
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

    private ListView mListView;
    private AppraiseDesignBeen mAppraiseDesignBeen;
    private List<AppraiseDesignBeen.EstimatesBean> estimates;
    private SeekDesignerAppraiseAdapter mSeekDesignerAppraiseAdapter;
    private String designerId;
    private List<AppraiseDesignBeen.EstimatesBean> estimatesList = new ArrayList<AppraiseDesignBeen.EstimatesBean>();
}
