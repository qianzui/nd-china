package com.hiapk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceDataWidget {
	// 操作sharedprefrence
	private String PREFS_NAME = "allprefs";
	// 系统设置
	private String SYS_PRE_NOTIFY = "notifyCtrl";
	private String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	private String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	private SharedPreferences prefs;
	// SharedPreferences prefs_sys;
	private Editor UseEditor;
	// Editor UseEditor_sys;
	// 小部件
	private final String WIDGET_14_OPEN = "widget1x4";
	// 系统初始化
	private final String PREF_INITSQL = "isSQLINIT";
	private final String MODE_NOTINIT = "SQLisnotINIT";
	private final String MODE_HASINIT = "SQLhasINIT";
	private final String FLOAT_X = "floatX";
	private final String FLOAT_Y = "floatY";

	public SharedPrefrenceDataWidget(Context context) {
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
	}

	public int getIntX() {
		int TodayMobileDataLong = prefs.getInt(FLOAT_X, 50);
		return TodayMobileDataLong;
	}

	public void setIntX(int TodayMobileDataLong) {
		UseEditor.putInt(FLOAT_X, TodayMobileDataLong);
		UseEditor.commit();

	}

	public int getIntY() {
		int TodayMobileDataLong = prefs.getInt(FLOAT_Y, 50);
		return TodayMobileDataLong;
	}

	public void setIntY(int TodayMobileDataLong) {
		UseEditor.putInt(FLOAT_Y, TodayMobileDataLong);
		UseEditor.commit();

	}

	public void setSQLinited(boolean isSQLinited) {
		if (isSQLinited) {
			UseEditor.putString(PREF_INITSQL, MODE_HASINIT);
		} else {
			UseEditor.putString(PREF_INITSQL, MODE_NOTINIT);
		}
		UseEditor.commit();// 委托，存入数据
	}

	public boolean isWidGet14Open() {
		boolean isWidGet14Open = prefs.getBoolean(WIDGET_14_OPEN, false);
		return isWidGet14Open;
	}

	public void setWidGet14Open(boolean isWidGet14Open) {
		UseEditor.putBoolean(WIDGET_14_OPEN, isWidGet14Open);
		UseEditor.commit();
	}

	public boolean isFloatOpen() {
		boolean isFloatOpen = prefs.getBoolean(SYS_PRE_FLOAT_CTRL, false);
		return isFloatOpen;
	}

	public void setFloatOpen(boolean isFloatOpen) {
		UseEditor.putBoolean(SYS_PRE_FLOAT_CTRL, isFloatOpen);
		UseEditor.commit();
	}

	public int getWidgetFresh() {
		int widgetFresh = prefs.getInt(SYS_PRE_REFRESH_FRZ, 2);
		return widgetFresh;
	}

	public void setWidgetFresh(int widgetFresh) {
		UseEditor.putInt(SYS_PRE_REFRESH_FRZ, widgetFresh);
		UseEditor.commit();
	}

	public boolean isNotifyOpen() {
		boolean isNotifyOpen = prefs.getBoolean(SYS_PRE_NOTIFY, true);
		return isNotifyOpen;
	}

	public void setNotifyOpen(boolean isNotifyOpen) {
		UseEditor.putBoolean(SYS_PRE_NOTIFY, isNotifyOpen);
		UseEditor.commit();
	}

}
