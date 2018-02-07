package com.radiogroup.baselibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.radiogroup.baselibrary.mainfragment.FindFragment;
import com.radiogroup.baselibrary.mainfragment.FragmentManagerHelper;
import com.radiogroup.baselibrary.mainfragment.HomeFragment;
import com.radiogroup.baselibrary.mainfragment.MessageFragment;
import com.radiogroup.baselibrary.mainfragment.NewFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private NewFragment mNewFragment;
    private MessageFragment mMessageFragment;

    private FragmentManagerHelper mFragmentHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

    }


    private void initData(){
        findViewById(R.id.home_rb).setOnClickListener(this);
        findViewById(R.id.find_rb).setOnClickListener(this);
        findViewById(R.id.new_rb).setOnClickListener(this);
        findViewById(R.id.message_rb).setOnClickListener(this);

        mFragmentHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.main_tab_fl);
        mHomeFragment = new HomeFragment();
        mFragmentHelper.add(mHomeFragment);

        ///dfdsf
//         默认一进入页面就添加主Fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        mHomeFragment = new HomeFragment();
//        fragmentTransaction.add(R.id.main_tab_fl, mHomeFragment);
//        // 最后记得提交
//        fragmentTransaction.commit();
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
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (mHomeFragment == null){
            mHomeFragment = new HomeFragment();
        }

        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        if (!fragments.contains(mHomeFragment)){
            fragmentTransaction.add(R.id.main_tab_fl, mHomeFragment);
        }else {
            fragmentTransaction.show(mHomeFragment);
        }

        fragmentTransaction.commit();*/

        if (mHomeFragment == null){
            mHomeFragment = new HomeFragment();
        }
        mFragmentHelper.switchFragment(mHomeFragment);
    }


    private void findRbClick() {
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(mFindFragment == null){
            mFindFragment = new FindFragment();
        }

        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        if(!fragments.contains(mFindFragment)){
            fragmentTransaction.add(R.id.main_tab_fl, mFindFragment);
        }else {
            fragmentTransaction.show(mFindFragment);
        }

        fragmentTransaction.commit();*/
        if(mFindFragment == null){
            mFindFragment = new FindFragment();
        }
        mFragmentHelper.switchFragment(mHomeFragment);
    }


    private void newRbClick() {
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(mNewFragment == null){
            mNewFragment = new NewFragment();
        }

        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        if(!fragments.contains(mNewFragment)){
            fragmentTransaction.add(R.id.main_tab_fl, mNewFragment);
        }else {
            fragmentTransaction.show(mNewFragment);
        }

        fragmentTransaction.commit();*/

        if(mNewFragment == null){
            mNewFragment = new NewFragment();
        }
        mFragmentHelper.switchFragment(mNewFragment);
    }

    private void messageRbClick() {
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }

            List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }

        if(!fragments.contains(mMessageFragment)  ){
            fragmentTransaction.add(R.id.main_tab_fl, mMessageFragment);
        }else {
            fragmentTransaction.show(mMessageFragment);
        }

        fragmentTransaction.commit();*/

        if(mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }
        mFragmentHelper.switchFragment(mMessageFragment);
    }









}
