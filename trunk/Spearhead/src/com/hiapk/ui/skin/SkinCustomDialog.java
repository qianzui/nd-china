package com.hiapk.ui.skin;

import com.hiapk.spearhead.SpearheadApplication;

import android.content.res.Resources;

public class SkinCustomDialog {
	private static String TAG = "SkinCustomDialog";
	static Resources res;

	/**
	 * 设置自定义对话框标题的背景色
	 * 
	 * @return
	 */
	public static int titleBarBackground() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int drawableId = res.getIdentifier("custom_dialog_title_background0",
				"drawable", "com.hiapk.spearhead");

		return drawableId;
	}

	/**
	 * 设置自定义对话框的按钮背景色
	 * 
	 * @return
	 */
	public static int buttonBackground() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int drawableId = res.getIdentifier("btnselector_dark0", "drawable",
				"com.hiapk.spearhead");

		return drawableId;
	}
}
