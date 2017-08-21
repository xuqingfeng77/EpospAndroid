package com.eposp.android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.eposp.android.util.ImageLoaderUtils;


public class ABViewHolder {
    private SparseArray<View> viewArray;
    private int position;
    private View mConvertView;
    private Context mContext;

    /**
     * 构造方法
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     */
    public ABViewHolder(Context context, View convertView, ViewGroup parent,
                        int layoutId, int position) {
        this.mContext = context;
        this.position = position;
        viewArray = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConvertView.setTag(this);
    }

    public static ABViewHolder get(Context context, View convertView,
                                   ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            // 如果convertView为空，则实例化ABViewHolder
            return new ABViewHolder(context, convertView, parent, layoutId,
                    position);
        } else {
            // 否则从convertView的Tag中取出ABViewHolder，避免重复创建
            ABViewHolder holder = (ABViewHolder) convertView.getTag();
            holder.position = position;
            return holder;
        }
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return position;
    }

    /**
     * 通过viewId获取控件对象
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = viewArray.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            viewArray.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * ------------------------------------华丽的分割线------------------------------
     */
    /** 以下方法为额外封装的方法，只是简单几个，以后可以慢慢完善 */
    /**
     * 设置TextView的内容
     *
     * @param viewId
     * @param text
     * @return
     */
    public ABViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置TextView的内容
     *
     * @param viewId
     * @param text   ，Spanned类型，可设置部分字体变色
     * @return
     */
    public ABViewHolder setText(int viewId, Spanned text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置TextView的drawTop图片
     *
     * @param viewId
     * @param url    ，drawTop 图片url 或者 resource
     * @return
     */
    public ABViewHolder setTextDrawTop(int viewId, String text, int url) {
        TextView tv = getView(viewId);
        tv.setText(text);
        Drawable rightDrawable = mContext.getResources().getDrawable(url);
        rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        tv.setCompoundDrawables(null, null, rightDrawable, null);
        return this;
    }

    /**
     * 设置TextView的内容
     *
     * @param viewId
     * @param color  颜色
     * @return
     */
    public ABViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 设置TextView的内容
     * tv.setTextColor(R.color.textColor_black);这样写是不行的
     * 要从resources中获取
     * tv.setTextColor(this.getResources().getColor(R.color.textColor_black));
     *
     * @param viewId
     * @param text
     * @param color  颜色
     * @return
     */
    public ABViewHolder setTextAndColor(int viewId, String text, int color) {
        TextView tv = getView(viewId);
        tv.setText(text);
        tv.setTextColor(mContext.getResources().getColor(color));
        return this;
    }

    /**
     * @param viewId
     * @param textSize 设置文本字体大小
     * @return
     */
    public ABViewHolder setTextSize(int viewId,float textSize){
        TextView tv = getView(viewId);
        tv.setTextSize(textSize);
        return this;
    }

    /**
     * 设置View背景色
     *
     * @param viewId
     * @param color
     * @return
     */
    public ABViewHolder setViewBackground(int viewId, int color) {
        View v = getView(viewId);
        v.setBackgroundColor(mContext.getResources().getColor(color));
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ABViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ABViewHolder setImageByUrl(int viewId, String url) {
        ImageView view = getView(viewId);
        ImageLoaderUtils.loadBitmap(url, view);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @param defResid
     * @return
     */
    public ABViewHolder setImageByUrl(int viewId, String url, int defResid) {
        ImageView view = getView(viewId);
        ImageLoaderUtils.loadBitmap(url, view, defResid, defResid);
        return this;
    }

    public ABViewHolder setBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * @param viewId
     * @param onClickListener
     * @return 设置RadioButton监听
     */
    public ABViewHolder setRbtnOnClick(int viewId, OnClickListener onClickListener) {

        RadioButton rbtn = getView(viewId);
        rbtn.setOnClickListener(onClickListener);

        return this;
    }

    /**
     * @param viewId
     * @param onClickListener
     * @return 设置Button监听
     */
    public ABViewHolder setBtnOnClick(int viewId, OnClickListener onClickListener) {
        Button btn = getView(viewId);
        btn.setOnClickListener(onClickListener);
        return this;
    }

    /**
     * @param viewId
     * @param onClickListener
     * @return 设置TextView监听
     */
    public ABViewHolder setTvOnClick(int viewId, OnClickListener onClickListener) {
        TextView tv = getView(viewId);
        tv.setOnClickListener(onClickListener);
        return this;
    }


    /**
     * 获取TextView文本值
     *
     * @return
     */
    public String getText(int viewId) {
        TextView tv = getView(viewId);
        return tv.getText().toString();
    }

}
