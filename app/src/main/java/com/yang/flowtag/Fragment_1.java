package com.yang.flowtag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.yang.slidingtab_yang.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;

public class Fragment_1 extends Fragment {


    private static final String TAG = Fragment_1.class.getSimpleName();
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager pager;

    ImageView add;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_1, container, false);

        List<String> stringList = JsonUtil.toList((String) SharedPreferencesUtil.getParem(getContext(), "tag", "tag"), String.class);
        if(stringList!=null) {
            for (String s : stringList) {
                System.out.println(s);
            }
        }

        add=(ImageView)v.findViewById(R.id.add);

        pager = (ViewPager)v.findViewById(R.id.pager);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip)v.findViewById(R.id.tabs);
        // 设置Tab的分割线的颜色
        final ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("TAB " + i);
        }
        pager.setAdapter(new SlidingTabPagerAdapter(getChildFragmentManager(), list));
        mPagerSlidingTabStrip.setViewPager(pager);
        pager.setCurrentItem(0);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),TagActivity.class);
                intent.putStringArrayListExtra("tagsDefault", list);
                intent.putStringArrayListExtra("tagsRecommend", list);
                intent.putStringArrayListExtra("tag", list);
                startActivity(intent);
            }
        });

        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected:" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPagerSlidingTabStrip.setOnPagerTitleItemClickListener(new PagerSlidingTabStrip.OnPagerTitleItemClickListener() {
            @Override
            public void onSingleClickItem(int position) {
                Toast.makeText(getContext(), "单击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDoubleClickItem(int position) {
                Toast.makeText(getContext(), "双击", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

}