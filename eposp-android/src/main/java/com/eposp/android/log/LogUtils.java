package com.eposp.android.log;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;


import com.eposp.android.util.ABAppUtil;
import com.eposp.android.util.ABConfig;
import com.eposp.android.util.ABFileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;


public class LogUtils {

    public static String customTagPrefix = "Dream"; // 自定义Tag的前缀，可以是作者名
    /** 时间转换格式 */
    @SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat sDateFormatYMD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

    private LogUtils() {
    }

    // 容许打印日志的类型，默认是true，设置为false则不打印
    public static boolean allowD = true;

    private static String generateTag(StackTraceElement caller, String mtag) {
        String tag = "%s.%s(Line:%d)"; // 占位符
        String callerClazzName = caller.getClassName(); // 获取到类名
        callerClazzName = callerClazzName.substring(callerClazzName
                .lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(),
                caller.getLineNumber()); // 替换
        tag = TextUtils.isEmpty(mtag) ? customTagPrefix + ":"
                + tag :mtag + ":"+ tag ;
        return tag;
    }


    public static void d(String content) {
        if (!allowD)
            return;
        StackTraceElement caller = getCallerStackTraceElement();
        String tag = generateTag(caller,null);
        Log.d(tag, content);
    }
    
    public static void d(String mtag, String content) {
    	if (!allowD)
    		return;
    	StackTraceElement caller = getCallerStackTraceElement();
    	String tag = generateTag(caller,mtag);
    	Log.d(tag, content);
    }

  
    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }
    
    /**
     * @param path 文件夹路径
     * @param name 文件名字
     * @param msg  内容
     */
    public static void point(String path, String name, String tag, String msg) {
        if (ABAppUtil.haveSDCard()) {
            File file  = ABFileUtil.getFileAutoCreated(ABFileUtil.getSDPATH()+ File.separator+path+ File.separator+name+".log");
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file, true)));
                out.write(tag + " " + msg + "\r\n");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
	/**
	 * 新加客户端日志
	 * @param logMe
	 */
	public static void write(String logMe) {
		d(ABConfig.FILE_CACHE_PATH, logMe);
		String filName = new SimpleDateFormat("yyyyMMdd").format(new Date());
		writeClientReportFile(filName, logMe);
	}
	
	public synchronized static void writeClientReportFile(String filName, String data) {
		String path = ABFileUtil.getSdCardPath();
		try {
			File filePath = new File(path);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}

			File file = new File(filePath + File.separator + filName); // httptest.txt
			if (!file.exists()) {
				file.createNewFile();
				orderByDate(path);//删除多余的日志
			}
			StringBuffer buffer = new StringBuffer();
			String dateString = sDateFormatYMD.format(new Date(System.currentTimeMillis()));
			buffer.append(dateString).append("   ").append(data).append("\r\n");
			
			RandomAccessFile raf = new RandomAccessFile(file, "rw");// "rw" 读写权限
			raf.seek(file.length());
			raf.write(buffer.toString().getBytes());
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void orderByDate(String fliePath) {
		File file = new File(fliePath);
		File[] fs = file.listFiles();
		Arrays.sort(fs, new Comparator<File>() {
			public int compare(File f1, File f2) {
				long diff = f1.lastModified() - f2.lastModified();
				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;
			}

			public boolean equals(Object obj) {
				return true;
			}

		});
		for (int i = fs.length - 1; i > -1; i--) {
			if (i + 3 < fs.length) {
				fs[i].delete();// 删除
			}
		}
	}
}

