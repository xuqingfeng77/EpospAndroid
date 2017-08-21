package com.eposp.android.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.eposp.android.net.NetUtil;
import com.eposp.android.ui.BaseActivity;

/**
 * @author :xqf
 * @date :2017/8/21  上午1:03
 * @desc :网络断开监听
 * @update :
 */
public class NetBroadcast extends BroadcastReceiver {

    public NetEvent evevt = BaseActivity.netChangeEvent;//这样写法有个弊端，无法兼容fragment

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWorkState);
        }
    }
     //还可以
    // 自定义接口
    public interface NetEvent {
        public void onNetChange(int netMobile);
    }
}
