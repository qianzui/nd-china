package com.hiapk.prefrencesetting;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class PrefrenceOperatorUnit {
	private static String PREFS_NAME = "allprefs";

	/**
	 * ����Ԥ��״̬
	 */
	public static void resetHasWarning(Context context) {
		String MOBILE_HAS_WARNING_MONTH = "mobilemonthhaswarning";
		String MOBILE_HAS_WARNING_DAY = "mobiledayhaswarning";
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		UseEditor.putBoolean(MOBILE_HAS_WARNING_MONTH, false);
		UseEditor.putBoolean(MOBILE_HAS_WARNING_DAY, false);
		UseEditor.commit();
	}
}
