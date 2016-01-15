package com.recycle.xiaoxiaoyin.pagerecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.recycle.xiaoxiaoyin.pagerecyclerview.R;
import com.xiaoxiaoyin.recycler.adapter.ArrayAdapter;

/**
 * Created by xiaoxiaoyin on 16/1/14.
 */
public class AllAdapter extends ArrayAdapter<String> {
    private LayoutInflater layoutInflater;

    public AllAdapter(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindView(RecyclerView.ViewHolder holder, int position, String object) {
        Holder h = (Holder) holder;
        h.txt.setText(object);
        ViewGroup.LayoutParams params = h.txt.getLayoutParams();
        params.height = (int) (Math.random() * 10 + 1) * 20;
        h.txt.setLayoutParams(params);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.txt_item, parent, false);
        return new Holder(view);
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView txt;

        public Holder(View itemView) {
            super(itemView);
            txt = (TextView) itemView;
        }
    }

}
