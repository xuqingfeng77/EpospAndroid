package com.eposp.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eposp.android.R;


/**
 * Created by zw on 2016/7/13 0013.
 */
public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    private TextView tv_back, tv_title, tv_right;
    private String titleStr;
    private String rightStr;
    private int color;
    private boolean showLeft;
    private boolean showRight;
    private Drawable leftDrawable;
    private Drawable rightDrawable;

    private LeftBtnOnClickListener leftBtnOnClickListener;
    private RightBtnOnClickListener rightBtnOnClickListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        titleStr = mTypedArray.getString(R.styleable.TitleBar_centerTitle);
        rightStr = mTypedArray.getString(R.styleable.TitleBar_rightTitle);
        color = mTypedArray.getColor(R.styleable.TitleBar_titleBarBg
                , getResources().getColor(R.color.titlebar_bg_color));
        showLeft = mTypedArray.getBoolean(R.styleable.TitleBar_showLeft, true);
        showRight = mTypedArray.getBoolean(R.styleable.TitleBar_showRight, false);
        leftDrawable = mTypedArray.getDrawable(R.styleable.TitleBar_leftDrawable);
        rightDrawable = mTypedArray.getDrawable(R.styleable.TitleBar_rightDrawable);

        View view = LayoutInflater.from(context).inflate(R.layout.view_titlebar, this, true);
        tv_back = (TextView) view.findViewById(R.id.tv_back);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        initView();
        mTypedArray.recycle();
    }

    private void initView() {
        this.setBackgroundColor(color);

        tv_back.setVisibility(showLeft ? VISIBLE : GONE);

        if (leftDrawable != null) {
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
            tv_back.setCompoundDrawables(leftDrawable, null, null, null);
        }

        if (titleStr != null) {
            tv_title.setText(titleStr);
        }

        tv_right.setVisibility(showRight ? View.VISIBLE : View.GONE);
        if (rightStr != null) {
            tv_right.setText(rightStr);
        }

        if (rightDrawable != null) {
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            tv_right.setCompoundDrawables(null, null, rightDrawable, null);
        }

        tv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_back) {
            if (leftBtnOnClickListener == null) {
                ((Activity) mContext).finish();
            } else {
                leftBtnOnClickListener.onClick(v);
            }
        } else if (i == R.id.tv_right) {
            if(rightBtnOnClickListener != null){
                rightBtnOnClickListener.onClick(v);
            }
        }
    }

    public void setTitleText(String text){
        tv_title.setText(text);
    }

    public void setLeftText(String text){
        if(tv_back != null){
            tv_back.setText(text);
        }
    }

    public void setRightText(String text){
        tv_right.setText(text);
    }

    public void setLeftResource(int resid) {
        Drawable drawable = mContext.getResources().getDrawable(resid);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_back.setCompoundDrawables(drawable, null, null, null);
    }

    public void setRightResource(int resid) {
        Drawable drawable = mContext.getResources().getDrawable(resid);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_right.setCompoundDrawables(null, null, drawable, null);
    }
    public void setLeftBtnOnClickListener(LeftBtnOnClickListener leftBtnOnClickListener) {
        this.leftBtnOnClickListener = leftBtnOnClickListener;
    }

    public void setRightBtnOnClickListener(RightBtnOnClickListener rightBtnOnClickListener) {
        this.rightBtnOnClickListener = rightBtnOnClickListener;
    }

    public interface LeftBtnOnClickListener{
        void onClick(View view);
    }

    public interface RightBtnOnClickListener{
        void onClick(View view);
    }

    /**
     * @param text 设置左边文字
     */
    public void setLeftTextView(CharSequence text) {
        tv_back.setText(text);
    }

    /**
     * @param text 设置中间标题文字
     */
    public void setTiteTextView(CharSequence text) {
        tv_title.setText(text);
    }

    /**
     * @param text 设置右边按钮文字
     */
    public void setRightTextView(CharSequence text) {
        tv_right.setText(text);
    }

    /**
     * 动态设置左边按钮是否显示
     */
    public void setShowLeft(int visibility) {
        tv_back.setVisibility(visibility);
    }

    /**
     * 动态设置右边按钮是否显示
     */
    public void setShowRight(int visibility) {
        tv_right.setVisibility(visibility);
    }

    /**
     * 获得标题
     */
    public String getCenterText() {
        return tv_title.getText().toString();
    }
    /**
     * 获得右边的文本
     */
    public String getRightText() {
        return tv_right.getText().toString();
    }


}
