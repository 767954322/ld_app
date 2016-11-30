package com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.autodesk.shejijia.shared.R;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.footerview.BaseFooterView;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.footerview.FooterViewListener;
import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.footerview.SimpleFooterView;

/**
 * Created by t_xuz on 11/24/16.
 * 带有下拉刷新与上拉加载更多的recyclerView
 */

public class SwipeRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener, FooterViewListener {

    private BaseFooterView mFooterView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    private boolean mIsLoadMoreEnable;
    private boolean mIsRefreshEnable;
    private boolean mIsLoadingMore;
    private int mLastVisiblePosition = 0;
    private RecyclerView.LayoutManager mLayoutManager;

    private DataObserver mDataObserver;
    private WrapperAdapter mWrapperAdapter;
    private RefreshLoadMoreListener mListener;

    public SwipeRecyclerView(Context context) {
        this(context, null);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttributes(context, attrs);

        mFooterView = new SimpleFooterView(getContext());
        View rootView = inflate(context, R.layout.layout_swipe_recyclerview, this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerViewOnScrollListener());
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwipeRecyclerView);
        if (attrs == null) {
            return;
        }
        try {
            mIsRefreshEnable = array.getBoolean(R.styleable.SwipeRecyclerView_refreshEnable, false);
            mIsLoadMoreEnable = array.getBoolean(R.styleable.SwipeRecyclerView_loadMoreEnable, false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            array.recycle();
        }
    }

    @Override
    public void onRefresh() {
        if (mListener != null) {
            mListener.onRefresh();
        }
    }

    /*
    * set real adapter
    * */
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        if (adapter != null) {
            if (mDataObserver == null) {
                mDataObserver = new DataObserver();
            }
            mWrapperAdapter = new WrapperAdapter(adapter, mIsLoadMoreEnable, mFooterView);
            mRecyclerView.setAdapter(mWrapperAdapter);
            adapter.registerAdapterDataObserver(mDataObserver);
            mDataObserver.onChanged();
        }
    }

    /*
    *set listener
    * */
    public void setRefreshLoadMoreListener(RefreshLoadMoreListener listener) {
        this.mListener = listener;
    }

    /*
    * 让调用者主动刷新一下
    * */
    public void setRefreshing(final boolean refreshing) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(refreshing);
                if (refreshing && !mIsLoadingMore && mListener != null) {
                    mListener.onRefresh();
                }
            }
        });
    }

    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }

    public boolean isLoadingMore() {
        return mIsLoadingMore;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.mIsLoadMoreEnable = loadMoreEnable;
    }

    public void setRefreshEnable(boolean refreshEnable) {
        this.mIsRefreshEnable = refreshEnable;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void setFooterView(BaseFooterView footerView) {
        if (footerView != null) {
            this.mFooterView = footerView;
        }
    }

    /**
     * refresh or load more completed
     */
    public void complete() {
        mSwipeRefreshLayout.setRefreshing(false);
        stopLoadingMore();
    }

    /*
    * stop load more
    * */
    public void stopLoadingMore() {
        mIsLoadingMore = false;
        if (mWrapperAdapter != null) {
            mWrapperAdapter.notifyItemRemoved(mWrapperAdapter.getItemCount());
        }
    }

    /*
    * 滑动到指定位置
    * */
    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onNetChange(String message) {
        if (mFooterView != null) {
            mFooterView.onNetChange(message);
        }
    }

    @Override
    public void onRefreshing() {
        if (mFooterView != null){
            mFooterView.onRefreshing();
        }
    }

    @Override
    public void onLoadingMore() {
        if (mFooterView != null) {
            mFooterView.onLoadingMore();
        }
    }

    @Override
    public void onNoMore(String message) {
        if (mFooterView != null) {
            mFooterView.onNoMore(message);
        }
    }

    @Override
    public void onError(String message) {
        if (mFooterView != null) {
            mFooterView.onError(message);
        }
    }


    private class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        RecyclerViewOnScrollListener() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!mIsLoadMoreEnable || isLoadingMore() || isRefreshing()) {
                return;
            }
            mLayoutManager = mRecyclerView.getLayoutManager();
            //get lastVisiblePosition
            if (mLayoutManager instanceof LinearLayoutManager) {
                mLastVisiblePosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
            } else if (mLayoutManager instanceof GridLayoutManager) {
                mLastVisiblePosition = ((GridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition();
            } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(into);
                mLastVisiblePosition = findMax(into);
            }

            int childCount = mWrapperAdapter == null ? 0 : mWrapperAdapter.getItemCount();
            if (childCount > 1 && mLastVisiblePosition == childCount - 1) {
                if (mListener != null) {
                    mIsLoadingMore = true;
                    mListener.onLoadMore();
                }
            }
        }

        private int findMax(int[] lastPositions) {
            int max = lastPositions[0];
            for (int value : lastPositions) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }
    }

    /**
     * a inner class used to monitor the dataSet change
     * <p>
     * because wrapperAdapter do not know when wrapperAdapter.mInnerAdapter
     * <p>
     * dataSet changed, these method are final
     */
    class DataObserver extends RecyclerView.AdapterDataObserver {

        @Override
        public void onChanged() {
            super.onChanged();
            mWrapperAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            mWrapperAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            mWrapperAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            mWrapperAdapter.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            mWrapperAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

    }

}
