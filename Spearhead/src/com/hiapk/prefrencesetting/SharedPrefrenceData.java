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
	String VALUE_MOBILE_SET_OF_FLOAT = "mobilemonthuseinint";
	// ���õ�λ���¶����ã�
	String MOBILE_SET_UNIT = "mobileMonthUnit";
	// ���ý������ڼ��������ڵ���ʩʱ�䣬���ڵ�
	String MOBILE_COUNT_DAY = "mobileMonthCountDay";
	String MOBILE_COUNT_SET_YEAR = "mobileMonthSetCountYear";
	String MOBILE_COUNT_SET_MONTH = "mobileMonthSetCountMonth";
	String MOBILE_COUNT_SET_DAY = "mobileMonthSetCountDay";
	String MOBILE_COUNT_SET_TIME = "mobileMonthSetCountTime";
	// �Ѿ������¶������������㣬�ڵ�������յ�ʱ��
	String MOBILE_HAS_USED_CLEAR_ON_COUNT_DAY = "mobileMonthHasUseDayClear";
	// ��ʹ��������int
	String VALUE_MOBILE_HASUSED_OF_FLOAT = "mobileHasusedint";
	String MONTH_USED_DATA_TEMP = "monthtempuseddata";
	// ���õ�λ����ʹ�ã�
	String MOBILE_HASUSED_SET_UNIT = "mobileHasusedUnit";
	String MONTH_SET_HAS_SET = "monthuseHasSet";
	// ����Ԥ��
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// Ԥ������
	String WARNING_ACTION = "warningaction";
	Context context;
	SharedPreferences prefs;
	// SharedPreferences prefs_sys;
	Editor UseEditor;
	// Editor UseEditor_sys;
	// С����
	private final String WIDGET_14_OPEN = "widget1x4";
	// ϵͳ��ʼ��
	private final String PREF_INITSQL = "isSQLINIT";
	private final String MODE_NOTINIT = "SQLisnotINIT";
	private final String MODE_HASINIT = "SQLhasINIT";
	// �������Ʒ������
	String CURRENT_CITY = "currentCity";
	String CURRENT_PROVINCE = "currentProvince";
	String CURRENT_YUNYINGSHANG = "currentYunyingsh";
	String CURRENT_PINPAI = "currentPinpai";
	String CURRENT_YUNYINGSHANG_ID = "currentYunyingshid";
	String CURRENT_PINPAI_ID = "currentPinpaiid";
	String CURRENT_PROVINCE_ID = "currentProvinceid";
	String CURRENT_CITY_ID = "currentCityid";
	String CHOOSED_CITY = "chooseedcity";

	String CITY = "city_data";
	String BRAND = "brand_data";
	String PROVINCE_ID = "province_id";
	String SMSNUM = "sms_num";
	String SMSTEXT = "sms_text";
	// ������
	String PACKAGE_NAMES = "allpackagenames";
	// �ۼ��¶�����ͳ��-�����õ��¶�ʹ��ֵ��ͬ
	String MONTH_HAS_USE_STACK_DATA = "monthhasusestack";
	// ��һ������ʱ��������ʾ��
	String ALERT_ON_FIRST_START = "isalertdialogonfirstdisplayed";
	boolean isAlertDialogOnfirstOpenDisplayed = false;

	public SharedPrefrenceData(Context context) {
		this.context = context;
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		// prefs_sys = PreferenceManager.getDefaultSharedPreferences(context);
		// UseEditor_sys =
		// PreferenceManager.getDefaultSharedPreferences(context)
		// .edit();
	}

	// SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

	public long getMonthHasUsedStack() {
		long mobileUseFloat = prefs.getLong(MONTH_HAS_USE_STACK_DATA, -100);
		return mobileUseFloat;
	}

	public void setMonthHasUsedStack(long monthMobileHasUseOffloat) {
		UseEditor.putLong(MONTH_HAS_USE_STACK_DATA, monthMobileHasUseOffloat);
		UseEditor.commit();

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

	String widgetFresh = "";

	public void setSQLinited(boolean isSQLinited) {
		if (isSQLinited) {
			UseEditor.putString(PREF_INITSQL, MODE_HASINIT);
		} else {
			UseEditor.putString(PREF_INITSQL, MODE_NOTINIT);
		}
		UseEditor.commit();// ί�У���������
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
