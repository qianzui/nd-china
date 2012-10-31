package com.hiapk.frame;

import com.hiapk.spearhead.SpearheadApplication;

import android.app.Activity;

public class UiActivity extends Activity {

	/**
	 * �����Զ�����ⱳ��
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
	 * ���ú����ı���ɫ
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
	 * �����Զ��尴ť,light
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
	 * �����Զ��尴ť,dark
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
