package com.hiapk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceData {
	// 操作sharedprefrence
	private String PREFS_NAME = "allprefs";
	// 月度流量设置
	private String VALUE_MOBILE_SET = "mobilemonthuse";

	// 显示在预警页面的int
	private String VALUE_MOBILE_SET_OF_FLOAT = "mobilemonthuseinint";
	// 设置单位（月度设置）
	private String MOBILE_SET_UNIT = "mobileMonthUnit";
	// 设置结算日期及结算日期的设施时间，日期等
	private String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	private String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	private String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
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
	// 防火墙页面显示的类型
	private String FireWallType = "firewalltype";
	// 是否允许流量预警
	private String IsAllowAlert = "isallowalert";
	// 防火墙是否成功应用
	private String IsFireWallOpen = "isfirewallopen";
	// 是否开启防火墙自动记忆功能
	private String isAutoSaveFireWallRule = "isOpenFireWallautosave";
	// 防火墙记忆功能已经知道了
	private String isHasKnowFireWallSave = "ishasknowfirewallsave";
	private String isShakeToSwitch = "isShakeToSwitchFireList";
	private String isKnowShakeToSwitch = "isKnowShakeToSwitchFireList";
	// 摇一摇敏感度
	private String shakeMedianValue = "shakeMedianValue";

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
		UseEditor.putInt(MOBILE_SET_UNIT, monthMobileSetUnit);
		UseEditor.commit();
	}

	public int getMonthHasUsedUnit() {
		int mobileUseUnit = prefs.getInt(MOBILE_HASUSED_SET_UNIT, 0);
		return mobileUseUnit;
	}

	public void setMonthHasUsedUnit(int monthHasUsedUnit) {
		UseEditor.putInt(MOBILE_HASUSED_SET_UNIT, monthHasUsedUnit);
		UseEditor.commit();
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

	/**
	 * 获取结算日的数值，实际日期需要+1
	 * 
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

	/**
	 * 获取结算日的设置，实际日期需要+1
	 * 
	 * @return
	 */
	public int getCountSetDay() {
		int mobileSetCountDay = prefs.getInt(MOBILE_COUNT_SET_DAY, 0);
		return mobileSetCountDay;
	}

	public void setCountSetDay(int countDay) {
		UseEditor.putInt(MOBILE_COUNT_SET_DAY, countDay);
		UseEditor.commit();
	}

	/**
	 * 获取结算年的数值，默认1977
	 * 
	 * @return
	 */
	public int getCountSetYear() {
		int mobileSetCountYear = prefs.getInt(MOBILE_COUNT_SET_YEAR, 1977);
		return mobileSetCountYear;
	}

	public void setCountSetYear(int countYear) {
		UseEditor.putInt(MOBILE_COUNT_SET_YEAR, countYear);
		UseEditor.commit();
	}

	public long getAlertWarningMonth() {
		long mobileWarning = prefs.getLong(MOBILE_WARNING_MONTH, 0);
		return mobileWarning;
	}

	public void setAlertWarningMonth(long alertWarningMonth) {
		UseEditor.putLong(MOBILE_WARNING_MONTH, alertWarningMonth);
		UseEditor.commit();
	}

	public long getAlertWarningDay() {
		long mobileWarning = prefs.getLong(MOBILE_WARNING_DAY, 0);
		return mobileWarning;
	}

	public void setAlertWarningDay(long alertWarningDay) {
		UseEditor.putLong(MOBILE_WARNING_DAY, alertWarningDay);
		UseEditor.commit();
	}

	public int getAlertAction() {
		int beforeWarningAction = prefs.getInt(WARNING_ACTION, 0);
		return beforeWarningAction;
	}

	public void setAlertAction(int alertAction) {
		UseEditor.putInt(WARNING_ACTION, alertAction);
		UseEditor.commit();
	}

	public int getFireWallType() {
		int FireWallTypeint = prefs.getInt(FireWallType, 0);
		return FireWallTypeint;
	}

	public void setFireWallType(int fireWallType) {
		UseEditor.putInt(FireWallType, fireWallType);
		UseEditor.commit();
	}

	public boolean IsAllowAlert() {
		boolean blean = prefs.getBoolean(IsAllowAlert, true);
		return blean;
	}

	public void setIsAllowAlert(boolean blean) {
		UseEditor.putBoolean(IsAllowAlert, blean);
		UseEditor.commit();
	}

	public boolean IsFireWallOpenFail() {
		boolean blean = prefs.getBoolean(IsFireWallOpen, false);
		return blean;
	}

	public void setIsFireWallOpenFail(boolean blean) {
		UseEditor.putBoolean(IsFireWallOpen, blean);
		UseEditor.commit();
	}

	public boolean isAutoSaveFireWallRule() {
		boolean blean = prefs.getBoolean(isAutoSaveFireWallRule, true);
		return blean;
	}

	public void setisAutoSaveFireWallRule(boolean blean) {
		UseEditor.putBoolean(isAutoSaveFireWallRule, blean);
		UseEditor.commit();
	}

	public boolean isHasknowFireWallSave() {
		boolean blean = prefs.getBoolean(isHasKnowFireWallSave, false);
		return blean;
	}

	public void setisHasknowFireWallSave(boolean blean) {
		UseEditor.putBoolean(isHasKnowFireWallSave, blean);
		UseEditor.commit();
	}

	public boolean isShakeToSwitch() {
		Boolean isShake = prefs.getBoolean(isShakeToSwitch, true);
		return isShake;
	}

	public void setIsShakeToSwitch(boolean isShake) {
		UseEditor.putBoolean(isShakeToSwitch, isShake);
		UseEditor.commit();
	}

	public boolean isKnowShakeToSwitch() {
		boolean blean = prefs.getBoolean(isKnowShakeToSwitch, false);
		return blean;
	}

	public void setIsKnowShakeToSwitch(boolean isKnow) {
		UseEditor.putBoolean(isKnowShakeToSwitch, isKnow);
		UseEditor.commit();
	}

	public float getMedianValues() {
		float values = prefs.getFloat(shakeMedianValue, 12);
		return values;
	}

	public void setMedianValues(float values) {
		UseEditor.putFloat(shakeMedianValue, values);
		UseEditor.commit();
	}

}
