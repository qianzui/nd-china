package com.hiapk.control.traff;

import com.hiapk.control.widget.MobileDataSwitch;
import com.hiapk.control.widget.NotificationWarningControl;
import com.hiapk.logs.Logs;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.Time;

public class TrafficAlert {
	// 操作sharedprefrence
	private String PREFS_NAME = "allprefs";
	// // 系统设置
	// private String SYS_PRE_NOTIFY = "notifyCtrl";
	// private String SYS_PRE_FLOAT_CTRL = "floatCtrl";
	// private String SYS_PRE_REFRESH_FRZ = "refreshfrz";
	// private String SYS_PRE_CLEAR_DATA = "cleardata";
	// 流量预警
	private String MOBILE_WARNING_MONTH = "mobilemonthwarning";
	private String MOBILE_WARNING_DAY = "mobiledaywarning";
	// 预警动作
	private String WARNING_ACTION = "warningaction";
	// 流量预警标识
	private String MOBILE_HAS_WARNING_MONTH = "mobilemonthhaswarning";
	private String MOBILE_HAS_WARNING_DAY = "mobiledayhaswarning";

	// 系统设置
	private SharedPreferences prefs;
	private Editor UseEditor;
	private MobileDataSwitch mbDatactrl;
	private NotificationWarningControl actNotify;
	private String TAG = "TrafficAlert";

	/**
	 * 初始化
	 */
	public TrafficAlert(Context context) {
		prefs = context.getSharedPreferences(PREFS_NAME, 0);
		UseEditor = context.getSharedPreferences(PREFS_NAME, 0).edit();
		mbDatactrl = new MobileDataSwitch();
		actNotify = new NotificationWarningControl(context);
	}

	long[] monthTraffic = new long[64];

	/**
	 * 判断是否流量超过月度限额
	 * 
	 * @param context
	 * @return
	 */
	public boolean isTrafficOverMonthSet(Context context) {
		long monthWarning = prefs.getLong(MOBILE_WARNING_MONTH,
				45 * 1024 * 1024);
		// if ((monthTraffic[0] + monthTraffic[63]) == 0) {
		// getMonthMobileTraffic(context);
		// }
		long mobile_month_use = TrafficManager.getMonthUseMobile(context);
		if (mobile_month_use > monthWarning) {
			// showLog(monthTraffic[0]+"");
			// showLog(monthTraffic[63]+"");
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断流量是否超日限额
	 * 
	 * @param context
	 * @return
	 */
	public boolean isTrafficOverDaySet(Context context) {
		if (TrafficManager.mobile_month_data[0] == 0
				&& TrafficManager.mobile_month_data[63] == 0) {
			return false;
		} else {
			long warningDayset = prefs.getLong(MOBILE_WARNING_DAY,
					5 * 1024 * 1024);
			if ((TrafficManager.mobile_month_data[getDay()] + TrafficManager.mobile_month_data[getDay() + 31]) > warningDayset) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * 执行日预警操作
	 * 
	 * @param context
	 */
	public void exeWarningActionDay(Context context) {
		int WarningAction = prefs.getInt(WARNING_ACTION, 0);
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
	 * 执行月预警操作
	 * 
	 * @param context
	 */
	public void exeWarningActionMonth(Context context) {
		int WarningAction = prefs.getInt(WARNING_ACTION, 0);
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

	// /**
	// * 获取流量信息
	// *
	// * @param context
	// */
	// private void getMonthMobileTraffic(Context context) {
	// Time t = new Time();
	// t.setToNow();
	// int year = t.year;
	// int month = t.month + 1;
	// // showLog(year+" "+month);
	// // TrafficManager trafficManager = new TrafficManager();
	// monthTraffic = TrafficManager.mobile_month_data;
	// }

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

	private void startMonthNotify(Context context, boolean vibrate) {
		actNotify.startNotifyMonth(context, vibrate);
	}

	private void startDayNotify(Context context, boolean vibrate) {
		actNotify.startNotifyDay(context, vibrate);
	}

	private void showLog(String string) {
		Logs.d(TAG, string);
	}
}
