package com.radiogroup.baselibrary.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radiogroup.baselibrary.R;
import com.radiogroup.baselibrary.activity.ListViewActivity;
import com.radiogroup.baselibrary.activity.TestImageChooseActivity;
import com.radiogroup.baselibrary.activity.TestTitleBarActivity;
import com.radiogroup.baselibrary.activity.testninegridlayout.TestActivity;
import com.radiogroup.baselibrary.activity.testslidingtab.TabActivity;

/**
 * Created by Administrator on 2017/6/10.
 */

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container, false);
        TextView contentTv = (TextView) rootView.findViewById(R.id.content_tv);
        contentTv.setText("主页");
        contentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListViewActivity.class));
            }
        });

        TextView contentTv2 = (TextView) rootView.findViewById(R.id.content_tv2);
        contentTv2.setText("测试ninegridlayout");
        contentTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TestActivity.class));
            }
        });

        TextView contentTv3 = (TextView) rootView.findViewById(R.id.content_tv3);
        contentTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TestImageChooseActivity.class));
            }
        });

        rootView.findViewById(R.id.tv_test_title_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TestTitleBarActivity.class));
            }
        });

        rootView.findViewById(R.id.tv_test_sliding_tab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TabActivity.class));
            }
        });

        return rootView;
    }





}
