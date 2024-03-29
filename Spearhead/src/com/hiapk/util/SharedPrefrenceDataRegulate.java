package com.hiapk.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceDataRegulate {
	// 操作sharedprefrence
	private String PREFS_NAME = "allprefs";
	Context context;
	SharedPreferences prefs;
	// SharedPreferences prefs_sys;
	Editor UseEditor;
	// 保存地区品牌设置
	private String CURRENT_CITY = "currentCity";
	private String CURRENT_PROVINCE = "currentProvince";
	private String CURRENT_YUNYINGSHANG = "currentYunyingsh";
	private String CURRENT_PINPAI = "currentPinpai";
	private String CURRENT_YUNYINGSHANG_ID = "currentYunyingshid";
	private String CURRENT_PINPAI_ID = "currentPinpaiid";
	private String CURRENT_PROVINCE_ID = "currentProvinceid";
	private String CURRENT_CITY_ID = "currentCityid";
	private String CHOOSED_CITY = "chooseedcity";

	private String CITY = "city_data";
	private String BRAND = "brand_data";
	private String PROVINCE_ID = "province_id";
	private String SMSNUM = "sms_num";
	private String SMSTEXT = "sms_text";
	boolean isAlertDialogOnfirstOpenDisplayed = false;

	public SharedPrefrenceDataRegulate(Context context) {
		this.context = context;
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		// prefs_sys = PreferenceManager.getDefaultSharedPreferences(context);
		// UseEditor_sys =
		// PreferenceManager.getDefaultSharedPreferences(context)
		// .edit();
	}

	// SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);

	int countDay = 0;
	long alertWarningMonth = 0;
	long alertWarningDay = 0;
	int alertAction = 0;
	int monthMobileSetUnit = 0;
	int monthHasUsedUnit = 0;
	long monthUseDataTemp;

	// public boolean getHasUsedClearOnCountDay() {
	// boolean hasClear = prefs.getBoolean(MOBILE_HAS_USED_CLEAR_ON_COUNT_DAY,
	// false);
	// return hasClear;
	// }
	//
	// public void setHasUsedClearOnCountDay(boolean countDay) {
	// UseEditor.putBoolean(MOBILE_HAS_USED_CLEAR_ON_COUNT_DAY, countDay);
	// UseEditor.commit();
	// }

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

	public void setCurrentYunyinshangID(int str) {
		UseEditor.putInt(CURRENT_YUNYINGSHANG_ID, str);
		UseEditor.commit();
	}

	public int getCurrentYunyinshangID() {
		int result = prefs.getInt(CURRENT_YUNYINGSHANG_ID, 0);
		return result;

	}

	public void setCurrentPinpaiID(int str) {
		UseEditor.putInt(CURRENT_PINPAI_ID, str);
		UseEditor.commit();
	}

	public int getCurrentPinpaiID() {
		int result = prefs.getInt(CURRENT_PINPAI_ID, 0);
		return result;

	}

	public void setCurrentProvinceID(int str) {
		UseEditor.putInt(CURRENT_PROVINCE_ID, str);
		UseEditor.commit();
	}

	public int getCurrentProvinceID() {
		int result = prefs.getInt(CURRENT_PROVINCE_ID, 0);
		return result;

	}

	public void setCurrentCityID(int str) {
		UseEditor.putInt(CURRENT_CITY_ID, str);
		UseEditor.commit();
	}

	public int getCurrentCityID() {
		int result = prefs.getInt(CURRENT_CITY_ID, 0);
		return result;

	}

}
