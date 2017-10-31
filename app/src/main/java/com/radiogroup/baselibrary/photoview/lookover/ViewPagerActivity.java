/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.radiogroup.baselibrary.photoview.lookover;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.radiogroup.baselibrary.R;
import com.radiogroup.baselibrary.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * uk.co.senab.photoview,
 *
 * 用法：
 * Intent intent = new Intent(context, ViewPagerActivity.class);
 intent.putExtra("position",position);
 intent.putStringArrayListExtra(ViewPagerActivity.KEY_IMAGE_URLS,(ArrayList<String>) model.getUrls());
 context.startActivity(intent);
 *
 *
 */
public class ViewPagerActivity extends AppCompatActivity {
	
	private ViewPager mViewPager;
	private ArrayList<String> sDrawables;
	public static final String KEY_IMAGE_URLS = "imageUrls";
	public static final String KEY_POSITION = "position";



	public static void startImagePagerActivity(Activity activity, List<String> imgUrls, int position){
		Intent intent = new Intent(activity, ViewPagerActivity.class);
		intent.putStringArrayListExtra(ViewPagerActivity.KEY_IMAGE_URLS, new ArrayList<String>(imgUrls));
		intent.putExtra(ViewPagerActivity.KEY_POSITION, position);
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.fade_in_img, R.anim.fade_out_img);
	}


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
		setContentView(mViewPager);
		ArrayList<String> imageUrls = getIntent().getStringArrayListExtra(KEY_IMAGE_URLS);
		int pos = getIntent().getIntExtra(KEY_POSITION,0);
		mViewPager.setAdapter(new SamplePagerAdapter(this,imageUrls));
		mViewPager.setCurrentItem(pos);
	}

	static class SamplePagerAdapter extends PagerAdapter {

		private ArrayList<String> sDrawables = null;
		private Context context;

		private SamplePagerAdapter(Context context, ArrayList<String> sDrawables){
			this.sDrawables = sDrawables;
			this.context = context;
		}

		@Override
		public int getCount() {
			return sDrawables.size();
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			MyImageLoader.getInstance(context).loadImage(sDrawables.get(position),photoView);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}
}
