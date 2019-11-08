package com.yang.flowtag_yang;

import android.text.TextPaint;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 流式布局标签根据内容长度自动排列工具类
 */
public class FlowLayoutUtils {
    /**
     *
     * @param tagInfos 需要排列的标签数据
     * @param width 一行标签的宽度
     * @param textSize
     * @param textViewSpacing 标签的间距
     * @param padding 标签文字和边界的距离
     * @return
     */
    public static SparseArray<ArrayList<TagInfo>> getRow(List<TagInfo> tagInfos, int width, int textSize, int textViewSpacing, int padding) {
        SparseArray<ArrayList<TagInfo>> sparseArray = new SparseArray();
        int totalWidth = 0;
        int row = 0;
        int measuredWidth;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        ArrayList<TagInfo> tagInfoList;
        for (int i = 0; i < tagInfos.size(); i++) {
            measuredWidth = (int) (textPaint.measureText(tagInfos.get(i).tagName) + padding);
            if (totalWidth == 0) {
                totalWidth += measuredWidth;
            } else {
                totalWidth += measuredWidth + textViewSpacing;
            }
            if (totalWidth > width) {
                row++;
                totalWidth = measuredWidth;
            }
            tagInfoList = sparseArray.get(row);
            if (tagInfoList == null) {
                tagInfoList = new ArrayList<>();
                sparseArray.put(row,tagInfoList);
            }
            tagInfoList.add(tagInfos.get(i));
        }
        return sparseArray;
    }

    /**
     * 根据内容排列标签
     * @param tagInfos
     * @param marginTop
     * @param width
     * @param textSize
     * @param height
     * @param textViewSpacing
     * @param verticalSpacing
     * @param padding
     * @param onGetTagListener
     * @return
     */
    public static SparseArray<ArrayList<TagInfo>> getTagRects(List<TagInfo> tagInfos, int marginTop, int width, int textSize, int height, int textViewSpacing, int verticalSpacing, int padding, onGetTagListener onGetTagListener) {
        SparseArray<ArrayList<TagInfo>> sparseArray = new SparseArray();
        ArrayList<TagInfo> tagInfoList;
        int totalWidth = 0;
        int row = 0;
        int measuredWidth;
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(textSize);
        TagInfo tagInfo;
        for (int i = 0; i < tagInfos.size(); i++) {
            tagInfo = tagInfos.get(i);
            measuredWidth = (int) (textPaint.measureText(tagInfo.tagName) + padding);
            if (totalWidth == 0) {
                totalWidth += measuredWidth;
            } else {
                totalWidth += measuredWidth + textViewSpacing;
            }
            if (totalWidth > width) {
                row++;
                totalWidth = measuredWidth;
            }
            tagInfo.rect.left = totalWidth - measuredWidth;
            tagInfo.rect.top = marginTop + row * (height + verticalSpacing);
            tagInfo.rect.right = totalWidth;
            tagInfo.rect.bottom =marginTop + row * (height + verticalSpacing) + height;
            tagInfoList = sparseArray.get(row);
            if (tagInfoList == null) {
                tagInfoList = new ArrayList<>();
                sparseArray.put(row,tagInfoList);
            }
            tagInfoList.add(tagInfos.get(i));
            if (onGetTagListener != null) {
                onGetTagListener.onGetTag(i,tagInfo);
            }
        }
        return sparseArray;
    }

    public interface onGetTagListener{
        void onGetTag(int position, TagInfo tagInfo);
    }

    /**
     * 标签：数组转List
     * @param tagId
     * @param stringArray
     * @param type
     * @return
     */
    public static List<TagInfo> addTags(String tagId, String[] stringArray, int type) {
        List<TagInfo> list = new ArrayList<>();
        TagInfo tagInfo;
        String name;
        if (stringArray != null && stringArray.length > 0) {
            for (int i = 0; i < stringArray.length; i++) {
                name = stringArray[i];
                tagInfo = new TagInfo();
                tagInfo.type = type;
                tagInfo.tagName = name;
                tagInfo.tagId = tagId + i;
                list.add(tagInfo);
            }
        }
        return list;
    }
}
