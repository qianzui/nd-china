package com.hiapk.frame;

import com.hiapk.spearhead.SpearheadApplication;

import android.app.Activity;

public class UiActivity extends Activity {

	/**
	 * 设置自定义标题背景
	 * 
	 * @return
	 */
	public int titleBackground() {
		int title_bg_Id = getResources().getIdentifier(
				"maintab_title_bg"
						+ SpearheadApplication.getInstance().getSkinType(),
				"drawable", "com.hiapk.spearhead");

		return title_bg_Id;
	}

	/**
	 * 设置横条的背景色
	 * 
	 * @return
	 */
	public int barsBackground() {
		int title_bg_Id = getResources().getIdentifier(
				"linear_selector"
						+ SpearheadApplication.getInstance().getSkinType(),
				"drawable", "com.hiapk.spearhead");

		return title_bg_Id;
	}

	/**
	 * 设置自定义按钮,light
	 * 
	 * @return
	 */
	public int buttonBackgroundLight() {
		int btn_light_Id = getResources().getIdentifier(
				"btnselector_light"
						+ SpearheadApplication.getInstance().getSkinType(),
				"drawable", "com.hiapk.spearhead");

		return btn_light_Id;
	}

	/**
	 * 设置自定义按钮,dark
	 * 
	 * @return
	 */
	public int buttonBackgroundDark() {
		int btn_dark_Id = getResources().getIdentifier(
				"btnselector_dark"
						+ SpearheadApplication.getInstance().getSkinType(),
				"drawable", "com.hiapk.spearhead");

		return btn_dark_Id;
	}
}
