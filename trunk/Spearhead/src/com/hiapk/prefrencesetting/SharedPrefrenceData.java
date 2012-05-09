package com.hiapk.prefrencesetting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPrefrenceData {
	// ����sharedprefrence
	String PREFS_NAME = "allprefs";
	// ϵͳ����
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	// �¶���������
	String VALUE_MOBILE_SET = "mobilemonthuse";
	String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";

	// ��ʾ��Ԥ��ҳ���int
	String VALUE_MOBILE_SET_OF_INT = "mobilemonthuseinint";
	// ���õ�λ���¶����ã�
	String MOBILE_SET_UNIT = "mobileMonthUnit";
	// ���ý������ڼ��������ڵ���ʩʱ�䣬���ڵ�
	String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// ��ʹ��������int
	String VALUE_MOBILE_HASUSED_OF_INT = "mobileHasusedint";
	// ���õ�λ����ʹ�ã�
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	// ����Ԥ��
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// Ԥ������
	String WARNING_ACTION = "warningaction";
	Context context;
	SharedPreferences prefs;
	SharedPreferences prefs_sys;
	Editor UseEditor;
	Editor UseEditor_sys;

	public SharedPrefrenceData(Context context) {
		this.context = context;
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		prefs_sys = PreferenceManager.getDefaultSharedPreferences(context);
		UseEditor_sys = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
	}

	// SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

	long monthMobileSetOfLong = 0;
	int monthMobileSetOfint = 0;
	long monthMobileHasUse = 0;
	long monthMobileHasUseOfint = 0;
	boolean isNotifyOpen = true;
	String widgetFresh="";
	
	
	public String getWidgetFresh() {
		return widgetFresh;
	}

	public void setWidgetFresh(String widgetFresh) {
		UseEditor_sys.putString(SYS_PRE_REFRESH_FRZ, widgetFresh);
		UseEditor_sys.commit();
	}

	public boolean isNotifyOpen() {
		boolean isNotifyOpen = prefs_sys.getBoolean(SYS_PRE_NOTIFY, true);
		return isNotifyOpen;
	}

	public void setNotifyOpen(boolean isNotifyOpen) {
		UseEditor_sys.putBoolean(SYS_PRE_NOTIFY, isNotifyOpen);
		UseEditor_sys.commit();
	}

	public int getMonthMobileHasUseOfint() {
		int mobileUseInt = prefs.getInt(VALUE_MOBILE_HASUSED_OF_INT, 0);
		return mobileUseInt;
	}

	public void setMonthMobileHasUseOfint(long monthMobileHasUseOfint) {
		this.monthMobileHasUseOfint = monthMobileHasUseOfint;
	}

	int countDay = 0;
	long alertWarningMonth = 0;
	long alertWarningDay = 0;
	int alertAction = 0;
	int monthMobileSetUnit = 0;
	int monthHasUsedUnit = 0;

	public int getMonthMobileSetUnit() {
		int mobileUnit = prefs.getInt(MOBILE_SET_UNIT, 0);
		return mobileUnit;
	}

	public void setMonthMobileSetUnit(int monthMobileSetUnit) {
		this.monthMobileSetUnit = monthMobileSetUnit;
	}

	public int getMonthHasUsedUnit() {
		int mobileUseUnit = prefs.getInt(MOBILE_HASUSED_SET_UNIT, 0);
		return mobileUseUnit;
	}

	public void setMonthHasUsedUnit(int monthHasUsedUnit) {
		this.monthHasUsedUnit = monthHasUsedUnit;
	}

	public long getMonthMobileSetOfLong() {
		long monthMobileSetOfLong = prefs.getLong(VALUE_MOBILE_SET, 52428800);
		return monthMobileSetOfLong;
	}

	public void setMonthMobileSetOfLong(long monthMobileSetOfLong) {
		this.monthMobileSetOfLong = monthMobileSetOfLong;
	}

	public int getMonthMobileSetOfint() {
		return monthMobileSetOfint;
	}

	public void setMonthMobileSetOfint(int monthMobileSetOfint) {
		this.monthMobileSetOfint = monthMobileSetOfint;
	}

	public long getMonthMobileHasUse() {
		long monthMobileHasUse = prefs.getLong(VALUE_MOBILE_HASUSED_LONG, 0);
		return monthMobileHasUse;
	}

	public void setMonthMobileHasUse(long monthMobileHasUse) {
		this.monthMobileHasUse = monthMobileHasUse;
	}

	public int getCountDay() {
		int mobileSetCountDay = prefs.getInt(MOBILE_COUNT_DAY, 0);
		return mobileSetCountDay;
	}

	public void setCountDay(int countDay) {
		this.countDay = countDay;
	}

	public long getAlertWarningMonth() {
		long mobileWarning = prefs.getLong(MOBILE_WARNING_MONTH,
				45 * 1024 * 1024);
		return mobileWarning;
	}

	public void setAlertWarningMonth(long alertWarningMonth) {
		this.alertWarningMonth = alertWarningMonth;
	}

	public long getAlertWarningDay() {
		long mobileWarning = prefs.getLong(MOBILE_WARNING_DAY, 5 * 1024 * 1024);
		return mobileWarning;
	}

	public void setAlertWarningDay(long alertWarningDay) {
		this.alertWarningDay = alertWarningDay;
	}

	public int getAlertAction() {
		int beforeWarningAction = prefs.getInt(WARNING_ACTION, 0);
		return beforeWarningAction;
	}

	public void setAlertAction(int alertAction) {
		this.alertAction = alertAction;
	}

}
