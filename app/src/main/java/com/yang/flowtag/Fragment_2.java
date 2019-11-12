package com.yang.flowtag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.yang.slidingtab_yang.PagerSlidingTabStrip;

import java.util.ArrayList;

public class Fragment_2 extends Fragment {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_2, container, false);

        pager = (ViewPager)v.findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip)v.findViewById(R.id.tabs);
        ArrayList<String> titles = new ArrayList<>();
        titles.add("聊天");
        titles.add("联系人");
        titles.add("发现");
        titles.add("我");
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (String s : titles) {
            Bundle bundle = new Bundle();
            bundle.putString("title", s);
            fragments.add(FragmentContent.getInstance(bundle));
        }
        pager.setAdapter(new FixTabPagerAdapter(getChildFragmentManager(), titles, fragments));
        tabs.setViewPager(pager);
        pager.setCurrentItem(1);

        return v;
    }
}