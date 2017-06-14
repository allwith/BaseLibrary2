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
import com.radiogroup.baselibrary.activity.testninegridlayout.TestActivity;

/**
 * Created by Administrator on 2017/6/10.
 */

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, null);
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

        return rootView;
    }





}
