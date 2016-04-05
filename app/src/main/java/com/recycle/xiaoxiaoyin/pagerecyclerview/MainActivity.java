package com.recycle.xiaoxiaoyin.pagerecyclerview;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.recycle.xiaoxiaoyin.pagerecyclerview.adapter.MainAdapter;
import com.xiaoxiaoyin.recycler.Listener.OnItemClickListener;
import com.xiaoxiaoyin.recycler.PageRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener<ClassItem>, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private RecyclerView mRecyclerView;
    private MainAdapter adapter;
    private List<String> data = new ArrayList<>();
    private TextView txt;

    private void initView() {
        for (int i = 0; i < 25; i++) {
            data.add(String.valueOf(i + 1));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        txt = (TextView) findViewById(R.id.txt);
        txt.setOnClickListener(this);
        adapter = new MainAdapter(this);
        adapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 10, 0, 4);
//                super.getItemOffsets(outRect, view, parent, state);
            }
        });

//        adapter.addAll(data);


        adapter.add(new ClassItem(GridHeadActivity.class, "Grid 横向显示", ClassItem.GRID_HORIZONTAL));
        adapter.add(new ClassItem(GridHeadActivity.class, "Grid 纵向显示", ClassItem.GRID_VERTICAL));
        adapter.add(new ClassItem(GridHeadActivity.class, "LINEAR 横向显示", ClassItem.LINEAR_HORIZONTAL));
        adapter.add(new ClassItem(GridHeadActivity.class, "LINEAR 纵向显示", ClassItem.LINEAR_VERTICAL));
        adapter.add(new ClassItem(GridHeadActivity.class, "STAGGER 横向显示", ClassItem.START_HORIZONTAL));
        adapter.add(new ClassItem(GridHeadActivity.class, "STAGGER 纵向显示", ClassItem.START_VERTICAL));

    }


    @Override
    public void onItemClickListener(ClassItem object) {
        startActivity(new Intent(this, object.aClass).putExtra("type", object.type));
    }

    private int i = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt:
                Collections.reverse(data);
                for (String s : data) {
                    i++;
                    Log.i("TestActivity", "78 " + i + " ===>>>>>> " + s);
                }
//                adapter.clear();
//                adapter.addAll(data);
                break;
            default:
                break;
        }
    }
}
