package com.lh.sky.views;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Sky000 on 2016/12/23.
 */
public class WelcomeView extends LinearLayout{
    public WelcomeView(Context context) {
        super(context);
        initView();
    }

    public void initView()
    {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setBackgroundResource(com.lh.sky.activity.R.drawable.welcome_pic);
    }
}
