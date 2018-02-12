package com.radiogroup.baselibrary.activity.testslidingtab;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.radiogroup.baselibrary.R;
import com.radiogroup.baselibrary.slidingtab.MyViewPager;
import com.radiogroup.baselibrary.slidingtab.PagerSlidingTabStrip;
import com.radiogroup.baselibrary.slidingtab.TextWithIconTabInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/5.
 */

public class TabActivity extends AppCompatActivity {
    private ArrayList<Fragment> fragments;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private View parentView;
    private LocalViewPagerAdapter viewPagerAdapter;
    private MyViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_local_container);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabStrip);
        viewPager = (MyViewPager) findViewById(R.id.viewPager);


        viewPagerAdapter = new LocalViewPagerAdapter(getSupportFragmentManager(),viewPager);
        setViewPagerList();
        viewPager.setAdapter(this.viewPagerAdapter);
        initPagerSlidingTabStrip();



    }




    private void initPagerSlidingTabStrip() {
        this.pagerSlidingTabStrip.setViewPager(this.viewPager);
        this.pagerSlidingTabStrip.setUnderlineHeight(dp2px(this,0));
        this.pagerSlidingTabStrip.setIndicatorColor(Color.BLACK);
        this.pagerSlidingTabStrip.setIndicatorHeight(dp2px(this, 2.5f));
        this.pagerSlidingTabStrip.setIndicatorPadding(dp2px(this, 20));
        this.pagerSlidingTabStrip.setTypeface(null, 0);
        this.pagerSlidingTabStrip.setIsShowDivider(false);
        this.pagerSlidingTabStrip.setTextSize(dp2px(this, 16));
        this.pagerSlidingTabStrip.setTextColor(Color.BLACK);
        this.pagerSlidingTabStrip.setDividerPadding(dp2px(this, 30));
        this.pagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int i, float v, int i1) {
            }

            public void onPageSelected(int position) {

            }

            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }

    private void setViewPagerList() {
        if (this.fragments == null) {
            this.fragments = new ArrayList();
        }
        this.fragments.clear();
        ArrayList<TextWithIconTabInfo> titles = new ArrayList();

        TextWithIconTabInfo textWithIconTabInfo;
        for (int i=0; i<10; i++){
            textWithIconTabInfo = new TextWithIconTabInfo();
            textWithIconTabInfo.setTitle("头条"+i);
            textWithIconTabInfo.setShowIcon(false);

            titles.add(textWithIconTabInfo);
            fragments.add(new ForumFragment("头条"+i));
        }


        viewPagerAdapter.setFragments(fragments);
        viewPagerAdapter.setTitles(titles);
    }



    private boolean resetViewPage() {

        boolean isRecommendTagsChange = true;
        if (isRecommendTagsChange) {
            scrollToFirst();
            //this.recommendTags =;
            setViewPagerList();
            this.viewPagerAdapter.updateViewPager();
            this.pagerSlidingTabStrip.notifyDataSetChanged();

            return isRecommendTagsChange;
        }

        return true;
    }


    private void scrollToFirst() {
        this.viewPager.setCurrentItem(0);
        this.pagerSlidingTabStrip.scrollToChild(0, 0);
    }





}
