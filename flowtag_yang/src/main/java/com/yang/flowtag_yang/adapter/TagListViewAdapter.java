package com.yang.flowtag_yang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yang.flowtag_yang.ViewHolder;
import com.yang.flowtag_yang.listener.impl.OnUpdateListUIListener;


public class TagListViewAdapter extends BaseAdapter {


    public void setOnUpdateUIListener(OnUpdateListUIListener onUpdateUIListener) {
        this.onUpdateUIListener = onUpdateUIListener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public OnUpdateListUIListener getOnUpdateUIListener() {
        return onUpdateUIListener;
    }

    protected OnUpdateListUIListener onUpdateUIListener;
    protected Context context;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = onUpdateUIListener.initLayout(context,position);
            viewHolder = new ViewHolder(convertView,context);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if (onUpdateUIListener != null) {
            viewHolder.position = position;
            onUpdateUIListener.onUpdateUI(context, viewHolder, position);
        }
        return convertView;
    }


    @Override
    public int getCount() {
        return onUpdateUIListener != null ?onUpdateUIListener.getCount() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public TagListViewAdapter(){
        super();
    }

    public TagListViewAdapter(Context context, OnUpdateListUIListener onSetUIListener) {
        this.context = context;
        this.onUpdateUIListener = onSetUIListener;
    }



}
