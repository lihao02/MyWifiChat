package com.lh.sky.wifi;

import android.net.wifi.WifiConfiguration;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.lh.sky.activity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sky000 on 2016/12/20.
 */
public class WifiAdmin {
    private WifiManager wifiManager;    //wifiManager对象
    private WifiInfo wifiInfo;          //wifiInfo保存wifi信息
    private List<ScanResult> scanResList;   //扫描出的wifi列表
    private List<WifiConfiguration> wifiConfigurations;     //配置好的网络连接列表
    WifiManager.WifiLock wifiLock;       //wifi锁
    private List<String> wifiListData;
    private ScanResult scanResult;
    private Context context;
    private boolean isWifiConnected;      //wifi连接标志  true已连接  false未连接

    private static final String TAG = "WifiAdmin";

    public WifiAdmin(Context context) {

        this.context = context;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
    }

    //打开wifi
    public void openWifi() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
    }

    //关闭wifi
    public void closeWifi() {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    //检测当前wifi状态
    public String checkWifiStatus() {
        if(wifiInfo == null)
        {

            return context.getResources().getString(com.lh.sky.activity.R.string.noWifiConnect);
        }
        else {
            return context.getResources().getString(R.string.connectedWifi) + " " + wifiInfo.getSSID();
        }
    }

    //wifiLock锁定
    public void LockWifiLock() {
        wifiLock.acquire();
    }

    //释放wifiLock
    public void releaseWifiLock() {
        if (wifiLock.isHeld())
            wifiLock.acquire();
    }

    //创建wifiLock
    public void createWifiLock() {
        wifiLock = wifiManager.createWifiLock("test");
    }


    /**
     * @param SSID
     * @return 成功返回index 失败返回-1
     */
    public int isWifiConfiged(String SSID)
    {
        if(wifiConfigurations != null)
        {
            for(int i = 0; i < wifiConfigurations.size(); i++)
            {
                if(wifiConfigurations.get(i).SSID.equals(SSID))
                {
                   return i;
                }
            }

            return -1;
        }

        return -1;
    }

    //获取已经配置好的网络连接
    public List<WifiConfiguration> getWifiConfigurations() {
        wifiConfigurations = wifiManager.getConfiguredNetworks();

        for(int i = 0; i < wifiConfigurations.size(); i++)
        {
            Log.d(TAG, "--->wifiConfigurations: SSID=" + wifiConfigurations.get(i).SSID + " BDDIS=" + wifiConfigurations.get(i).BSSID);
        }

        return wifiConfigurations;
    }

    //获取扫描网络列表
    public List<ScanResult> getScanResList() {
        return scanResList;
    }

    //指定配置好的网络进行连接
    public boolean connectConfNet(int index) {
        if (index > wifiConfigurations.size() || index < 0)
            return false;
        return wifiManager.enableNetwork(wifiConfigurations.get(index).networkId, true);
    }

    //开始扫描
    public void startScan() {
        wifiManager.startScan();
        scanResList = wifiManager.getScanResults();         //得到扫描结果
        wifiConfigurations = wifiManager.getConfiguredNetworks();       //得到已经配置网络列表
    }

    //获取mac地址
    public String getMacAddr() {
        return (wifiInfo == null ? null : wifiInfo.getMacAddress());
    }

    //获取BSSID
    public String getBSSID() {
        return (wifiInfo == null ? null : wifiInfo.getBSSID());
    }

    //获取ip地址
    public int getIpAddr() {
        return (wifiInfo == null ? 0 : wifiInfo.getIpAddress());
    }

    //获取连接的id
    public int getNetConId()
    {
        return (wifiInfo == null ? 0 : wifiInfo.getNetworkId());
    }

    //添加一个网络并连接
    public void addNetConn(WifiConfiguration wifiConfiguration)
    {
        int id = wifiManager.addNetwork(wifiConfiguration);
        wifiManager.enableNetwork(id, true);
    }

    //断开指定id网络
    public void disNetById(int id)
    {
        wifiManager.disableNetwork(id);
        wifiManager.disconnect();
    }

    public List<String> getAllWifiList()
    {
        wifiListData = new ArrayList<String>();
        //扫描网络
        startScan();
        scanResList = getScanResList();
        if(scanResList != null)
        {
            Log.d(TAG, "size=" + scanResList.size());
            for(int i = 0; i < scanResList.size(); i++)
            {
                scanResult = scanResList.get(i);
                Log.d(TAG, "SSID: " + scanResult.SSID + " BSSID: " + scanResult.BSSID + " level: " + scanResult.level + "-->" + wifiManager.calculateSignalLevel(scanResult.level, 100));
                wifiListData.add(i, context.getResources().getString(com.lh.sky.activity.R.string.wifiName) + scanResult.SSID + "\t\t" +
                        context.getResources().getString(com.lh.sky.activity.R.string.wifiStrength) + wifiManager.calculateSignalLevel(scanResult.level, 100) + "%");
            }
        }

        return wifiListData;
    }

}
