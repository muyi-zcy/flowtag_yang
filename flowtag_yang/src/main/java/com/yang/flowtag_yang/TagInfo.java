package com.yang.flowtag_yang;

import android.graphics.Rect;

public class TagInfo {
    public static final int TYPE_TAG_USER = 0;//可以移动的标签
    public static final int TYPE_TAG_SERVICE = 1;//不能移动的标签，一般是默认放在最前面的标签
    public String tagId;
    public String tagName;
    Rect rect = new Rect();
    public int childPosition;//移动前标签的位置
    public int dataPosition = -1;//移动后标签的位置
    public int type;
}
