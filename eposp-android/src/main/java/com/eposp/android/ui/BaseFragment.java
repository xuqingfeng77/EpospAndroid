package com.eposp.android.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eposp.android.R;
import com.eposp.android.broadcast.NetBroadcast;
import com.eposp.android.dialog.CustomDialog;
import com.eposp.android.dialog.DialogHelper;
import com.eposp.android.dialog.DialogUtil;
import com.eposp.android.util.ScreenSwitch;


/**
 *@author : xqf
 *@date   :2017/8/21 下午2:06
 *@desc   :
 *@update :
 */
public abstract class BaseFragment extends Fragment implements DialogControl,BaseViewInfterface,NetBroadcast.NetEvent{

    private View view;
    protected Context mContext;
    protected Bundle bundle;
    protected CustomDialog progressDialog;
    private Toast mToast;
    private boolean cancelable = true;//返回键是否可以隐藏菊花  默认为true
    private ProgressDialog _waitDialog;

    protected LayoutInflater mInflater;
    protected ActionBar mActionBar;

    private boolean _isVisible;//控制是否需要显示对话框



    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }



    /**
     * 重写此方法，发起异步异步请求
     *
     * @param taskId 异步请求任务id
     */
    protected void sendHttpRequest(int taskId) {

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            initView();
        } else {
            View oldParent = (View) view.getParent();
            if (oldParent != container && oldParent != null) {
                ((ViewGroup) oldParent).removeView(view);
            }
        }
        eventOnClick();
        return view;
    }

    public <T extends View> T getViewById(int id) {
        View v = view.findViewById(id);
        return (T) v;
    }
//
//    public void showProgressDialog() {
//        if (progressDialog == null) {
//            progressDialog = DialogUtil.getHorizontalProgressDialog(mContext);
//            progressDialog.setCancelable(cancelable);
//        }
//        progressDialog.show();
//    }
//
//    public void showProgressDialog(String string) {
//        if (progressDialog == null) {
//            progressDialog = DialogUtil.getHorizontalProgressDialog(mContext, string);
//            progressDialog.setCancelable(cancelable);
//        }
//        progressDialog.show();
//    }
//
//    public void dismissProgressDialog() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//    }
    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getProgressDialog(mContext, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void showToast(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
                } else {
                    mToast.setText(text);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.show();
            }
        });
    }

    protected void setViewOnclickListener(int viewId, View.OnClickListener listener) {
        View view = getViewById(viewId);
        view.setOnClickListener(listener);
    }

    public void goActivity(Class<?> descClass, Bundle bundle, int requestCode) {
        ScreenSwitch.switchActivity(mContext, descClass, bundle, requestCode);
    }

    protected void setViewVisibility(int viewId, boolean visible) {
        View view = getViewById(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }


    public void goActivity(Class<?> descClass, Bundle bundle) {
        goActivity(descClass, bundle, 0);
    }

    public void goActivity(Class<?> descClass) {
        goActivity(descClass, null);
    }
}
