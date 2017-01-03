package com.lh.sky.mywifichat;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lh.sky.wifi.WifiAdmin;

import java.util.List;

public class MainActivity extends ActionBarActivity {
    private TextView wifiListTextView;      //扫描结果textView
    private Button openWifiButton;          //关闭wifibutton
    private Button closeWifiButton;         //关闭wifibutton
    private Button scanWifiButton;          //扫描wifi
    private Button getWifiStateButton;      //获取wifi状态

    private WifiAdmin wifiAdmin;            //  wifi管理对象
    private List<ScanResult> scanResultList;//wifi扫描结果
    private ScanResult scanResult;
    StringBuffer wifiListData;               //wifi扫描数据

    private final static String TAG = "WifiChatApp";
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.lh.sky.activity.R.layout.activity_main);
        init();
    }

    public void init()
    {
        context = this;
        wifiAdmin = new WifiAdmin(context);
        wifiListTextView = (TextView)this.findViewById(com.lh.sky.activity.R.id.textView_wifiList);
        wifiListTextView.setMovementMethod(new ScrollingMovementMethod());  //textView实现滚动显示内容
        wifiListData = new StringBuffer();

        openWifiButton = (Button)this.findViewById(com.lh.sky.activity.R.id.button_openwifi);
        closeWifiButton = (Button)this.findViewById(com.lh.sky.activity.R.id.button_closewifi);
        scanWifiButton = (Button)this.findViewById(com.lh.sky.activity.R.id.button_scanwifi);
        getWifiStateButton = (Button)this.findViewById(com.lh.sky.activity.R.id.button_wifistatus);

        openWifiButton.setOnClickListener(new MyViewListener());
        closeWifiButton.setOnClickListener(new MyViewListener());
        scanWifiButton.setOnClickListener(new MyViewListener());
        getWifiStateButton.setOnClickListener(new MyViewListener());
    }

    //view点击事件绑定
    private class MyViewListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case com.lh.sky.activity.R.id.button_openwifi:
                    Log.d(TAG, "on click button_openwifi");
                    wifiAdmin.openWifi();
                    Toast.makeText(context, getResources().getString(com.lh.sky.activity.R.string.currentWifiState) + wifiAdmin.checkWifiStatus(), Toast.LENGTH_SHORT).show();
                    break;
                case com.lh.sky.activity.R.id.button_closewifi:
                    Log.d(TAG, "on click button_closewifi");
                    wifiAdmin.closeWifi();
                    Toast.makeText(context, getResources().getString(com.lh.sky.activity.R.string.currentWifiState) + wifiAdmin.checkWifiStatus(), Toast.LENGTH_SHORT).show();
                    break;
                case com.lh.sky.activity.R.id.button_scanwifi:
                    Log.d(TAG, "on click button_scanwifi");
                    getAllWifiList();
                    break;
                case com.lh.sky.activity.R.id.button_wifistatus:
                    Log.d(TAG, "on click button_wifistatus");
                    Toast.makeText(context, getResources().getString(com.lh.sky.activity.R.string.currentWifiState) + wifiAdmin.checkWifiStatus(), Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    //获取wifi列表
    public void getAllWifiList()
    {
        if(wifiListData.length() > 0)
            wifiListData.delete(0, wifiListData.length()-1);
        wifiListData.append(getResources().getString(com.lh.sky.activity.R.string.scanResTitle) + "\n");
        //扫描网络
        wifiAdmin.startScan();
        scanResultList = wifiAdmin.getScanResList();
        if(scanResultList != null)
        {
            for(int i = 0; i < scanResultList.size(); i++)
            {
                scanResult = scanResultList.get(i);
                wifiListData.append("SSID: " + scanResult.SSID + " BSSID: " + scanResult.BSSID + " level: " + scanResult.level + "\n\n");
            }

            wifiListTextView.setText(wifiListData.toString());
            Toast.makeText(context, getResources().getString(com.lh.sky.activity.R.string.scanWifiCount) + scanResultList.size(), Toast.LENGTH_SHORT).show();
        }
    }

}
