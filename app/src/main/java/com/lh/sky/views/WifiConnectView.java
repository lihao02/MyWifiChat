package com.lh.sky.views;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lh.sky.wifi.WifiAdmin;

import java.util.List;

/**
 * Created by Sky000 on 2016/12/30.
 */
public class WifiConnectView extends LinearLayout {
    private static final String TAG = "WifiConnectView";

    private WifiAdmin wifiAdmin;    //wifi对象
    private ScanResult scanResult;
    private Context context;
    private TextView wifiState_TextView;    //wifi状态
    private TextView wifiList_TextView;  //wifi列表
    private ListView wifiList_ListView;  //wifi列表view
    private List<ScanResult> scanResultList;    //wifi扫描结果
    private List<String> wifiListData;

    public WifiConnectView(Context context) {
        super(context);
        this.context = context;
        initData();
        initView();
    }

    //初始化一些操作
    public void initData()
    {
        wifiAdmin = new WifiAdmin(context);
        //打开wifi
        wifiAdmin.openWifi();
        wifiListData = wifiAdmin.getAllWifiList();
        for(int i = 0; i < wifiListData.size(); i++)
        {
            Log.d(TAG, "wifi: " + wifiListData.get(i));
        }
    }

    //view 初始化
    public void initView()
    {
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        wifiState_TextView = new TextView(context);
        wifiState_TextView.setText(com.lh.sky.activity.R.string.currentWifiState);

        wifiList_TextView = new TextView(context);
        wifiList_TextView.setTextSize(20);
        wifiList_TextView.setText(com.lh.sky.activity.R.string.scanResTitle);
        addView(wifiList_TextView);

        wifiList_ListView = new ListView(context);
        wifiList_ListView.setAdapter(new ArrayAdapter<String>(context, com.lh.sky.activity.R.layout.support_simple_spinner_dropdown_item, wifiListData));
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(wifiList_ListView, layoutParams);
    }

}
