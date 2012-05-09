package com.hiapk.alertaction;

import com.hiapk.dataexe.TrafficManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class TrafficAlert {
	// ����sharedprefrence
	String PREFS_NAME = "allprefs";
	// ϵͳ����
	String SYS_PRE_NOTIFY = "notifyCtrl";
	String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	String SYS_PRE_CLEAR_DATA = "cleardata";
	// ����Ԥ��
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// Ԥ������
	String WARNING_ACTION = "warningaction";
	// ����Ԥ����ʶ
	String MOBILE_HAS_WARNING_MONTH = "mobilemonthhaswarning";
	String MOBILE_HAS_WARNING_DAY = "mobiledayhaswarning";
	// ϵͳ����

	long[] monthTraffic = new long[64];

	/**
	 * �ж��Ƿ����������¶��޶�
	 * 
	 * @param context
	 * @return
	 */
	public boolean isTrafficOverMonthSet(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long monthWarning = prefs.getLong(MOBILE_WARNING_MONTH,
				45 * 1024 * 1024);
		if ((monthTraffic[0] + monthTraffic[63]) == 0) {
			getMonthMobileTraffic(context);
		}
		if (monthWarning > (monthTraffic[0] + monthTraffic[63])) {
			// showLog(monthTraffic[0]+"");
			// showLog(monthTraffic[63]+"");
			return false;
		} else {
			return true;
		}

	}

	/**
	 * �ж������Ƿ����޶�
	 * 
	 * @param context
	 * @return
	 */
	public boolean isTrafficOverDaySet(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		long warningDayset = prefs.getLong(MOBILE_WARNING_DAY, 5 * 1024 * 1024);
		if ((monthTraffic[0] + monthTraffic[63]) == 0) {
			getMonthMobileTraffic(context);
		}
		if (warningDayset > (monthTraffic[getDay()] + monthTraffic[getDay() + 31])) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ִ����Ԥ������
	 * 
	 * @param context
	 */
	public void exeWarningActionDay(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		int WarningAction = prefs.getInt(WARNING_ACTION, 0);
		AlertActionMobileDataControl mbDatactrl = new AlertActionMobileDataControl();
		switch (WarningAction) {
		case 0:
			startDayNotify(context, false);
			UseEditor.putBoolean(MOBILE_HAS_WARNING_DAY, true);
			showLog(0 + "day");
			break;
		case 1:
			startDayNotify(context, true);
			UseEditor.putBoolean(MOBILE_HAS_WARNING_DAY, true);
			showLog(1 + "day");
			break;
		case 2:

			mbDatactrl.setMobileDataDisable(context);
			startDayNotify(context, false);
			UseEditor.putBoolean(MOBILE_HAS_WARNING_DAY, true);
			showLog(2 + "day");
			break;
		case 3:
			mbDatactrl.setMobileDataDisable(context);
			startDayNotify(context, true);
			UseEditor.putBoolean(MOBILE_HAS_WARNING_DAY, true);
			showLog(2 + "day");
			break;
		default:
			break;
		}
		UseEditor.commit();
	}

	/**
	 * ִ����Ԥ������
	 * 
	 * @param context
	 */
	public void exeWarningActionMonth(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		Editor UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		int WarningAction = prefs.getInt(WARNING_ACTION, 0);
		AlertActionMobileDataControl mbDatactrl = new AlertActionMobileDataControl();
		switch (WarningAction) {
		case 0:
			startMonthNotify(context, false);
			UseEditor.putBoolean(MOBILE_HAS_WARNING_MONTH, true);
			showLog(0 + "month");
			break;
		case 1:
			startMonthNotify(context, true);
			UseEditor.putBoolean(MOBILE_HAS_WARNING_MONTH, true);
			showLog(1 + "month");
			break;
		case 2:
			startMonthNotify(context, false);
			mbDatactrl.setMobileDataDisable(context);
			UseEditor.putBoolean(MOBILE_HAS_WARNING_MONTH, true);
			showLog(2 + "month");
			break;
		case 3:
			startMonthNotify(context, true);
			mbDatactrl.setMobileDataDisable(context);
			UseEditor.putBoolean(MOBILE_HAS_WARNING_MONTH, true);
			showLog(2 + "month");
			break;
		default:
			break;
		}
		UseEditor.commit();
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param context
	 */
	private void getMonthMobileTraffic(Context context) {
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		// showLog(year+" "+month);
		TrafficManager trafficManager = new TrafficManager();
		monthTraffic = trafficManager.getMobileMonthTraffic(context);
	}

	/**
	 * ��ȡ����
	 * 
	 * @return
	 */
	private int getDay() {
		Time t = new Time();
		t.setToNow();
		int DayofMonth = t.monthDay;
		return DayofMonth;

	}

	private void startMonthNotify(Context context, boolean vibrate) {
		SharedPreferences prefs_setting = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean allowNotify = prefs_setting.getBoolean(SYS_PRE_NOTIFY, true);
//		showLog(allowNotify + "");
		if (allowNotify) {
			AlertActionNotify actNotify = new AlertActionNotify();
			actNotify.startNotifyMonth(context, vibrate);
		}
	}

	private void startDayNotify(Context context, boolean vibrate) {
		SharedPreferences prefs_setting = PreferenceManager
				.getDefaultSharedPreferences(context);
		boolean allowNotify = prefs_setting.getBoolean(SYS_PRE_NOTIFY, true);
//		showLog(allowNotify + "");
		if (allowNotify) {
			AlertActionNotify actNotify = new AlertActionNotify();
			actNotify.startNotifyDay(context, vibrate);
		}
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("alertaction", string);
	}
}
