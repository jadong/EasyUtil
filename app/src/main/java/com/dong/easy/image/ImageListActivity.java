package com.dong.easy.image;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.internal.FloatScroller;
import com.alexvasilkov.gestures.transition.SimpleViewsTracker;
import com.alexvasilkov.gestures.transition.ViewsCoordinator;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.ViewsTransitionBuilder;
import com.dong.easy.R;
import com.dong.easy.base.BaseActivity;
import com.dong.easy.image.adapter.CCStaggeredGridLayoutManager;
import com.dong.easy.image.adapter.ImageListAdapter;
import com.dong.easy.image.adapter.ImagePagerAdapter;
import com.dong.easy.image.data.ImageDataResult;
import com.dong.easy.image.listener.ImagePagerController;
import com.dong.easy.image.presenter.LoadImagePresenter;
import com.dong.easy.image.setting.SpacesItemDecoration;
import com.dong.easy.image.view.IImageLoadView;
import com.dong.easy.image.viewholder.BigImageViewHolder;
import com.dong.easy.image.viewholder.BigViewPagerHolder;
import com.dong.easy.util.UIUtils;
import com.dong.easy.util.Views;
import com.dong.easy.view.LoadMoreRecyclerView;

/**
 * 🌑🌒🌓🌔🌕🌖🌗🌘
 * Created by zengwendong on 2017/12/14.
 */
public class ImageListActivity extends BaseActivity implements IImageLoadView,
        ImageListAdapter.OnImageListener,
        ImagePagerController,
        ViewPositionAnimator.PositionUpdateListener {

//    private SuperSwipeRefreshLayout superSwipeRefreshLayout;
//    private PullRefreshHeader pullRefreshHeader;
    private LoadMoreRecyclerView recyclerView;
    private SearchView searchView;

    private LoadImagePresenter presenter;

    private int page = 1;
    private String keyword = "";

    private ViewsTransitionAnimator<Integer> animator;
    private ImageListAdapter imageListAdapter;
    private ImagePagerAdapter imagePagerAdapter;
    private BigViewPagerHolder bigViewPagerHolder;

    @Override
    public int getContentView() {
        return R.layout.activity_image_list;
    }

    @Override
    public void initData() {
        searchView = Views.find(this, R.id.searchView);
        bigViewPagerHolder = new BigViewPagerHolder(this);

        setSearchView();

//        superSwipeRefreshLayout = Views.find(this, R.id.superSwipeRefreshLayout);
        recyclerView = Views.find(this, R.id.recyclerView);

        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(UIUtils.INSTANCE.dip2px(0.5f));
        recyclerView.addItemDecoration(decoration);

        setListener();

        initRecyclerView();
        initPager();
        initAnimator();

        presenter = new LoadImagePresenter(this);

        searchView.setQuery("仙剑", true);
    }

    private void initRecyclerView() {

//        pullRefreshHeader = new PullRefreshHeader(this);
//        superSwipeRefreshLayout.setHeaderView(pullRefreshHeader.getView());
//        superSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
//            @Override
//            public void onRefresh() {
//                pullRefreshHeader.refreshing();
//                page = 1;
//                presenter.loadImageList(page);
//            }
//
//            @Override
//            public void onPullDistance(int distance) {
//
//            }
//
//            @Override
//            public void onPullEnable(boolean enable) {
//                pullRefreshHeader.pullEnable(enable);
//            }
//        });


        recyclerView.setLayoutManager(new CCStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        //recyclerView.setItemAnimator(new DefaultItemAnimator());

        imageListAdapter = new ImageListAdapter(this);

        recyclerView.setAdapter(imageListAdapter);
    }

    private void setSearchView() {
        searchView.setIconifiedByDefault(false); //设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
        searchView.onActionViewExpanded(); // 写上此句后searchView初始是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能出现输入框,也就是设置为ToolBar的ActionView，默认展开
//        searchView.requestFocus(); //输入焦点
        searchView.setSubmitButtonEnabled(true);//添加提交按钮，监听在OnQueryTextListener的onQueryTextSubmit响应
//        searchView.setFocusable(true); //将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
        //searchView.isIconified = false //输入框内icon不显示
//        searchView.requestFocusFromTouch(); //模拟焦点点击事件

        TextView textView = (TextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
        textView.setHintTextColor(Color.parseColor("#cccccc"));

        View search_edit_frame = searchView.findViewById(android.support.v7.appcompat.R.id.search_edit_frame);
        LinearLayout.LayoutParams frameLayoutParams = (LinearLayout.LayoutParams) search_edit_frame.getLayoutParams();
        frameLayoutParams.leftMargin = 0;
        search_edit_frame.setLayoutParams(frameLayoutParams);

        ImageView search_mag_icon = (ImageView) findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        LinearLayout.LayoutParams iconLayoutParams = (LinearLayout.LayoutParams) search_mag_icon.getLayoutParams();
        iconLayoutParams.leftMargin = 0;
        search_mag_icon.setLayoutParams(iconLayoutParams);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keyword = query;
                UIUtils.INSTANCE.hideSoftInput(searchView);
                page = 1;
                presenter.loadImageList(page);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setListener() {
        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
                presenter.loadImageList(page);
            }
        });

        recyclerView.setOnViewScrollListener(new LoadMoreRecyclerView.OnViewScrollListener() {
            @Override
            public void onScroll(RecyclerView recyclerView, int firstVisiblePosition, int dx, int dy) {
                if (dy > 0) {
                    hideSoftInput();
                }
            }
        });

    }

    private void initPager() {
        imagePagerAdapter = new ImagePagerAdapter(this);

        bigViewPagerHolder.setPagerAdapter(imagePagerAdapter);
        bigViewPagerHolder.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExitImagePager();
            }
        });

    }

    private void initAnimator() {
        //动画执行时间，毫秒
        FloatScroller.DEFAULT_DURATION = 350;
        animator = new ViewsTransitionBuilder<Integer>()
                .fromRecyclerView(recyclerView, new SimpleViewsTracker() {
                    @Override
                    public View getViewForPosition(int position) {
                        RecyclerView.ViewHolder holder =
                                recyclerView.findViewHolderForLayoutPosition(position);
                        return holder == null ? null : ImageListAdapter.getImage(holder);
                    }
                })
                .intoViewPager(bigViewPagerHolder.viewPager, new SimpleViewsTracker() {
                    @Override
                    public View getViewForPosition(int position) {
                        BigImageViewHolder holder = imagePagerAdapter.getViewHolder(position);
                        return holder == null ? null : ImagePagerAdapter.getImageView(holder);
                    }
                })
                .build();
        animator.addPositionUpdateListener(this);
        animator.setReadyListener(new ViewsCoordinator.OnViewsReadyListener<Integer>() {
            @Override
            public void onViewsReady(@NonNull Integer id) {
                ImageView from = (ImageView) animator.getFromView();
                ImageView to = (ImageView) animator.getToView();
                if (to.getDrawable() == null) {
                    to.setImageDrawable(from.getDrawable());
                }
            }
        });
    }

    @Override
    public void onImageClick(int position) {
        hideSoftInput();
        searchView.setVisibility(View.GONE);
        imagePagerAdapter.setActivated(true);
        animator.enter(position, true);
    }

    @Override
    public void onPositionUpdate(float state, boolean isLeaving) {
        bigViewPagerHolder.setUpdateState(state);

        if (isLeaving && state == 0f) {
            imagePagerAdapter.setActivated(false);
        }
    }

    /**
     * 搜索关键词
     */
    @Override
    public String getSearchKeyword() {
        return keyword;
    }

    @Override
    public void loadFail() {
        UIUtils.INSTANCE.showShortToast("加载失败");
    }

    @Override
    public void refreshImageData(ImageDataResult imageDataResult) {

        if (page == 1) {
//            superSwipeRefreshLayout.setRefreshing(false);
            imageListAdapter.setData(imageDataResult.getData());
            imagePagerAdapter.setData(imageDataResult.getData());
            recyclerView.scrollToPosition(0);
        } else {
            imageListAdapter.addData(imageDataResult.getData());
            imagePagerAdapter.addData(imageDataResult.getData());
        }

        recyclerView.notifyMoreFinish(true);
    }


    public void onBackPressed() {
        if (!onExitImagePager()) {
            overridePendingTransition(0, 0);
            super.onBackPressed();
        }
    }

    /**
     * 隐藏键盘
     */
    private void hideSoftInput() {
        searchView.clearFocus();
        UIUtils.INSTANCE.hideSoftInput(searchView);
    }

    @Override
    public void onPause() {
        super.onPause();
        hideSoftInput();
    }

    @Override
    public boolean onExitImagePager() {
        if (!animator.isLeaving()) {
            searchView.setVisibility(View.VISIBLE);
            animator.exit(true);
            return true;
        }
        return false;
    }

    @Override
    public ViewPager getViewPager() {
        return bigViewPagerHolder.viewPager;
    }
}
