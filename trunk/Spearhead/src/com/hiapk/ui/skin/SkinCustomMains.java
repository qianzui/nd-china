package com.hiapk.ui.skin;

import android.content.res.Resources;

import com.hiapk.spearhead.SpearheadApplication;

public class SkinCustomMains {
	// private static String TAG = "SkinCustomNotification";
	static Resources res;

	/**
	 * �����Զ��尴ť,light
	 * 
	 * @return
	 */
	public static int buttonBackgroundLight() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int btn_light_Id = res.getIdentifier("btnselector_light"
				+ SpearheadApplication.getInstance().getSkinType(), "drawable",
				"com.hiapk.spearhead");

		return btn_light_Id;
	}

	/**
	 * �����Զ��尴ť,dark
	 * 
	 * @return
	 */
	public static int buttonBackgroundDark() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int btn_dark_Id = res.getIdentifier("btnselector_dark"
				+ SpearheadApplication.getInstance().getSkinType(), "drawable",
				"com.hiapk.spearhead");

		return btn_dark_Id;
	}

	/**
	 * �����Զ�����ⱳ��
	 * 
	 * @return
	 */
	public static int titleBackground() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int title_bg_Id = res.getIdentifier("maintab_title_bg"
				+ SpearheadApplication.getInstance().getSkinType(), "drawable",
				"com.hiapk.spearhead");

		return title_bg_Id;
	}

	/**
	 * ���ú����ı���ɫ
	 * 
	 * @return
	 */
	public static int barsBackground() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int title_bg_Id = res.getIdentifier("linear_selector"
				+ SpearheadApplication.getInstance().getSkinType(), "drawable",
				"com.hiapk.spearhead");

		return title_bg_Id;
	}

	/**
	 * ����ǽҳ��Ľ�������ɫ
	 * 
	 * @return
	 */
	public static int flowIndicatorBackground(int skinType) {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int title_bg_Id = res.getIdentifier("flow_indicator" + skinType,
				"drawable", "com.hiapk.spearhead");

		return title_bg_Id;
	}
}
