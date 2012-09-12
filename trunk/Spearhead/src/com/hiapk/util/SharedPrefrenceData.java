package com.hiapk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceData {
	// 操作sharedprefrence
	private String PREFS_NAME = "allprefs";
	// 月度流量设置
	private String VALUE_MOBILE_SET = "mobilemonthuse";
	private String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";

	// 显示在预警页面的int
	private String VALUE_MOBILE_SET_OF_FLOAT = "mobilemonthuseinint";
	// 设置单位（月度设置）
	private String MOBILE_SET_UNIT = "mobileMonthUnit";
	// 设置结算日期及结算日期的设施时间，日期等
	private String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	// 已使用总流量int
	private String VALUE_MOBILE_HASUSED_OF_FLOAT = "mobileHasusedint";
	private String MONTH_USED_DATA_TEMP = "monthtempuseddata";
	// 设置单位（已使用）
	private String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	private String MONTH_SET_HAS_SET = "monthuseHasSet";
	// 流量预警
	private String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	private String MOBILE_WARNING_DAY = "mobiledaywarning";
	// 预警动作
	private String WARNING_ACTION = "warningaction";
	private SharedPreferences prefs;
	// SharedPreferences prefs_sys;
	private Editor UseEditor;
	// 包名们
	private String PACKAGE_NAMES = "allpackagenames";
	// 累计月度流量统计-与设置的月度使用值不同
	private String MONTH_HAS_USE_STACK_DATA = "monthhasusestack";
	private String TODAY_MOBILE_DATA = "todaymobiledata";

	public SharedPrefrenceData(Context context) {
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		// prefs_sys = PreferenceManager.getDefaultSharedPreferences(context);
		// UseEditor_sys =
		// PreferenceManager.getDefaultSharedPreferences(context)
		// .edit();
	}

	// SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

	public long getTodayMobileDataLong() {
		long TodayMobileDataLong = prefs.getLong(TODAY_MOBILE_DATA, 0);
		return TodayMobileDataLong;
	}

	public void setTodayMobileDataLong(long TodayMobileDataLong) {
		UseEditor.putLong(TODAY_MOBILE_DATA, TodayMobileDataLong);
		UseEditor.commit();

	}

	public long getMonthHasUsedStack() {
		long mobileUseFloat = prefs.getLong(MONTH_HAS_USE_STACK_DATA, -100);
		return mobileUseFloat;
	}

	public void setMonthHasUsedStack(long monthMobileHasUseOffloat) {
		UseEditor.putLong(MONTH_HAS_USE_STACK_DATA, monthMobileHasUseOffloat);
		UseEditor.commit();

	}

	public boolean isMonthSetHasSet() {
		boolean blean = prefs.getBoolean(MONTH_SET_HAS_SET, false);
		return blean;
	}

	public void setMonthSetHasSet(boolean blean) {
		UseEditor.putBoolean(MONTH_SET_HAS_SET, blean);
		UseEditor.commit();
	}

	public float getMonthMobileHasUseOffloat() {
		float mobileUseFloat = prefs.getFloat(VALUE_MOBILE_HASUSED_OF_FLOAT, 0);
		return mobileUseFloat;
	}

	public void setMonthMobileHasUseOffloat(float monthMobileHasUseOffloat) {
		UseEditor.putFloat(VALUE_MOBILE_HASUSED_OF_FLOAT,
				monthMobileHasUseOffloat);
		UseEditor.commit();

	}

	int countDay = 0;
	long alertWarningMonth = 0;
	long alertWarningDay = 0;
	int alertAction = 0;
	int monthMobileSetUnit = 0;
	int monthHasUsedUnit = 0;
	long monthUseDataTemp;

	public long getMonthUseDataTemp() {
		long monthUseDataTemp = prefs.getLong(MONTH_USED_DATA_TEMP, 0);
		return monthUseDataTemp;
	}

	public void setMonthUseDataTemp(long monthUseDataTemp) {
		UseEditor.putLong(MONTH_USED_DATA_TEMP, monthUseDataTemp);
		UseEditor.commit();
	}

	public String getPackageNames() {
		String PackageNames = prefs.getString(PACKAGE_NAMES, "");
		return PackageNames;
	}

	public void setPackageNames(String PackageNames) {
		UseEditor.putString(PACKAGE_NAMES, PackageNames);
		UseEditor.commit();
	}

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
		long monthMobileSetOfLong = prefs.getLong(VALUE_MOBILE_SET, 0);
		return monthMobileSetOfLong;
	}

	public void setMonthMobileSetOfLong(long monthMobileSetOfLong) {
		UseEditor.putLong(VALUE_MOBILE_SET, monthMobileSetOfLong);
		UseEditor.commit();
	}

	public float getMonthMobileSetOfFloat() {
		float monthMobileSetOfint = prefs
				.getFloat(VALUE_MOBILE_SET_OF_FLOAT, 0);
		return monthMobileSetOfint;
	}

	public void setMonthMobileSetOfFloat(float monthMobileSetOfint) {
		UseEditor.putFloat(VALUE_MOBILE_SET_OF_FLOAT, monthMobileSetOfint);
		UseEditor.commit();
	}

	public long getMonthMobileHasUse() {
		long monthMobileHasUse = prefs.getLong(VALUE_MOBILE_HASUSED_LONG, 0);
		return monthMobileHasUse;
	}

	public void setMonthMobileHasUse(long monthMobileHasUse) {
		UseEditor.putLong(VALUE_MOBILE_HASUSED_LONG, monthMobileHasUse);
		UseEditor.commit();
	}
/**
 * 获取结算日的数值，实际日期需要+1
 * @return
 */
	public int getCountDay() {
		int mobileSetCountDay = prefs.getInt(MOBILE_COUNT_DAY, 0);
		return mobileSetCountDay;
	}

	public void setCountDay(int countDay) {
		UseEditor.putInt(MOBILE_COUNT_DAY, countDay);
		UseEditor.commit();
	}

	public long getAlertWarningMonth() {
		long mobileWarning = prefs.getLong(MOBILE_WARNING_MONTH, 0);
		return mobileWarning;
	}

	public void setAlertWarningMonth(long alertWarningMonth) {
		this.alertWarningMonth = alertWarningMonth;
	}

	public long getAlertWarningDay() {
		long mobileWarning = prefs.getLong(MOBILE_WARNING_DAY, 0);
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
		UseEditor.putInt(WARNING_ACTION, alertAction);
		UseEditor.commit();
	}

}
