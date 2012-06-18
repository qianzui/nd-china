package com.hiapk.prefrencesetting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPrefrenceData {
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	// 系统设置
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	// 月度流量设置
	String VALUE_MOBILE_SET = "mobilemonthuse";
	String VALUE_MOBILE_HASUSED_LONG = "mobileHasusedlong";

	// 显示在预警页面的int
	String VALUE_MOBILE_SET_OF_INT = "mobilemonthuseinint";
	// 设置单位（月度设置）
	String MOBILE_SET_UNIT = "mobileMonthUnit";
	// 设置结算日期及结算日期的设施时间，日期等
	String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// 已经进行月度已用流量清零，在到达结算日的时候
	String MOBILE_HAS_USED_CLEAR_ON_COUNT_DAY = "mobileMonthHasUseDayClear";
	// 已使用总流量int
	String VALUE_MOBILE_HASUSED_OF_FLOAT = "mobileHasusedint";
	String MONTH_USED_DATA_TEMP = "monthtempuseddata";
	// 设置单位（已使用）
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	String MONTH_SET_HAS_SET = "monthuseHasSet";
	// 流量预警
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// 预警动作
	String WARNING_ACTION = "warningaction";
	Context context;
	SharedPreferences prefs;
	SharedPreferences prefs_sys;
	Editor UseEditor;
	Editor UseEditor_sys;
	// 小部件
	private final String WIDGET_14_OPEN = "widget1x4";
	// 系统初始化
	private final String PREF_INITSQL = "isSQLINIT";
	private final String MODE_NOTINIT = "SQLisnotINIT";
	private final String MODE_HASINIT = "SQLhasINIT";
	// 用于系统自动清理
	private String HAS_Cleared = "hasdatacleared";
	// 保存地区品牌设置
	String CURRENT_CITY = "currentCity";
	String CURRENT_PROVINCE = "currentProvince";
	String CURRENT_YUNYINGSHANG = "currentYunyingsh";
	String CURRENT_PINPAI = "currentPinpai";
	String CHOOSED_CITY = "chooseedcity";

	String CITY = "city_data";
	String BRAND = "brand_data";
	String PROVINCE_ID = "province_id";
	String SMSNUM = "sms_num";
	String SMSTEXT = "sms_text";
	// 包名们
	String PACKAGE_NAMES = "allpackagenames";
	// 第一次启动时弹出的提示框
	String ALERT_ON_FIRST_START = "isalertdialogonfirstdisplayed";
	boolean isAlertDialogOnfirstOpenDisplayed = false;

	public boolean isFirstBoot() {
		boolean hasDisplayed = prefs.getBoolean(ALERT_ON_FIRST_START, true);
		return hasDisplayed;
	}

	public void setFirstBoot(boolean hasDisplay) {
		UseEditor.putBoolean(ALERT_ON_FIRST_START, hasDisplay);
		UseEditor.commit();
	}

	public SharedPrefrenceData(Context context) {
		this.context = context;
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		prefs_sys = PreferenceManager.getDefaultSharedPreferences(context);
		UseEditor_sys = PreferenceManager.getDefaultSharedPreferences(context)
				.edit();
	}

	// SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

	public boolean isWidGet14Open() {
		boolean isWidGet14Open = prefs.getBoolean(WIDGET_14_OPEN, false);
		return isWidGet14Open;
	}

	public void setWidGet14Open(boolean isWidGet14Open) {
		UseEditor.putBoolean(WIDGET_14_OPEN, isWidGet14Open);
		UseEditor.commit();
	}

	public boolean isFloatOpen() {
		boolean isFloatOpen = prefs_sys.getBoolean(SYS_PRE_FLOAT_CTRL, false);
		return isFloatOpen;
	}

	public void setFloatOpen(boolean isFloatOpen) {
		UseEditor_sys.putBoolean(SYS_PRE_FLOAT_CTRL, isFloatOpen);
		UseEditor_sys.commit();
	}

	String widgetFresh = "";

	public boolean isSQLinited() {
		// boolean isSQLinited = prefs.getString(MOBILE_SET_UNIT, 0);
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		return prefs.getString(PREF_INITSQL, MODE_NOTINIT).endsWith(
				MODE_HASINIT);
	}

	public void setSQLinited(boolean isSQLinited) {
		if (isSQLinited) {
			UseEditor.putString(PREF_INITSQL, MODE_HASINIT);
		} else {
			UseEditor.putString(PREF_INITSQL, MODE_NOTINIT);
		}
		UseEditor.commit();// 委托，存入数据
	}

	public String getWidgetFresh() {
		String widgetFresh = prefs_sys.getString(SYS_PRE_REFRESH_FRZ, "2");
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

	public boolean isHAS_Cleared() {
		boolean isClear = prefs.getBoolean(HAS_Cleared, true);
		return isClear;
	}

	public void setHAS_Cleared(boolean is_Cleared) {
		UseEditor.putBoolean(HAS_Cleared, is_Cleared);
		UseEditor.commit();
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
		UseEditor.putLong(VALUE_MOBILE_SET, monthMobileSetOfLong);
		UseEditor.commit();
	}

	public int getMonthMobileSetOfint() {
		int monthMobileSetOfint = prefs.getInt(VALUE_MOBILE_SET_OF_INT, 50);
		return monthMobileSetOfint;
	}

	public void setMonthMobileSetOfint(int monthMobileSetOfint) {
		UseEditor.putInt(VALUE_MOBILE_SET_OF_INT, monthMobileSetOfint);
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

	public int getCountDay() {
		int mobileSetCountDay = prefs.getInt(MOBILE_COUNT_DAY, 0);
		return mobileSetCountDay;
	}

	public void setCountDay(int countDay) {
		UseEditor.putInt(MOBILE_COUNT_DAY, countDay);
		UseEditor.commit();
	}

	public boolean getHasUsedClearOnCountDay() {
		boolean hasClear = prefs.getBoolean(MOBILE_HAS_USED_CLEAR_ON_COUNT_DAY,
				false);
		return hasClear;
	}

	public void setHasUsedClearOnCountDay(boolean countDay) {
		UseEditor.putBoolean(MOBILE_HAS_USED_CLEAR_ON_COUNT_DAY, countDay);
		UseEditor.commit();
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

	public void setPhoneInfo(String city, String brand, int shengfenId,
			String smsNum, String smsText) {
		UseEditor.putString(CITY, city);
		UseEditor.putString(BRAND, brand);
		UseEditor.putInt(PROVINCE_ID, shengfenId);
		UseEditor.putString(SMSNUM, smsNum);
		UseEditor.putString(SMSTEXT, smsText);

		UseEditor.commit();
	}

	public void setIsSend(Boolean isSend) {
		UseEditor.putBoolean("IS_SEND", isSend);
		UseEditor.commit();
	}

	public void setIsReceive(Boolean isReceive) {
		UseEditor.putBoolean("IS_RECEIVE", isReceive);
		UseEditor.commit();
	}

	public boolean getIsSend() {
		boolean isSend = prefs.getBoolean("IS_SEND", false);
		return isSend;

	}

	public boolean getIsReceive() {
		boolean isReceive = prefs.getBoolean("IS_RECEIVE", false);
		return isReceive;
	}

	public String getCity() {
		String city = prefs.getString(CITY, "进入地区选择页");
		return city;

	}

	public String getBrand() {
		String brand = prefs.getString(BRAND, "");
		return brand;

	}

	public int getProvinceID() {
		int city = prefs.getInt(PROVINCE_ID, 0);
		return city;

	}

	public String getChooseCity() {
		String smsNum = prefs.getString(CHOOSED_CITY, "北京--神州行");
		return smsNum;

	}

	public void setChooseCity(String str) {
		UseEditor.putString(CHOOSED_CITY, str);
		UseEditor.commit();
	}

	public String getSmsNum() {
		String smsNum = prefs.getString(SMSNUM, "10086");
		return smsNum;

	}

	public void setSmsNum(String str) {
		UseEditor.putString(SMSNUM, str);
		UseEditor.commit();
	}

	public String getSmsText() {
		String smsText = prefs.getString(SMSTEXT, "CXLL");
		return smsText;

	}

	public void setSmsText(String str) {
		UseEditor.putString(SMSTEXT, str);
		UseEditor.commit();
	}

	public void setIsFirstRegulate(Boolean isFirstRegulate) {
		UseEditor.putBoolean("IS_FIRST_REGULAT", isFirstRegulate);
		UseEditor.commit();
	}

	public boolean getIsFirstRegulate() {
		boolean isFirstRegulate = prefs.getBoolean("IS_FIRST_REGULAT", true);
		return isFirstRegulate;

	}

	public void setCurrentCity(String str) {
		UseEditor.putString(CURRENT_CITY, str);
		UseEditor.commit();
	}

	public String getCurrentCity() {
		String result = prefs.getString(CURRENT_CITY, "北京");
		return result;

	}

	public void setCurrentProvince(String str) {
		UseEditor.putString(CURRENT_PROVINCE, str);
		UseEditor.commit();
	}

	public String getCurrentProvince() {
		String result = prefs.getString(CURRENT_PROVINCE, "北京");
		return result;

	}

	public void setCurrentYunyinshang(String str) {
		UseEditor.putString(CURRENT_YUNYINGSHANG, str);
		UseEditor.commit();
	}

	public String getCurrentYunyinshang() {
		String result = prefs.getString(CURRENT_YUNYINGSHANG, "中国移动");
		return result;

	}

	public void setCurrentPinpai(String str) {
		UseEditor.putString(CURRENT_PINPAI, str);
		UseEditor.commit();
	}

	public String getCurrentPinpai() {
		String result = prefs.getString(CURRENT_PINPAI, "神州行");
		return result;

	}
}
