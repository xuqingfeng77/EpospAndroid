package com.eposp.android.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.eposp.android.R;
import com.eposp.android.broadcast.NetBroadcast;
import com.eposp.android.dialog.CustomDialog;
import com.eposp.android.dialog.DialogHelper;
import com.eposp.android.net.NetUtil;
import com.eposp.android.util.ScreenSwitch;
import com.eposp.android.view.ToolBarHelper;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 *@author : xqf
 *@date   :2017/8/18 下午4:48
 *@desc   : 基础模块放到基础库中，方便各个模块使用；标题栏用使用toolbar
 *@update :
 */
public abstract class BaseToolbarActivity extends AppCompatActivity implements DialogControl,BaseViewInfterface,NetBroadcast.NetEvent{

    protected Activity mContext;
    protected CustomDialog progressDialog;//自定义对话框，暂时不用
    private Toast mToast;
    protected Bundle bundle;
    private Unbinder unbinder;;
    private boolean cancelable = true;//返回键是否可以隐藏菊花  默认为true
    private ProgressDialog _waitDialog;

    protected LayoutInflater mInflater;

    private boolean _isVisible;//控制是否需要显示对话框
    public static NetBroadcast.NetEvent netChangeEvent;
    /**
     * 网络类型
     */
    private int netMobile;

    private ToolBarHelper mToolBarHelper ;
    public Toolbar toolbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        netChangeEvent=this;
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mInflater = getLayoutInflater();
        _isVisible=true;//默认需要显示对话框
        unbinder=ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        initView();
        inspectNet();
        eventOnClick();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
        toolbar = mToolBarHelper.getToolBar() ;
        setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar) ;
    }


    public void onCreateCustomToolBar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0,0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }



    public void setToolbarTitle(int resId) {
        if (resId != 0) {
            setToolbarTitle(getString(resId));
        }
    }

    public void setToolbarTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        if (hasActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onBeforeSetContentLayout() {
    }

    protected boolean hasActionBar() {
        return getSupportActionBar() != null;
    }


    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    protected int getActionBarTitle() {
        return R.string.app_name;
    }

    protected boolean hasBackButton() {
        return false;
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getViewById(int id) {
        View view = findViewById(id);
        return (T) view;
    }
    //----start------被注释的也是可以用的，----
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
    //-------------end----

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
                _waitDialog = DialogHelper.getProgressDialog(this, message);
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
        runOnUiThread(new Runnable() {
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
        //下面是自定义toast，需要优化，容易创建很多实例对象
//        CommonToast commonToast=new CommonToast(this);
//        commonToast.setMessage(text);
//        commonToast.setDuration(CommonToast.DURATION_SHORT);
//        commonToast.setLayoutGravity(Gravity.CENTER);
//        commonToast.show();
    }

    /**
     * @param colorId
     * @return
     */
    public int getResColor(int colorId){
        return mContext.getResources().getColor(colorId);
    }
//    /**
//     * @param stringId  根据id获取string.xml里的字符串
//     */
//    public void showToast(final int stringId) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mToast == null) {
//                    mToast = Toast.makeText(mContext, mContext.getResources().getString(stringId), Toast.LENGTH_LONG);
//                } else {
//                    mToast.setText(mContext.getResources().getString(stringId));
//                    mToast.setDuration(Toast.LENGTH_SHORT);
//                }
//                mToast.show();
//            }
//        });
//    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    public void onBackPressed() {
        cancelToast();
        super.onBackPressed();
    }

    public void goActivityForResult(Class<?> descClass, int requestCode) {
        ScreenSwitch.switchActivity(mContext, descClass, null, requestCode);
    }

    public void goActivityForResult(Class<?> descClass, Bundle bundle, int requestCode) {
        ScreenSwitch.switchActivity(mContext, descClass, bundle, requestCode);
    }

    public void goActivity(Class<?> descClass, Bundle bundle) {
        goActivityForResult(descClass, bundle, -1);
    }

    public void goActivity(Class<?> descClass) {
        goActivity(descClass, null);
    }

    public void setText(int viewId, String text) {
        TextView tv = getViewById(viewId);
        tv.setText(text);
    }

    public void setText(int viewId, Spanned text) {
        TextView tv = getViewById(viewId);
        tv.setText(text);
    }

    protected String getTextContent(int viewId) {
        TextView tv = getViewById(viewId);
        return tv.getText().toString().trim();
    }

    protected void setViewOnclickListener(int viewId, View.OnClickListener listener) {
        View view = getViewById(viewId);
        view.setOnClickListener(listener);
    }



    /**
     * @param drawableId
     * @return mContext.getResources().getDrawable(drawableId)
     */
    public Drawable getResDrawable(int drawableId) {
        return mContext.getResources().getDrawable(drawableId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(BaseToolbarActivity.this);
        NetBroadcast.event=this;
        return isNetConnect();

        // if (netMobile == 1) {
        // System.out.println("inspectNet：连接wifi");
        // } else if (netMobile == 0) {
        // System.out.println("inspectNet:连接移动数据");
        // } else if (netMobile == -1) {
        // System.out.println("inspectNet:当前没有网络");
        //
        // }
    }

    /**
     * 网络变化之后的类型
     * @param netMobile
     */
    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        this.netMobile = netMobile;
        isNetConnect();

    }

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;

        }
        return false;
    }
}

