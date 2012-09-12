package com.hiapk.ui.skin;

import com.hiapk.logs.Logs;
import com.hiapk.spearhead.R;
import com.hiapk.spearhead.SpearheadApplication;

import android.content.res.Resources;

public class SkinCustomDialog {
	private static String TAG = "SkinCustomDialog";
	static Resources res;

	public static int titleBarBackground() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int drawableId = res.getIdentifier("custom_dialog_title_background0",
				"drawable", "com.hiapk.spearhead");

		return drawableId;
	}

	public static int buttonBackground() {
		if (res == null) {
			res = SpearheadApplication.getInstance().getRes();
		}
		int drawableId = res.getIdentifier("btnselector_dark0",
				"drawable", "com.hiapk.spearhead");

		return drawableId;
	}
}
