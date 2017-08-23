package com.eposp.xqf.util;

import com.eposp.android.log.LogUtils;
import com.eposp.android.util.ABPreferenceUtils;
import com.eposp.android.util.SerializeUtils;

import java.io.Serializable;

/**
 *@author  :xqf
 *@date    :2017/8/23 11:52
 *@desc    : 缓存用户个人数据
 *@update  :
 */
public class UserData implements Serializable {

    private static final String USER_INFO = "user_info";

    private String phone;//手机号
    private String pwd;//密码
    private String decPwd;//明文密码
    private String userId;
    private String userName;//用户名称
    private String merchantName;//商户名称
    private String merchantNo;//商户编号
    private String merStatus;//商户状态     1待一审 2待平台审核 3审核失败 4正常


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDecPwd() {
        return decPwd;
    }

    public void setDecPwd(String decPwd) {
        this.decPwd = decPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerStatus() {
        return merStatus;
    }

    public void setMerStatus(String merStatus) {
        this.merStatus = merStatus;
    }



    private static volatile UserData instance = null;

    private UserData() {
    }

    public static UserData getInstance() {
        if (instance == null) {
            synchronized (UserData.class) {
                if (instance == null) {
                    instance = new UserData();
                }
            }
        }
        return instance;
    }

    public static UserData getUserDataInSP() {
        if(instance == null){
            String infoStr = ABPreferenceUtils.getStringParam(USER_INFO);
            instance = (UserData) SerializeUtils.deSerialization(infoStr);//反序列化
        }
        return instance;
    }

    public void saveUserInfo(){
        if(instance != null){
            String serialize = SerializeUtils.serialize(instance);//序列化
            ABPreferenceUtils.saveParam(USER_INFO, serialize);
        }else{
            LogUtils.d("保存用户信息失败");
        }
    }

    public static void removeUserInfo(){
        instance = null;
        ABPreferenceUtils.removeKey(USER_INFO);
    }
}
