package com.eposp.android.util;



import com.eposp.android.log.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/3/3 0003.
 */
public class SerializeUtils {

    /**
     * 序列化对象
     *
     * @param obj
     * @return
     * @throws IOException
     */
    public static String serialize(Object obj) {
        long startTime = System.currentTimeMillis();
        String serStr = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
            objectOutputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.d("paper", "序列化耗时:" + (System.currentTimeMillis() - startTime)/1000+"秒");
        return serStr;
    }

    public static String serializeM(Object userInfo) throws IOException {
        long startTime = System.currentTimeMillis();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(userInfo);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        Long endTime = System.currentTimeMillis();
        LogUtils.d("paper", "序列化耗时为:" + (endTime - startTime));
        return serStr;
    }




    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deSerialization(String str) {
        long startTime = System.currentTimeMillis();
        String redStr;
        Object person = null;
        try {
            redStr = java.net.URLDecoder.decode(str, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    byteArrayInputStream);
            person =  objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        LogUtils.d("反序列化耗时为:" + (endTime - startTime));
        return person;
    }
}
