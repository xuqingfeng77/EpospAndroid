package com.eposp.android.ui;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


import com.eposp.android.util.AppCrashHandler;
import com.eposp.android.util.T;
import com.squareup.leakcanary.LeakCanary;


/**
 *@author : xqf
 *@date   :2017/8/19 下午4:45
 *@desc   :
 *@update :
 */
public class BaseApplication extends Application {
    public static final int PAGE_SIZE = 20;// 默认分页大小
    private static BaseApplication instance;
    static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        // 初始化异常捕获类
        AppCrashHandler.getInstance().init(this);
        //内存异常检测,square提供
        LeakCanary.install(this);
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return AppContext
     */
    public static BaseApplication getInstance() {
        return instance;
    }
    public static synchronized BaseApplication context() {
        return (BaseApplication) mContext;
    }
    public void clearAppCache(){

    }

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0, Gravity.BOTTOM);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, Gravity.BOTTOM);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.BOTTOM);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon, int gravity) {
        Context context = mContext;
        if (context != null)
            T.show(context, message, gravity, duration);
    }
}