package com.dong.easy.image.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class EndlessRecyclerAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private final RecyclerView.OnScrollListener scrollListener =
            new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    loadNextItemsIfNeeded(recyclerView);
                }
            };

    private boolean isLoading;
    private boolean isError;

    private LoaderCallbacks callbacks;
    private int loadingOffset = 0;

    public final boolean isLoading() {
        return isLoading;
    }

    public final boolean isError() {
        return isError;
    }

    public void setCallbacks(LoaderCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    public void setLoadingOffset(int loadingOffset) {
        this.loadingOffset = loadingOffset;
    }

    public void loadNextItems() {
        if (!isLoading && !isError && callbacks != null && callbacks.canLoadNextItems()) {
            isLoading = true;
            onLoadingStateChanged();
            callbacks.loadNextItems();
        }
    }

    public void reloadNextItemsIfError() {
        if (isError) {
            isError = false;
            onLoadingStateChanged();
            loadNextItems();
        }
    }

    public void onNextItemsLoaded() {
        if (isLoading) {
            isLoading = false;
            isError = false;
            onLoadingStateChanged();
        }
    }

    public void onNextItemsError() {
        if (isLoading) {
            isLoading = false;
            isError = true;
            onLoadingStateChanged();
        }
    }

    protected void onLoadingStateChanged() {
        // No-default-op
    }


    private void loadNextItemsIfNeeded(RecyclerView recyclerView) {
        if (!isLoading && !isError) {
            View lastVisibleChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
            int lastVisiblePos = recyclerView.getChildAdapterPosition(lastVisibleChild);
            int total = getItemCount();

            if (lastVisiblePos >= total - loadingOffset) {
                loadNextItems();
            }
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(scrollListener);
        loadNextItemsIfNeeded(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnScrollListener(scrollListener);
    }


    public interface LoaderCallbacks {
        boolean canLoadNextItems();

        void loadNextItems();
    }

}
