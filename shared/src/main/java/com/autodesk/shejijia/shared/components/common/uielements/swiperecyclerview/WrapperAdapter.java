package com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.autodesk.shejijia.shared.components.common.uielements.swiperecyclerview.footerview.BaseFooterView;

/**
 * Created by t_xuz on 11/24/16.
 * 包裹recyclerView 真实adapter用的
 */

public class WrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 0X23;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;
    private boolean isLoadMoreEnable;
    private BaseFooterView mFooterView;
    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    public WrapperAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, boolean loadMoreEnable, BaseFooterView footerView) {
        this.mInnerAdapter = adapter;
        this.isLoadMoreEnable = loadMoreEnable;
        this.mFooterView = footerView;
    }

    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FooterVH(mFooterView);
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isLoadMoreItem(position)) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int count = mInnerAdapter == null ? 0 : mInnerAdapter.getItemCount();

        //without loadingMore when adapter size is zero
        if (count == 0) {
            return 0;
        }
        return isLoadMoreEnable ? count + 1 : count;
    }

    @Override
    public long getItemId(int position) {
        return mInnerAdapter.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMoreItem(position)) {
            return TYPE_FOOTER;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    private boolean isLoadMoreItem(int position) {
        return isLoadMoreEnable && position == getItemCount() - 1;
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && isLoadMoreItem(holder.getLayoutPosition())) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
        mInnerAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isLoadMore = isLoadMoreItem(position);
                    if (mSpanSizeLookup != null && !isLoadMore) {
                        return mSpanSizeLookup.getSpanSize(position);
                    }
                    return isLoadMore ? gridManager.getSpanCount() : 1;
                }
            });
        }
        mInnerAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mInnerAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return mInnerAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mInnerAdapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        mInnerAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewRecycled(holder);
    }

    private static class FooterVH extends RecyclerView.ViewHolder {
        FooterVH(View itemView) {
            super(itemView);
        }
    }
}
