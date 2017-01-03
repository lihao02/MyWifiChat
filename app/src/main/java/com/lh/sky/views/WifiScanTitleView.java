package com.lh.sky.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lh.sky.activity.R;

/**
 * Created by Sky000 on 2017/1/3.
 */
public class WifiScanTitleView extends LinearLayout {
    private TextView wifiScanTitle;
    private Button scanWifiList_button; //刷新wifi列表
    private Context context;

    private static final String TAG = "WifiScanTitleView";

    public WifiScanTitleView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void init()
    {
        setOrientation(HORIZONTAL);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        wifiScanTitle = new TextView(context);
        wifiScanTitle.setTextSize(20);
        wifiScanTitle.setText(getResources().getString(R.string.scanResTitle));
        addView(wifiScanTitle, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        scanWifiList_button = new Button(context);
        scanWifiList_button.setTextSize(16);
        scanWifiList_button.setText(getResources().getString(R.string.scanWifiList));
        scanWifiList_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //刷新wifi列表
                Log.d(TAG, "scanWifiList_button onClick");
            }
        });
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = 30;
        addView(scanWifiList_button, layoutParams);

    }
}
