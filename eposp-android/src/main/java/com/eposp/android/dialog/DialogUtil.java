package com.eposp.android.dialog;

import android.content.Context;
import android.view.View.OnClickListener;

import com.eposp.android.R;
import com.eposp.android.util.Constants;


/**
 * @author div 
 * 2015-11-20
 * 自定义
 */
public class DialogUtil {

	public static CustomDialog getDoubleCustomDialog(Context mContext, int res_title, int res_message, OnClickListener ok_listener) {
		return getDoubleCustomDialog(mContext, res_title, res_message, R.string.cancel, R.string.ok,ok_listener);
	}
	
	public static CustomDialog getDoubleCustomDialog(Context mContext, int res_title, int res_message, int res_cancel, int res_ok, OnClickListener ok_listener) {
		CustomDialog dialog = new CustomDialog(mContext);
		return dialog.setTitles(mContext.getString(res_title))
		      .setMessage(mContext.getString(res_message))
		      .setNegativeButton(mContext.getString(res_cancel), null)
		      .setPositiveButton(mContext.getString(res_ok), ok_listener);
	}
	
	public static CustomDialog getDoubleCustomDialog(Context mContext, String title, String message, OnClickListener ok_listener) {
		return getDoubleCustomDialog(mContext, title, message, mContext.getString(R.string.cancel), mContext.getString(R.string.ok),ok_listener);
	}
	
	public static CustomDialog getDoubleCustomDialog(Context mContext, String title, String message, String cancel, String ok, OnClickListener ok_listener) {
		CustomDialog dialog = new CustomDialog(mContext);
		return dialog.setTitles(title)
				.setMessage(message)
				.setNegativeButton(cancel, null)
				.setPositiveButton(ok, ok_listener);
	}
	
	public static CustomDialog getSingleCustomDialog(Context mContext, int res_title, int res_message, int res_btn_text, OnClickListener listener) {
		return getSingleCustomDialog(mContext, mContext.getString(res_title), mContext.getString(res_message), mContext.getString(res_btn_text), listener);
	}
	
	public static CustomDialog getSingleCustomDialog(Context mContext, String title, String message, String res_btn_text, OnClickListener listener) {
		CustomDialog dialog = new CustomDialog(mContext);
		return dialog.setTitles(title)
				.setMessage(message)
				.setPositiveButton(res_btn_text, listener);
	}
	
	
    public static CustomDialog getHorizontalProgressDialog(Context mContext){
        return getHorizontalProgressDialog(mContext, mContext.getString(R.string.loading));
    }
    
    public static CustomDialog getHorizontalProgressDialog(Context mContext, int res){
    	return getHorizontalProgressDialog(mContext, mContext.getString(res));
    }
    
    public static CustomDialog getHorizontalProgressDialog(Context mContext, String text){
    	return new CustomDialog(mContext, Constants.Dialog_HorizontalProgress).setHorizontalProgress(text);
    }
    
    public static CustomDialog getVerticalProgressDialog(Context mContext){
        return getHorizontalProgressDialog(mContext, mContext.getString(R.string.loading));
    }
    
    public static CustomDialog getVerticalProgressDialog(Context mContext, int res){
    	return getHorizontalProgressDialog(mContext, mContext.getString(res));
    }
    
    public static CustomDialog getVerticalProgressDialog(Context mContext, String text){
    	return new CustomDialog(mContext, Constants.Dialog_VerticalProgress).setVerticalProgress(text);
    }
    
    
}
