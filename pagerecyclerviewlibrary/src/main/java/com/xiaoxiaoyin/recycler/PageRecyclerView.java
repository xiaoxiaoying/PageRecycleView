package com.xiaoxiaoyin.recycler;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.xiaoxiaoyin.recycler.Listener.OnLoadNextListener;
import com.xiaoxiaoyin.recycler.widget.LoadingFooter;

import java.util.ArrayList;

/**
 * Created by xiaoxiaoyin on 16/1/12.
 */
public class PageRecyclerView extends RecyclerView {
    /**
     * item 类型
     */
    public final static int TYPE_NORMAL = 0;
    public final static int TYPE_HEADER = 1;//头部--支持头部增加一个headerView
    public final static int TYPE_FOOTER = 2;//底部--往往是loading_more
    public final static int TYPE_LIST = 3;//代表item展示的模式是list模式
    public final static int TYPE_STAGGER = 4;//代码item展示模式是网格模式
    private LoadingFooter mLoadingFooter;
    private OnLoadNextListener mLoadNextListener;
    private boolean hasHeadView;
    private boolean hasFootView = true;
    private LayoutManager mLayoutManager;
    private int layoutManagerType;
    public static final int TYPE_MANAGER_OTHER = 0;
    public static final int TYPE_MANAGER_LINEAR = 1;
    public static final int TYPE_MANAGER_GRID = 2;
    public static final int TYPE_MANAGER_STAGGERED_GRID = 3;
    private ArrayList<Integer> indexItemMatch = new ArrayList<>();

    public PageRecyclerView(Context context) {
        super(context);
        init();
    }

    public PageRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mLoadingFooter = new LoadingFooter(getContext());
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (onScrollListener != null)
                    onScrollListener.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mLoadingFooter.getState() == LoadingFooter.State.Loading
                        || mLoadingFooter.getState() == LoadingFooter.State.TheEnd) {
                    return;
                }


                if (getLastVisiblePosition() + 1 == mLoadAdapter.getItemCount()
                        && dy != 0
                        && mLoadNextListener != null) {
                    mLoadingFooter.setState(LoadingFooter.State.Loading);
                    mLoadNextListener.onLoadNext();
                }
            }
        });


    }

    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
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
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    public void setState(LoadingFooter.State status) {
        mLoadingFooter.setState(status);
    }

    public void setState(LoadingFooter.State status, long delay) {
        mLoadingFooter.setState(status, delay);
    }

    public void setLoadNextListener(OnLoadNextListener mLoadNextListener) {
        this.mLoadNextListener = mLoadNextListener;
    }

    private com.xiaoxiaoyin.recycler.Listener.OnScrollRecyclerListener onScrollListener;

    public void setOnScrollListener(com.xiaoxiaoyin.recycler.Listener.OnScrollRecyclerListener listener) {
        this.onScrollListener = listener;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        super.setLayoutManager(layoutManager);
        if (layoutManager != null) {
            if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = TYPE_MANAGER_GRID;
                ((GridLayoutManager) mLayoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {

                        if (isHeader(position) || isFooter(position)) {
                            return ((GridLayoutManager) mLayoutManager).getSpanCount();
                        }
                        int index = position;
                        if (hasHeadView)
                            index++;
                        if (indexItemMatch.size() != 0)
                            if (indexItemMatch.contains(index)) {
                                return ((GridLayoutManager) mLayoutManager).getSpanCount();
                            }
                        return 1;
                    }

                });
            } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = TYPE_MANAGER_STAGGERED_GRID;
                ((StaggeredGridLayoutManager) mLayoutManager).setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            } else if (mLayoutManager instanceof LinearLayoutManager) {
                layoutManagerType = TYPE_MANAGER_LINEAR;
            } else {
                layoutManagerType = TYPE_MANAGER_OTHER;
            }
        }

    }

    private boolean isHeader(int position) {
        return position == 0 && hasHeadView ? true : false;
    }

    private boolean isFooter(int position) {
        if (hasFootView)
            return position >= mLoadAdapter.getItemCount() - 1;

        return position >= mLoadAdapter.getItemCount();
    }


    private LoadAdapter mLoadAdapter;

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            mLoadAdapter = new LoadAdapter(adapter);
        }
        super.swapAdapter(mLoadAdapter, true);
    }

    /**
     * 添加头部view
     *
     * @param view add head view
     */
    public void addHeaderView(View view) {
        mLoadAdapter.addHeaderView(view);
        mLoadAdapter.setHeaderEnable(true);
        hasHeadView = true;
        mLoadAdapter.notifyItemInserted(0);
    }

    /**
     * 移除头部view
     */
    public void removeHeaderView() {
        mLoadAdapter.setHeaderEnable(false);
        hasHeadView = false;
        mLoadAdapter.notifyItemRemoved(0);
    }

    /**
     * 不显示尾部 默认是显示的
     */
    public void removeFootView() {
        hasFootView = false;
        if (mLoadAdapter != null)
            mLoadAdapter.notifyDataSetChanged();
    }

    /**
     * 指定条目通栏
     *
     * @param position item index
     */
    public void setItemLayoutMatchParent(int position) {
        if (!indexItemMatch.contains(position)) {
            indexItemMatch.add(position);
            if (mLoadAdapter.getCount() > position) {
                int changeIndex = position;
                if (hasHeadView)
                    changeIndex++;
                mLoadAdapter.notifyItemChanged(changeIndex);
            }

        }
    }

    public void addItemLayoutMatchParent(int... position) {
        for (int i = 0; i < position.length; i++) {
            if (!indexItemMatch.contains(position[i])) {
                indexItemMatch.add(position[i]);
                int changeIndex = position[i];
                if (hasHeadView)
                    changeIndex++;
                mLoadAdapter.notifyItemChanged(changeIndex);
            }
        }
    }

    public void clearItemParent() {
        indexItemMatch.clear();
        mLoadAdapter.notifyDataSetChanged();
    }

    public void removeItemParent(int position) {
        if (indexItemMatch.contains(position)) {
            indexItemMatch.remove(position);
            int changeIndex = position;
            if (hasHeadView)
                changeIndex++;
            mLoadAdapter.notifyItemChanged(changeIndex);
        }
    }

    class LoadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private RecyclerView.Adapter adapter;
        private boolean mIsHeaderEnable;
        private View mHeaderView;

        public LoadAdapter(RecyclerView.Adapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_HEADER) {
                return new HeadViewHolder(mHeaderView);
            } else if (viewType == TYPE_FOOTER) {
                return new FooterViewHolder(mLoadingFooter.getView());
            } else {
                return adapter.onCreateViewHolder(parent, viewType);
            }

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type != TYPE_FOOTER && type != TYPE_HEADER) {
                adapter.onBindViewHolder(holder, position);
                if (indexItemMatch.contains(position) && layoutManagerType == TYPE_MANAGER_STAGGERED_GRID) {
                    setLayoutParams(holder.itemView);

                }
            }
        }

        @Override
        public int getItemCount() {
            int count = getCount();
            if (mIsHeaderEnable)
                count++;

            if (hasFootView)
                count++;

            return count;
        }

        public int getCount() {
            return adapter.getItemCount();
        }

        public RecyclerView.Adapter getParentAdapter() {
            return adapter;
        }

        @Override
        public int getItemViewType(int position) {
            int headerPosition = 0;
            int footerPosition = getItemCount() - 1;

            if (headerPosition == position && mIsHeaderEnable) {
                return TYPE_HEADER;
            }
            if (hasFootView)
                if (footerPosition == position) {
                    return TYPE_FOOTER;
                }
            /**
             * 这么做保证layoutManager切换之后能及时的刷新上对的布局
             */
            if (getLayoutManager() instanceof LinearLayoutManager) {
                return TYPE_LIST;
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                return TYPE_STAGGER;
            } else {
                return TYPE_NORMAL;
            }
        }

        class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);

                setLayoutParams(itemView);
            }
        }

        class HeadViewHolder extends RecyclerView.ViewHolder {
            public HeadViewHolder(View view) {
                super(view);

                setLayoutParams(view);
            }
        }

        private void setLayoutParams(View view) {
            if (layoutManagerType == TYPE_MANAGER_STAGGERED_GRID) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setFullSpan(true);
                view.setLayoutParams(layoutParams);
            } else if (layoutManagerType == TYPE_MANAGER_LINEAR || layoutManagerType == TYPE_MANAGER_GRID) {
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }

        public void setHeaderEnable(boolean enable) {
            mIsHeaderEnable = enable;
        }

        public void addHeaderView(View view) {
            mHeaderView = view;
        }

    }

}
