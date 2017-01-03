package com.lh.sky.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lh.sky.activity.R;
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
    private WifiScanTitleView wifiScanTitleView;  //wifi列表
    private ListView wifiList_ListView;  //wifi列表view
    private List<ScanResult> scanResultList;    //wifi扫描结果
    private List<String> wifiListData;
    private List<WifiConfiguration> wifiConfigurationsList;

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
        wifiConfigurationsList = wifiAdmin.getWifiConfigurations();
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
        wifiState_TextView.setTextSize(20);
        wifiState_TextView.setText(getResources().getString(R.string.currentWifiState) + wifiAdmin.checkWifiStatus());
        addView(wifiState_TextView);

        wifiScanTitleView = new WifiScanTitleView(context);
        addView(wifiScanTitleView);

        wifiList_ListView = new ListView(context);
        wifiList_ListView.setAdapter(new ArrayAdapter<String>(context, com.lh.sky.activity.R.layout.support_simple_spinner_dropdown_item, wifiListData));
        wifiList_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProgressDialog dialog;
                dialog = ProgressDialog.show(context, "", getResources().getString(R.string.wificonnecting));
                String currentSSID = scanResultList.get(position).SSID;
                Log.d(TAG, "pos=" + position + " currentSSID=" + currentSSID);
                //判断currentSSID是否已经配置
                if (wifiAdmin.isWifiConfiged(currentSSID) == -1) //未配置
                {
                    Log.d(TAG, "currentSSID=" + currentSSID + "未配置");
                } else    //已经配置
                {
                    Log.d(TAG, "currentSSID=" + currentSSID + "已配置");
                }
            }
        });
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(wifiList_ListView, layoutParams);
    }

}
