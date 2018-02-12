package com.radiogroup.baselibrary.activity.testslidingtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/2/5.
 */

public class ForumFragment extends Fragment {

    String typeg ;

    public ForumFragment(String type){
        typeg = type;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TextView textView = new TextView(getActivity());
        textView.setText(typeg);
        return textView;
    }
}
