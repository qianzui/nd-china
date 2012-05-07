package com.hiapk.alertaction;

import com.hiapk.dataexe.TrafficManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class TrafficAlert {
	// 操作sharedprefrence
	String PREFS_NAME = "allprefs";
	// 流量预警
	String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	String MOBILE_WARNING_DAY = "mobiledaywarning";
	// 预警动作
	String WARNING_ACTION = "warningaction";
	long[] monthTraffic = new long[64];

	/**
	 * 判断是否流量超过月度限额
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
	 * 判断流量是否超日限额
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
	 * 执行日预警操作
	 * 
	 * @param context
	 */
	public void exeWarningActionDay(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int WarningAction = prefs.getInt(WARNING_ACTION, 0);
		AlertActionNotify actNotify = new AlertActionNotify();
		switch (WarningAction) {
		case 0:

			actNotify.startNotifyDay(context, false);
			showLog(0 + "day");
			break;
		case 1:
			actNotify.startNotifyDay(context, true);
			showLog(1 + "day");
			break;
		case 2:
			AlertActionMobileDataControl mbDatactrl = new AlertActionMobileDataControl();
			mbDatactrl.setMobileDataDisable(context);
			actNotify.startNotifyDay(context, false);
			showLog(2 + "day");
			break;
		default:
			break;
		}
	}

	/**
	 * 执行月预警操作
	 * 
	 * @param context
	 */
	public void exeWarningActionMonth(Context context) {
		SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
		int WarningAction = prefs.getInt(WARNING_ACTION, 0);
		AlertActionNotify actNotify = new AlertActionNotify();
		switch (WarningAction) {
		case 0:
			actNotify.startNotifyMonth(context, false);
			showLog(0 + "month");
			break;
		case 1:
			actNotify.startNotifyMonth(context, true);
			showLog(1 + "month");
			break;
		case 2:
			actNotify.startNotifyMonth(context, false);
			AlertActionMobileDataControl mbDatactrl = new AlertActionMobileDataControl();
			mbDatactrl.setMobileDataDisable(context);
			showLog(2 + "month");
			break;
		default:
			break;
		}
	}

	/**
	 * 获取流量信息
	 * 
	 * @param context
	 */
	private void getMonthMobileTraffic(Context context) {
		Time t = new Time();
		t.setToNow();
		int year = t.year;
		int month = t.month + 1;
		// showLog(year+" "+month);
		TrafficManager trafficMan = new TrafficManager();
		monthTraffic = trafficMan.getMobileMonthTraffic(context, year, month);
	}

	/**
	 * 获取日期
	 * 
	 * @return
	 */
	private int getDay() {
		Time t = new Time();
		t.setToNow();
		int DayofMonth = t.monthDay;
		return DayofMonth;

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("alertaction", string);
	}
}
