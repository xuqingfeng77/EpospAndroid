package com.eposp.android.util;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.eposp.android.BuildConfig;
import com.eposp.android.ui.BaseApplication;
import com.eposp.android.ui.ErrorActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * @author : xqf
 * @date :2017/8/19 下午4:44
 * @desc :全局异常捕获类,记录异常日志，把异常日志显示到Activity界面上
 * @update :
 */

public class AppCrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    private static String strLogPath = "log/eposp_android/";//异常日志保存路径
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static AppCrashHandler INSTANCE = new AppCrashHandler();
    private Context mContext;

    private AppCrashHandler() {
    }

    public static AppCrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;

        Thread.UncaughtExceptionHandler exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (exceptionHandler == this)
            return;
        mDefaultHandler = exceptionHandler;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ErrorActivity.show(mContext, ex.getMessage());
        writeFile(strLogPath, ex.getMessage(), false);

//        final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + strLogPath;
//        String merchantNo = "111";
//
//        String fileName = android.os.Build.MODEL + "_" + android.os.Build.VERSION.RELEASE
//                + "_" + "appVersion_"
//                + "_" + merchantNo + ".txt";
//        writeFile(savePath + fileName, ex.getMessage(), false);

        if (mDefaultHandler != null && (BuildConfig.DEBUG || (!handleException(ex)))) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        ex.printStackTrace();


        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "app异常；正准备重启！！", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        BaseApplication.getInstance().clearAppCache();


        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return true;
    }

    public static boolean writeFile(String filePath, String content, boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            File file = ABFileUtil.creatSDDir(filePath);//先创建文件夹
            String fileName = android.os.Build.MODEL + "_" + android.os.Build.VERSION.RELEASE + "_" + DateUtil.getCurDateStr() + ".txt";
            fileWriter = new FileWriter(file.getAbsoluteFile() + File.separator + fileName, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

}
