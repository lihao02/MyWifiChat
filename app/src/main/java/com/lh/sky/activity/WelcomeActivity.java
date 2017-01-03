package com.lh.sky.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.lh.sky.views.WelcomeView;

/**
 * 欢迎activity
 * Created by Sky000 on 2016/12/22.
 */
public class WelcomeActivity extends Activity{
    private WelcomeView welcomeView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        welcomeView = new WelcomeView(this);
        context = this;
        setContentView(welcomeView);
        dealNetAbout();
    }

    public void dealNetAbout()
    {
        //判断wifi是否已经连接
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            Toast.makeText(context, R.string.wifiConnected, Toast.LENGTH_SHORT).show();
        }
        else
        {
            //跳转到wifi连接界面
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getResources().getString(R.string.systemInfo));
            builder.setMessage(getResources().getString(R.string.noWifiConnect));
            builder.setPositiveButton(getResources().getString(R.string.goConnWifi), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //跳转到wifi连接activity
                    finish(); //退出当前activity
                }
            });
            builder.create().show();
        }
    }
}
