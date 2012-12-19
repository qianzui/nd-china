package com.hiapk.firewall.viewpager;

import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;

public class MyPagerAdapter extends PagerAdapter {
	public List<View> mListViews;
	Context context;
	int mCount;

	public MyPagerAdapter(List<View> mListViews) {
		this.mListViews = mListViews;
		this.context = context;
	}
	@Override
	public void destroyItem(View arg0, int position, Object arg2) {
		if (position >= mListViews.size()) {
			int newPosition = position % mListViews.size();
			position = newPosition;
			// ((ViewPager) collection).removeView(views.get(position));
		}
		if (position < 0) {
			position = -position;
			// ((ViewPager) collection).removeView(views.get(position));
		}
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return mListViews.size();// 设置成最大值以便循环滑动
	}

	@Override
	public Object instantiateItem(View collection, int position) {
		try {
			((ViewPager) collection).addView(
					mListViews.get(position % mListViews.size()), 0);
		} catch (Exception e) {
		}
		return mListViews.get(position % mListViews.size());
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}
}