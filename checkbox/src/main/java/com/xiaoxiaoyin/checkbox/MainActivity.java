package com.xiaoxiaoyin.checkbox;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.xiaoxiaoyin.checkbox.view.CheckBox;
import com.xiaoxiaoyin.checkbox.view.PlayProgress;

/**
 * Created by xiaoxiaoyin on 16/1/27.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private PlayProgress mPlayProgress;
    private CheckBox mCheckBox;

    private void initView() {
        mPlayProgress = (PlayProgress) findViewById(R.id.play_progress);
        mCheckBox = (CheckBox) findViewById(R.id.rectangle);
        mCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    mPlayProgress.start();
                } else {
                    mPlayProgress.stop();
                }
            }
        });

    }
}
