package com.yang.flowtag_yang;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.yang.flowtag_yang.adapter.TagListViewAdapter;
import com.yang.flowtag_yang.listener.impl.NewsTagUpdateListViewListener;

import java.util.ArrayList;
import java.util.List;

/**
 * FlowLayout 统一管理器
 */

public class FlowLayoutManager {

    Context context;

    /**
     * 设置“我的标签”固定和非固定标签 标识头
     */
    final public String TYPE_FIX="fix";

    final public String TYPE_DEFAULT="default";

    final public String TYPE_FLAG="flag";

    public int maxNum=100;

    private FlowLayout flowLayout;
    private ListView recommendNewsTagListView;
    /**
     * “推荐标签”点击监听
     */
    private NewsTagUpdateListViewListener newsTagUpdateListViewListener = new NewsTagUpdateListViewListener();

    /**
     * “推荐标签"适配器
     */
    private TagListViewAdapter tagListViewAdapter;


    /**
     * “推荐”标签
     */
    private List<TagInfo> recommondTagInfos = new ArrayList<>();

    /**
     * “我的”标签
     */
    private ArrayList<TagInfo> myTagInfos = new ArrayList<>();

    /**
     *  初始选中标签ID
     */
    private int currentId=-2;


    /**
     *
     */
    /**
     * 是否可编辑
     */
    private boolean isEdit;


    /**
     * 获取最终"我的标签"
     * @return
     */
    public ArrayList<String> endData(){
        ArrayList<String> tagList=new ArrayList<String>();
        for(TagInfo tagInfo:getMyTagInfos()){
            tagList.add(tagInfo.tagName);
        }
        return tagList;
    }

    public FlowLayoutManager(Context context, View view, int newsTag, int news_tag_list_view){
        this.context=context;
        this.flowLayout=view.findViewById(newsTag);
        this.recommendNewsTagListView=view.findViewById(news_tag_list_view);
        tagListViewAdapter=new TagListViewAdapter(context, newsTagUpdateListViewListener);
    }

    public FlowLayoutManager(Context context, List<TagInfo> recommondTagInfos, ArrayList<TagInfo> myTagInfos ){
        this.context=context;
        this.recommondTagInfos=recommondTagInfos;
        this.myTagInfos=myTagInfos;
    }

    /**
     * 获取推荐列表,根据数据和屏幕信息，对每一行的数据进行分配
     */
    public SparseArray<ArrayList<TagInfo>> getListSparseArray() {
        return FlowLayoutUtils.getRow(
                //数据
                recommondTagInfos
                // dp
                , ViewSizeUtil.getCustomDimen(context, 330f)
                // textSize
                , (int) (ViewSizeUtil.getCustomDimen(context, 16f))
                // 标签的间距
                , (int) (ViewSizeUtil.getCustomDimen(context, 15f))
                // 标签文字和边界的距离
                , (int) (ViewSizeUtil.getCustomDimen(context, 26f))
        );
    }



    public void setSelectTagId(int tagId){
        currentId=tagId;
    }


    public void setListFormatData(){
        SparseArray<ArrayList<TagInfo>> sparseArray = getListSparseArray();
        getNewsTagUpdateListViewListener().setData(sparseArray);

    }
    /**
     *
     */
    public void editToDefault(int color){
        isEdit = true;
        if (flowLayout.getSelectButton() != null) {
            setUnSelectTag(flowLayout.getSelectButton(),color);
        }

        initTagDrag();
    }

    public void defaultToEdit(int color){
        isEdit = false;
        if (flowLayout.getSelectButton() != null) {
            setSelectTag(flowLayout.getSelectButton(),color);
        }
        initTagDefault();
    }

    /**
     * 删除标签
     * @param tagInfo
     */
    public void deleteTag(TagInfo tagInfo){
        //把删除的标签
        getRecommondTagInfos().add(0,tagInfo);
        setListFormatData();
        tagListViewAdapter.notifyDataSetChanged();
    }

    /**
     * 添加标签
     * @param tagInfo
     */
    public void addTag(TagInfo tagInfo){
        getRecommondTagInfos().remove(tagInfo);//移除
        setListFormatData();
        //待选标签
        flowLayout.addTag(tagInfo, isEdit);
        tagListViewAdapter.notifyDataSetChanged();
        TagInfo info = getMyTagInfos().get(getMyTagInfos().size() - 1);
        currentId = Integer.parseInt(info.tagId.substring(info.tagId.length()-1));
    }


    public void setTags(String[] tagsDefault, String[] tagsRecommend, String[] tagsRecommond){
        if(currentId==-1){
            flowLayout.setSelectTagId(""+currentId);
        }else if(currentId<tagsDefault.length){
            flowLayout.setSelectTagId(TYPE_FIX+currentId);
        }else{
            flowLayout.setSelectTagId(TYPE_DEFAULT+(currentId-tagsDefault.length));
        }
        //我的标签：不可编辑标签
        getMyTagInfos().addAll(FlowLayoutUtils.addTags(TYPE_FIX, tagsDefault, TagInfo.TYPE_TAG_SERVICE));

        //我的标签：可编辑标签
        getMyTagInfos().addAll(FlowLayoutUtils.addTags(TYPE_DEFAULT, tagsRecommend, TagInfo.TYPE_TAG_USER));

        //待选标签
        getRecommondTagInfos().addAll(FlowLayoutUtils.addTags(TYPE_FLAG, tagsRecommond, TagInfo.TYPE_TAG_USER));
        flowLayout.setTags(getMyTagInfos());
        tagListViewAdapter.notifyDataSetChanged();
        setListFormatData();
        tagListViewAdapter.notifyDataSetChanged();
        recommendNewsTagListView.setAdapter(tagListViewAdapter);
    }


    /**
     * 状态改变：允许滑动;可编辑
     */
    public void initTagDrag() {
        flowLayout.enableDragAndDrop();//允许滑动
        flowLayout.setIsEdit(true);
    }

    /**
     * 状态改变：不允许滑动;不可编辑
     */
    public void initTagDefault() {
        flowLayout.setDefault(); //设置标签默认状态不可滑动
        flowLayout.setIsEdit(false);
    }




    public List<TagInfo> getRecommondTagInfos() {
        return recommondTagInfos;
    }

    public void setRecommondTagInfos(List<TagInfo> recommondTagInfos) {
        this.recommondTagInfos = recommondTagInfos;
    }

    public ArrayList<TagInfo> getMyTagInfos() {
        return myTagInfos;
    }

    public void setMyTagInfos(ArrayList<TagInfo> myTagInfos) {
        this.myTagInfos = myTagInfos;
    }

    public FlowLayout getFlowLayout() {
        return flowLayout;
    }

    public void setFlowLayout(FlowLayout flowLayout) {
        this.flowLayout = flowLayout;
    }

    public ListView getRecommendNewsTagListView() {
        return recommendNewsTagListView;
    }

    public void setRecommendNewsTagListView(ListView recommendNewsTagListView) {
        this.recommendNewsTagListView = recommendNewsTagListView;
    }

    public TagListViewAdapter getTagListViewAdapter() {
        return tagListViewAdapter;
    }

    public void setTagListViewAdapter(TagListViewAdapter tagListViewAdapter) {
        this.tagListViewAdapter = tagListViewAdapter;
    }

    public NewsTagUpdateListViewListener getNewsTagUpdateListViewListener() {
        return newsTagUpdateListViewListener;
    }

    public void setNewsTagUpdateListViewListener(NewsTagUpdateListViewListener newsTagUpdateListViewListener) {
        this.newsTagUpdateListViewListener = newsTagUpdateListViewListener;
    }

    /**
     * 初始化的被选中标签
     * @param textView
     */
    public static void setSelectTag(TextView textView, int color){
        textView.setBackgroundResource(R.drawable.tag_select);
        textView.setTextColor(color);
    }

    /**
     * 初始化的未被选择的标签
     * @param textView
     */
    public static void setUnSelectTag(TextView textView, int color){
        textView.setBackgroundResource(R.drawable.round_rect_gray);
        textView.setTextColor(color);
    }
    public static void setGraySelectTag(TextView textView, int color){
        textView.setBackgroundResource(R.drawable.tag_uncheck);
        textView.setTextColor(color);
    }
}
