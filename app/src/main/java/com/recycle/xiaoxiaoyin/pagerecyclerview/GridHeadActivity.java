package com.recycle.xiaoxiaoyin.pagerecyclerview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.recycle.xiaoxiaoyin.pagerecyclerview.adapter.AllAdapter;
import com.xiaoxiaoyin.recycler.Listener.OnLoadNextListener;
import com.xiaoxiaoyin.recycler.PageRecyclerView;
import com.xiaoxiaoyin.recycler.widget.LoadingFooter;

import java.util.ArrayList;

/**
 * Created by xiaoxiaoyin on 16/1/13.
 */
public class GridHeadActivity extends AppCompatActivity implements OnLoadNextListener {
    private PageRecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_head);
        mRecyclerView = (PageRecyclerView) findViewById(R.id.page_recycler_view);
        intent = getIntent();
        initView();
    }

    private Intent intent;
    private int type;
    private AllAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private void initView() {
        type = intent.getIntExtra("type", 0);

        View view = LayoutInflater.from(this).inflate(R.layout.txt_item, null);
        ((TextView) view).setText("header");
        ((TextView) view).setTextSize(28);
        ((TextView) view).setTextColor(Color.BLUE);

        adapter = new AllAdapter(this);
        switch (type) {
            case ClassItem.LINEAR_HORIZONTAL:
                layoutManager = new LinearLayoutManager(this);
                ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
                mRecyclerView.removeFootView();
                break;
            case ClassItem.LINEAR_VERTICAL:
                layoutManager = new LinearLayoutManager(this);
                break;
            case ClassItem.GRID_HORIZONTAL:
                layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false);
                mRecyclerView.removeFootView();
                break;
            case ClassItem.GRID_VERTICAL:
                layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
                break;
            case ClassItem.START_HORIZONTAL:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
                mRecyclerView.removeFootView();
                break;
            case ClassItem.START_VERTICAL:
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                break;
            default:
                break;
        }
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(4, 4, 4, 4);
//                super.getItemOffsets(outRect, view, parent, state);
            }
        });
        mRecyclerView.addHeaderView(view);
        mRecyclerView.setLoadNextListener(this);
        page();
    }

    private int page = 1;
    private ArrayList<String> arrayList = new ArrayList<>();

    private void setDate() {
        arrayList.clear();
        for (int i = 0; i < 20; i++) {
            arrayList.add("this is test," + Math.random() * 20);
        }
    }

    @Override
    public void onLoadNext() {
        page++;
        page();
    }

    private void page() {
        setDate();
        if (page < 5) {
            mRecyclerView.setState(LoadingFooter.State.Idle);
        } else mRecyclerView.setState(LoadingFooter.State.TheEnd);


        adapter.addAll(arrayList);

        if (page == 1) {
            mRecyclerView.setItemLayoutMatchParent(5);
        }


    }
}
