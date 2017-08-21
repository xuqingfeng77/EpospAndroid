package com.eposp.android.ui;

import android.app.ProgressDialog;
/**
 *@author : xqf
 *@date   :2017/8/21 下午2:06
 *@desc   :对话框接口
 *@update :
 */
public interface DialogControl {

    public abstract void hideWaitDialog();

    public abstract ProgressDialog showWaitDialog();

    public abstract ProgressDialog showWaitDialog(int resid);

    public abstract ProgressDialog showWaitDialog(String text);
}
