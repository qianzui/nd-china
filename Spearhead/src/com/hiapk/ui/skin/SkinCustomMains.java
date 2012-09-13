package com.hiapk.ui.skin;

import android.content.res.Resources;

import com.hiapk.spearhead.SpearheadApplication;

public class SkinCustomMains {
	// private static String TAG = "SkinCustomNotification";
	static Resources res;

	/**
	 * 设置自定义按钮,light
	 * 
	 * @return
	 */
	public static int buttonBackgroundLight() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int styleId = res.getIdentifier("btnselector_light0", "drawable",
				"com.hiapk.spearhead");

		return styleId;
	}

	/**
	 * 设置自定义按钮,dark
	 * 
	 * @return
	 */
	public static int buttonBackgroundDark() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int drawableId = res.getIdentifier("btnselector_dark0", "drawable",
				"com.hiapk.spearhead");

		return drawableId;
	}
}
