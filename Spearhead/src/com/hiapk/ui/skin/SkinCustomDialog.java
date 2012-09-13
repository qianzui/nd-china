package com.hiapk.ui.skin;

import com.hiapk.spearhead.SpearheadApplication;

import android.content.res.Resources;

public class SkinCustomDialog {
//	private static String TAG = "SkinCustomDialog";
	static Resources res;

	/**
	 * �����Զ���Ի������ı���ɫ
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
	 * �����Զ���Ի���İ�ť����ɫ
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