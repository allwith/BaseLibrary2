package com.radiogroup.baselibrary.mainfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radiogroup.baselibrary.R;

/**
 * Created by Administrator on 2017/6/10.
 */

public class NewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_layout, null);
        TextView contentTv = (TextView)
                rootView.findViewById(R.id.content_tv);
        contentTv.setText("新鲜");
        return rootView;
    }



}
