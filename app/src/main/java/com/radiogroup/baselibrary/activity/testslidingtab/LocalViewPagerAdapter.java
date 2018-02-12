package com.radiogroup.baselibrary.activity.testslidingtab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.radiogroup.baselibrary.slidingtab.MyViewPager;
import com.radiogroup.baselibrary.slidingtab.PagerSlidingTabStrip;
import com.radiogroup.baselibrary.slidingtab.TextWithIconTabInfo;

import java.util.ArrayList;
import java.util.List;

/*
            setViewPagerList();
            this.viewPagerAdapter.updateViewPager();
            this.pagerSlidingTabStrip.notifyDataSetChanged();
* */
public class LocalViewPagerAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.TextWithIconTabProvider {
    private ArrayList<Fragment> fragments = new ArrayList();
    private List<TextWithIconTabInfo> titles = new ArrayList();
    private MyViewPager viewPager;

    public LocalViewPagerAdapter(FragmentManager fragmentManager, MyViewPager viewPager) {
        super(fragmentManager);
        this.viewPager = viewPager;
    }

    public Fragment getItem(int position) {
        return (Fragment) this.fragments.get(position);
    }

    public CharSequence getPageTitle(int position) {
        return ((TextWithIconTabInfo) this.titles.get(position)).getTitle();
    }

    public int getCount() {
        return this.titles.size();
    }

    public int getItemPosition(Object object) {
        return -2;
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments.clear();
        this.fragments.addAll(fragments);
    }

    public void setTitles(ArrayList<TextWithIconTabInfo> titles) {
        this.titles.clear();
        this.titles.addAll(titles);
    }

    public void updateViewPager() {
        this.viewPager.removeAllViews();
        notifyDataSetChanged();
    }

    public ArrayList<Fragment> getCurrentFragments() {
        return this.fragments;
    }

    public TextWithIconTabInfo getTextWithIconTabInfo(int position) {
        return (TextWithIconTabInfo) this.titles.get(position);
    }
}
