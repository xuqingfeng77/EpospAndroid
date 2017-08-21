package com.eposp.android.ui;

/**
 * @author :xqf
 * @date :2017/8/19  下午6:02
 * @desc :
 * @update :
 */
public interface  BaseViewInfterface {
    /**
     * @return 布局id
     */
     public int getLayoutId();

    /**
     * 初始化控件
     */
    public void initView();

    /**
     * 事件绑定
     */
    public void eventOnClick();
}
