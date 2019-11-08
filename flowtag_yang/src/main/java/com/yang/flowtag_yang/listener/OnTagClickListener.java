package com.yang.flowtag_yang.listener;


import com.yang.flowtag_yang.TagInfo;

public interface OnTagClickListener {
    void onTagClick(TagInfo tagInfo);

    void onTagDelete(TagInfo tagInfo);
}
