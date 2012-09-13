package com.hiapk.ui.scene;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class PrefrenceStaticOperator {
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

	/**
	 * ����Ԥ��״̬-��
	 */
	public static void resetHasWarningDay(Context context) {
		String MOBILE_HAS_WARNING_DAY = "mobiledayhaswarning";
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		UseEditor.putBoolean(MOBILE_HAS_WARNING_DAY, false);
		UseEditor.commit();
	}

	/**
	 * ����Ԥ��״̬-��
	 */
	public static void resetHasWarningMonth(Context context) {
		String MOBILE_HAS_WARNING_MONTH = "mobilemonthhaswarning";
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		UseEditor.putBoolean(MOBILE_HAS_WARNING_MONTH, false);
		UseEditor.commit();
	}
}
