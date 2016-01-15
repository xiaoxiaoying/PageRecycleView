package com.recycle.xiaoxiaoyin.pagerecyclerview.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recycle.xiaoxiaoyin.pagerecyclerview.ClassItem;
import com.recycle.xiaoxiaoyin.pagerecyclerview.R;
import com.xiaoxiaoyin.recycler.adapter.ArrayAdapter;

/**
 * Created by xiaoxiaoyin on 16/1/13.
 */
public class MainAdapter extends ArrayAdapter<ClassItem> {
    private LayoutInflater layoutInflater;

    public MainAdapter(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position, ClassItem object) {
        Holder h = (Holder) holder;
        h.txt.setText(object.content);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.txt_item, parent, false);

        return new Holder(view);
    }

    class Holder extends RecyclerView.ViewHolder {
        public TextView txt;

        public Holder(View itemView) {
            super(itemView);
            txt = (TextView) itemView;
        }
    }
}
