package com.radiogroup.baselibrary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.radiogroup.baselibrary.mainfragment.FindFragment;
import com.radiogroup.baselibrary.mainfragment.HomeFragment;
import com.radiogroup.baselibrary.mainfragment.MessageFragment;
import com.radiogroup.baselibrary.mainfragment.NewFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private NewFragment mNewFragment;
    private MessageFragment mMessageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.home_rb).setOnClickListener(this);
        findViewById(R.id.find_rb).setOnClickListener(this);
        findViewById(R.id.new_rb).setOnClickListener(this);
        findViewById(R.id.message_rb).setOnClickListener(this);

        // 默认一进入页面就添加主Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mHomeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.main_tab_fl, mHomeFragment);
        // 最后记得提交
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_rb:
                homeRbClick();
                break;
            case R.id.find_rb:
                findRbClick();
                break;
            case R.id.new_rb:
                newRbClick();
                break;
            case R.id.message_rb:
                messageRbClick();
                break;
        }
    }


    private void homeRbClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        fragmentTransaction.show(mHomeFragment);
        fragmentTransaction.commit();
    }


    private void findRbClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        if(mFindFragment == null){
            mFindFragment = new FindFragment();
            fragmentTransaction.add(R.id.main_tab_fl,mFindFragment);
        }else {
            fragmentTransaction.show(mFindFragment);
        }

        fragmentTransaction.commit();
    }


    private void newRbClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        if(mNewFragment == null){
            mNewFragment = new NewFragment();
            fragmentTransaction.add(R.id.main_tab_fl,mNewFragment);
        }else {
            fragmentTransaction.show(mNewFragment);
        }

        fragmentTransaction.commit();
    }

    private void messageRbClick() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        if(mMessageFragment == null){
            mMessageFragment = new MessageFragment();
            fragmentTransaction.add(R.id.main_tab_fl,mMessageFragment);
        }else {
            fragmentTransaction.show(mMessageFragment);
        }

        fragmentTransaction.commit();
    }









}
