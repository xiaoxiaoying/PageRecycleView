package com.xiaoxiaoyin.recycler.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiaoxiaoyin.recycler.R;


/**
 * Created by storm on 14-4-12.
 */
public class LoadingFooter {
    protected View mLoadingFooter;

    TextView mLoadingText;
    private ProgressBar progressBar;


    protected State mState = State.Idle;

    public static enum State {
        Idle, TheEnd, Loading
    }

    public LoadingFooter(Context context) {
        mLoadingFooter = LayoutInflater.from(context).inflate(R.layout.loading_footer, null);
        mLoadingFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 屏蔽点击
            }
        });
        mLoadingText = (TextView) mLoadingFooter.findViewById(R.id.textView);
        progressBar = (ProgressBar) mLoadingFooter.findViewById(R.id.load_progress);
        setState(State.Idle);
    }

    public View getView() {
        return mLoadingFooter;
    }

    public State getState() {
        return mState;
    }

    public void setState(final State state, long delay) {
        mLoadingFooter.postDelayed(new Runnable() {
            @Override
            public void run() {
                setState(state);
            }
        }, delay);
    }

    public void setState(State status) {
        if (mState == status) {
            return;
        }
        mState = status;

        mLoadingFooter.setVisibility(View.VISIBLE);
        switch (mState) {
            case Loading:
                mLoadingText.setVisibility(View.GONE);
                mLoadingText.setText("努力加载数据中...");
                progressBar.setVisibility(View.VISIBLE);
                break;
            case TheEnd:
                mLoadingText.setVisibility(View.VISIBLE);
                mLoadingText.setText("没有更多了");
                progressBar.setVisibility(View.GONE);
                break;
            default:
                mLoadingFooter.setVisibility(View.GONE);
                break;
        }
    }
}
