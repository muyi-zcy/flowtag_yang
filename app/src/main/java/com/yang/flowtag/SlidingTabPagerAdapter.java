package com.yang.flowtag;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class SlidingTabPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titles;

    public SlidingTabPagerAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.titles = list;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle b = new Bundle();
        b.putString("title", titles.get(position)+"............");
        return FragmentContent.getInstance(b);
    }
}