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
 *@desc   : 基础模块放到基础库中，方便各个模块使用；标题栏用使用toolbar，此类就可以满足基本需求，如果不需要Toolbar,则需要继承BaseActivity
 *@update :
 */
public abstract class BaseToolbarActivity extends BaseActivity{

    private ToolBarHelper mToolBarHelper ;
    public Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setContentView(@LayoutRes int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this,layoutResID) ;
        toolbar = mToolBarHelper.getToolBar() ;
        setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar) ;
    }

    /**
     * 默认结束当前页面
     * @param toolbar
     */
    public void onCreateCustomToolBar(Toolbar toolbar){
        toolbar.setContentInsetsRelative(0,0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    /**
     * 设置标题 id
     * @param resId
     */
    public void setToolbarTitle(int resId) {
        if (resId != 0) {
            setToolbarTitle(getString(resId));
        }
    }

    /**
     * 设置标题 str
     * @param title
     */
    public void setToolbarTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        if (hasActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }

}

