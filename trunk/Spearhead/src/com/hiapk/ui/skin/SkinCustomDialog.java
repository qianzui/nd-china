package com.hiapk.ui.skin;

import com.hiapk.spearhead.SpearheadApplication;

import android.content.res.Resources;

public class SkinCustomDialog {
	// private static String TAG = "SkinCustomDialog";
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
		int drawableId = res.getIdentifier("custom_dialog_title_background"
				+ SpearheadApplication.getInstance().getSkinType(), "drawable",
				"com.hiapk.spearhead");

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
		int drawableId = res.getIdentifier("btnselector_dark"
				+ SpearheadApplication.getInstance().getSkinType(), "drawable",
				"com.hiapk.spearhead");

		return drawableId;
	}

	/**
	 * ����spinner�ı���ɫ
	 * 
	 * @return
	 */
	public static int spinnerBackground() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int title_bg_Id = res.getIdentifier("linear_selector"
				+ SpearheadApplication.getInstance().getSkinType(), "drawable",
				"com.hiapk.spearhead");

		return title_bg_Id;
	}
}
