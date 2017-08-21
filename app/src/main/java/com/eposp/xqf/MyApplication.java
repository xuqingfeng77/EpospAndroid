package com.eposp.xqf;

import android.app.Application;
import android.content.Context;

import com.eposp.android.ui.BaseApplication;
import com.squareup.leakcanary.LeakCanary;


/**
 * @author div 2015-11-20 TODO
 */
public class MyApplication extends BaseApplication {



    public static String baseUrl = "ws://u.im-cc.com:17998/httpif";
    public static String UploadUrl = "http://u.im-cc.com:18400/";//文件上传的url测试
    public static String DownloadUrl = "http://u.im-cc.com:18400/download/";//文件下载的url


    @Override
    public void onCreate() {
        super.onCreate();


    }


    /**
     * debug 控制台输出日志
     * release 记录日志在sdcard
     */
    private void isAllException() {
        if (BuildConfig.DEBUG) {//debug 在控制台输出日志

        } else {//release
//            Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler(getApplicationContext()));
        }
    }

}
