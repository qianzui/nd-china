package com.hiapk.ui.skin;

import android.content.res.Resources;

import com.hiapk.spearhead.SpearheadApplication;

public class SkinCustomNotification {
	// private static String TAG = "SkinCustomNotification";
	static Resources res;

	/**
	 * 设置自定义进度条风格
	 * 
	 * @return
	 */
	public static int titleBarBackground() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int styleId = res.getIdentifier("myProgressBarStyleNotify0", "style",
				"com.hiapk.spearhead");

		return styleId;
	}

}
