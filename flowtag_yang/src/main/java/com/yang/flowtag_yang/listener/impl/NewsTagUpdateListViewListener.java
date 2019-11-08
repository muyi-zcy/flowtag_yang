package com.yang.flowtag_yang.listener.impl;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;


import com.yang.flowtag_yang.FlowLayout;
import com.yang.flowtag_yang.TagInfo;
import com.yang.flowtag_yang.ViewHolder;
import com.yang.flowtag_yang.listener.OnTagClickListener;

import java.util.ArrayList;
import java.util.List;

public class NewsTagUpdateListViewListener extends OnUpdateListUIListener {

    private SparseArray<ArrayList<TagInfo>> sparseArray;

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    private OnTagClickListener onTagClickListener;

    @Override
    public void onUpdateUI(final Context context, final ViewHolder holder, final int position) {
        final ArrayList<TagInfo> tagInfos = sparseArray.get(position);
        if (tagInfos != null) {
            final FlowLayout newsTag = (FlowLayout)holder.getConvertView();
            newsTag.setTags(tagInfos);
            newsTag.setOnTagClickListener(new OnTagClickListener() {
                @Override
                public void onTagClick(TagInfo tagInfo) {
                    if (onTagClickListener != null) {
                        onTagClickListener.onTagClick(tagInfo);
                    }
                }

                @Override
                public void onTagDelete(TagInfo tagInfo) {

                }
            });
        }
    }

    @Override
    public int getCount() {
        return sparseArray.size();
    }

    @Override
    public void setData(List list) {

    }

    public void setData(SparseArray<ArrayList<TagInfo>> sparseArray) {
        this.sparseArray = sparseArray;
    }

    @Override
    public void setCount(int count) {
        mCount = count;
    }

    @Override
    public View initLayout(Context context, int position) {
        return new FlowLayout(context);
    }
}
