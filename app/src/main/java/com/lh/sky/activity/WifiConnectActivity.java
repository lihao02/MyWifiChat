package com.lh.sky.activity;

import android.app.Activity;
import android.os.Bundle;

import com.lh.sky.views.WifiConnectView;

/**
 * wifi网络连接activity
 * Created by Sky000 on 2016/12/22.
 */
public class WifiConnectActivity extends Activity {
    private WifiConnectView wifiConnectView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wifiConnectView = new WifiConnectView(this);
        setContentView(wifiConnectView);
    }
}
