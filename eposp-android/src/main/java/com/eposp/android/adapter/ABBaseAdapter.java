package com.eposp.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.eposp.android.util.ScreenSwitch;

import java.util.ArrayList;
import java.util.List;

public abstract class ABBaseAdapter<E> extends BaseAdapter {
    public List<E> list = new ArrayList<E>();

    public Context mContext;

    public ABBaseAdapter(Context context) {
        super();
        this.mContext = context;
    }

    public void add(E e) {
        this.list.add(e);
        notifyDataSetChanged();
    }

    public void addAll(List<E> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<E> getDatas() {
        return list;
    }

    public void remove(int position) {
        this.list.remove(position);
        notifyDataSetChanged();
    }

    public void setList(List<E> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void removeAll() {
        this.list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public E getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ABViewHolder holder = ABViewHolder.get(mContext, convertView, parent, getLayoutId(), position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public abstract int getLayoutId();

    public abstract void convert(ABViewHolder holder, E model);



    public void goActivity(Class<?> descClass) {
        goActivityForResult(descClass,0);
    }

    public void goActivityForResult(Class<?> descClass, int requestCode){
        ScreenSwitch.switchActivity(mContext, descClass, null, requestCode);
    }

}
