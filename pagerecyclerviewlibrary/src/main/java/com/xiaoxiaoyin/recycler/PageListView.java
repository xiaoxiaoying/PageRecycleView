package com.xiaoxiaoyin.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

import com.xiaoxiaoyin.recycler.Listener.OnLoadNextListener;
import com.xiaoxiaoyin.recycler.widget.LoadingFooter;


/**
 * Created by storm on 14-4-14.
 */
public class PageListView extends ListView implements AbsListView.OnScrollListener {
    private LoadingFooter mLoadingFooter;

    private OnLoadNextListener mLoadNextListener;

    public PageListView(Context context) {
        super(context);
        init();
    }

    public PageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private void init() {
        mLoadingFooter = new LoadingFooter(getContext());
        addFooterView(mLoadingFooter.getView());
        setOnScrollListener(this);
    }

    public void setLoadNextListener(OnLoadNextListener listener) {
        mLoadNextListener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (onScrollListener != null)
            onScrollListener.onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mLoadingFooter.getState() == LoadingFooter.State.Loading
                || mLoadingFooter.getState() == LoadingFooter.State.TheEnd) {
            return;
        }
        if (firstVisibleItem + visibleItemCount >= totalItemCount
                && totalItemCount != 0
                && totalItemCount != getHeaderViewsCount()
                + getFooterViewsCount() && mLoadNextListener != null) {
            mLoadingFooter.setState(LoadingFooter.State.Loading);
            mLoadNextListener.onLoadNext();
        }
    }

    public void setState(LoadingFooter.State status) {
        mLoadingFooter.setState(status);
    }

    public void setState(LoadingFooter.State status, long delay) {
        mLoadingFooter.setState(status, delay);
    }

    private com.xiaoxiaoyin.recycler.Listener.OnScrollAbsListViewListener onScrollListener;

    public void setOnScrollListener(com.xiaoxiaoyin.recycler.Listener.OnScrollAbsListViewListener listener) {
        this.onScrollListener = listener;
    }
}
