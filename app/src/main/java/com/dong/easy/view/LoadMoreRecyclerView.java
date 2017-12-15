package com.dong.easy.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by zengwendong on 16/7/79.
 * 上拉加载更多  功能
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private boolean isEnableFooter = true;//是否启用footer

    /**
     * 标记是否正在加载更多，防止再次调用加载更多接口
     */
    private boolean isLoadingMore = false;
    /**
     * 标记加载更多的position
     */
    //private int mLoadMorePosition;
    /**
     * 加载更多的监听-业务需要实现加载数据
     */
    private LoadMoreListener mListener;
    /**
     * 条目 位置改变监听
     */
    private ItemPositionChangeListener itemPositionChangeListener;

    public LoadMoreRecyclerView(Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setEnableFooter(boolean enableFooter) {
        isEnableFooter = enableFooter;
    }

    private void init() {
        super.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (getAdapter() == null) {
                    return;
                }
                int lastVisiblePosition = getLastVisiblePosition();
                int firstVisiblePosition = getFirstVisiblePosition();
                if (null != mListener && isEnableFooter && !isLoadingMore && newState == RecyclerView.SCROLL_STATE_IDLE) {

                    if (lastVisiblePosition + 1 == getAdapter().getItemCount()) {
                        isLoadingMore = true;//设置正在加载
                        //mLoadMorePosition = lastVisiblePosition;
                        mListener.onLoadMore();
                    }
                }
                if (itemPositionChangeListener != null) {
                    itemPositionChangeListener.positionChanged(lastVisiblePosition,firstVisiblePosition);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onViewScrollListener != null) {
                    onViewScrollListener.onScroll(recyclerView, getFirstVisiblePosition(), dx, dy);
                }

            }
        });
    }

    private OnViewScrollListener onViewScrollListener;

    public void setOnViewScrollListener(OnViewScrollListener onViewScrollListener) {
        this.onViewScrollListener = onViewScrollListener;
    }

    public interface OnViewScrollListener {
        void onScroll(RecyclerView recyclerView, int firstVisiblePosition, int dx, int dy);
    }

    /**
     * 设置加载更多的监听
     */
    public void setLoadMoreListener(LoadMoreListener listener) {
        mListener = listener;
    }

    public void setItemPositionChangeListener(ItemPositionChangeListener itemPositionChangeListener) {
        this.itemPositionChangeListener = itemPositionChangeListener;
    }

    /**
     * 获取第一条展示的位置
     */
    private int getFirstVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findFirstVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMinPositions(lastPositions);
        } else {
            position = 0;
        }
        return position;
    }

    /**
     * 获得当前展示最小的position
     *
     */
    private int getMinPositions(int[] positions) {
        int size = positions.length;
        int minPosition = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            minPosition = Math.min(minPosition, positions[i]);
        }
        return minPosition;
    }

    /**
     * 获取最后一条展示的位置
     *
     */
    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    /**
     * 通知更多的数据已经加载
     *
     * @param hasMore  true 表示有更多数据
     */
    public void notifyMoreFinish(boolean hasMore) {
        setEnableFooter(hasMore);
        isLoadingMore = false;//设置加载完成
    }

    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    /**
     * 加载更多监听
     */
    public interface LoadMoreListener {
        void onLoadMore();
    }

    /**
     * 条目滚动监听
     */
    public interface ItemPositionChangeListener {
        void positionChanged(int lastVisiblePosition, int firstVisiblePosition);
    }

}

