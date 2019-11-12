package com.yang.flowtag;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yang.flowtag_yang.FlowLayout;
import com.yang.flowtag_yang.FlowLayoutManager;
import com.yang.flowtag_yang.TagInfo;
import com.yang.flowtag_yang.listener.OnTagClickListener;

import java.util.List;

public class TagActivity extends AppCompatActivity {
    Context context;

    private FlowLayout flowLayout;

    private FlowLayoutManager flowLayoutManager;

    private int currentId;

    TextView tag_drag_tips;
    TextView tag_my_sort;
    TextView tag_edit;

    String[] tagsDefault;
    String[] tagsRecommend;
    String[] tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        context =this;
        setContentView(R.layout.activity_tag);

        //初始化控件
        init();

        //获取数据
        getInitData();

        /**
         * 添加点击事件监听
         */

        /**
         * ”我的分类“标签点击事件监控
         */
        flowLayout.setOnTagClickListener(new OnTagClickListener() {
            /**
             * 点击标签
             * @param tagInfo
             */
            @Override
            public void onTagClick(TagInfo tagInfo) {
                flowLayoutManager.getMyTagInfos();
            }

            /**
             * 删除标签
             * @param tagInfo
             */
            @Override
            public void onTagDelete(TagInfo tagInfo) {
                flowLayoutManager.deleteTag(tagInfo);
                setMyTagNum();
            }
        });

        /**
         *“推荐分类”事件监听
         */
        flowLayoutManager.getNewsTagUpdateListViewListener().setOnTagClickListener(new OnTagClickListener() {
            /**
             * 点击事件
             * @param tagInfo
             */
            @Override
            public void onTagClick(TagInfo tagInfo) {
                if (flowLayoutManager.getMyTagInfos().size() >= flowLayoutManager.maxNum) {
                    Toast.makeText(TagActivity.this, "不能超过100个分类", Toast.LENGTH_SHORT).show();
                } else {
                    flowLayoutManager.addTag(tagInfo);
                    setMyTagNum();
                }
            }

            /**
             * 点击删除
             * @param tagInfo
             */
            @Override
            public void onTagDelete(TagInfo tagInfo) {
            }
        });


        /**
         * 设置选中标签
         */
        flowLayoutManager.setSelectTagId(4);

        flowLayoutManager.setTags(tagsDefault,tagsRecommend,tag);

        tag_edit.setVisibility(View.VISIBLE);
        /**
         * “编辑/完成”按钮监听
         */
        tag_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 编辑状态
                 */
                if (((TextView) v).getText().toString().equals("编辑")){

                    flowLayoutManager.editToDefault(0xff645e66);
                    setMyTagNum();
                    tag_drag_tips.setVisibility(View.VISIBLE);
                    tag_drag_tips.setText("拖拽可排序");
                    ((TextView) v).setText("完成");

                } else {
                    /**
                     * 非编辑状态
                     */
                    flowLayoutManager.defaultToEdit(0xffffffff);

                    tag_my_sort.setText("我的分类");
                    ((TextView) v).setText("编辑");
                    tag_drag_tips.setVisibility(View.GONE);
                }
            }
        });


    }


    /**
     * 初始化控件
     */
    public void  init(){
        tag_drag_tips=(TextView)findViewById(R.id.tag_drag_tips);
        tag_my_sort=(TextView)findViewById(R.id.tag_my_sort);
        tag_edit=(TextView)findViewById(R.id.tag_edit);
        flowLayoutManager =new FlowLayoutManager(context,getWindow().getDecorView(),R.id.newsTag,R.id.news_tag_list_view);
        flowLayout=flowLayoutManager.getFlowLayout();
    }

    /**
     * 获取数据
     */
    public void getInitData(){
        List<String> tagsDefaultList=getIntent().getStringArrayListExtra("tagsDefault");
        tagsDefault=new String[tagsDefaultList.size()];
        for(int i=0;i<tagsDefaultList.size();i++){
            tagsDefault[i]=tagsDefaultList.get(i);
        }

        List<String> tagsRecommendList=getIntent().getStringArrayListExtra("tagsRecommend");
        tagsRecommend=new String[tagsRecommendList.size()];
        for(int i=0;i<tagsRecommendList.size();i++){
            tagsRecommend[i]=tagsRecommendList.get(i);
        }

        List<String> tagList=getIntent().getStringArrayListExtra("tag");
        tag=new String[tagList.size()];
        for(int i=0;i<tagList.size();i++){
            tag[i]=tagList.get(i);
        }
    }


    /**
     * 设置“我的分类"数目
     */
    public void setMyTagNum() {
        if (flowLayoutManager.getMyTagInfos().size() != 0) {
            tag_my_sort.setText("我的分类(" + flowLayoutManager.getMyTagInfos().size() + "/"+flowLayoutManager.maxNum + ")");
        } else {
            tag_my_sort.setText("我的分类");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /**
         * 把数据保存在本地
         */
        SharedPreferencesUtil.setParem(context,"tag",JsonUtil.toJSON(flowLayoutManager.endData()));
        finish();
    }
}
