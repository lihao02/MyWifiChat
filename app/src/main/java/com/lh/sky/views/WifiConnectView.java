package com.lh.sky.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button scanWifiListButton;
    private ArrayAdapter<String> listViewDataAdapter;

    private ProgressDialog dialog;
    private ProgressDialog dialog1;

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
        scanResultList = wifiAdmin.getScanResList();
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
        listViewDataAdapter = new ArrayAdapter<String>(context, com.lh.sky.activity.R.layout.support_simple_spinner_dropdown_item, wifiListData);
        wifiList_ListView.setAdapter(listViewDataAdapter);
        wifiList_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String currentSSID = "\"" + scanResultList.get(position).SSID + "\"";
                Log.d(TAG, "pos=" + position + " currentSSID=" + currentSSID);
                //判断currentSSID是否已经配置
                if (wifiAdmin.isWifiConfiged(currentSSID) == -1) //未配置
                {
                    final WifiConfiguration wifiConfiguration = new WifiConfiguration();
                    Log.d(TAG, "currentSSID=" + currentSSID + "未配置");
                    WifiPasswordInputView wifiPasswordInputView = new WifiPasswordInputView(context);
                    final EditText password = wifiPasswordInputView.getPasswordEditText();
                    Button connButton = wifiPasswordInputView.getConnButton();
                    final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(wifiPasswordInputView).create();
                    alertDialog.setTitle(getResources().getString(R.string.inputPassTitle));
                    alertDialog.show();
                    connButton.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "---connButton.setOnClickListener");
                            dialog1 = ProgressDialog.show(context, "", getResources().getString(R.string.wificonnecting) + currentSSID);
                            //获得输入的wifi密码
                            String passwordString = password.getText().toString();
                            Log.d(TAG, "---input password=" + passwordString);
                            wifiConfiguration.SSID = currentSSID;
                            wifiConfiguration.preSharedKey = "\"" + passwordString + "\"";
                            wifiConfiguration.hiddenSSID = false;
                            wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
                            boolean connRes = wifiAdmin.addNetConn(wifiConfiguration);
                            dialog1.dismiss();
                            if (connRes) {
                                Toast.makeText(context, getResources().getString(R.string.connectedWifi) + currentSSID, Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                //跳转到聊天界面
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.connError), Toast.LENGTH_SHORT).show();
                                password.setText("");
                            }
                        }
                    });
                } else    //已经配置
                {
                    //dialog = ProgressDialog.show(context, "", getResources().getString(R.string.wificonnecting) + currentSSID);
                    Log.d(TAG, "currentSSID=" + currentSSID + "已配置");
                    if (wifiAdmin.connectConfNet(position)) {
                        dialog.dismiss();
                        Toast.makeText(context, getResources().getString(R.string.connectedWifi) + currentSSID, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(wifiList_ListView, layoutParams);

        scanWifiListButton = wifiScanTitleView.getScanWifiListButton();
        scanWifiListButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(context, "", getResources().getString(R.string.wifiScaning));
                wifiListData = wifiAdmin.getAllWifiList();
                scanResultList = wifiAdmin.getScanResList();
                listViewDataAdapter.notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(context, getResources().getString(R.string.wifiScanComplete), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
