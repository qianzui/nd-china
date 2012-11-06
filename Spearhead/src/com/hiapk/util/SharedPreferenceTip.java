package com.hiapk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceTip {
	public static final String PREF_3G_UIDS = "AllowedUids3G";
	public static final String PREF_WIFI_UIDS = "AllowedUidsWifi";
	public static final String PREF_ALL_UIDS = "AppListUids";
	public static final String PREF_S = "Cache";
	public static final String PREF_SHOW = "IsShowTip";
	public static final String PREF_HELP = "isShwoHelp";
	public static final String PREF_TIP = "FireTip";
	public static final String PREF_LOAD="FirstStart";
	public static final String PREFS_NAME = "DroidWallPrefs";
	
	
//	public static String getSavedUID
	
	public static boolean isShowTip(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		boolean isShow = prefs.getBoolean(PREF_SHOW, true);
		return isShow;
	}

	public static void isShowTipSet(Context context, boolean isShow) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final Editor edit = prefs.edit();
		edit.putBoolean(PREF_SHOW, isShow);
		edit.commit();
	}

	public static boolean isShowHelp(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		boolean isShow = prefs.getBoolean(PREF_HELP, true);
		return isShow;
	}

	public static void isShowHelpSet(Context context, boolean isShow) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final Editor edit = prefs.edit();
		edit.putBoolean(PREF_HELP, isShow);
		edit.commit();
	}

	public static boolean fireTip(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		boolean isShow = prefs.getBoolean(PREF_TIP, true);
		return isShow;
	}

	public static void fireTipSet(Context context, boolean isShow) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final Editor edit = prefs.edit();
		edit.putBoolean(PREF_TIP, isShow);
		edit.commit();
	}
	public static boolean isLoadingFromSD(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		boolean isLoad = prefs.getBoolean(PREF_LOAD, true);
		return isLoad;
	}

	public static void isLoadingSet(Context context, boolean isLoad) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFS_NAME, 0);
		final Editor edit = prefs.edit();
		edit.putBoolean(PREF_LOAD, isLoad);
		edit.commit();
	}
}
