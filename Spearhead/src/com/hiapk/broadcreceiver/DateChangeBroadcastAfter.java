package com.hiapk.broadcreceiver;

import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.logs.Logs;
import com.hiapk.logs.WriteLog;
import com.hiapk.ui.scene.PrefrenceStaticOperator;
import com.hiapk.util.SharedPrefrenceData;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

public class DateChangeBroadcastAfter extends BroadcastReceiver {
	private int monthDay;
	private String TAG = "DateChangeAft";

	@Override
	public void onReceive(Context context, Intent intent) {
		Logs.d(TAG, "afterDate");
		resetRecordData(context);
	}

	/**
	 * 清零月与日流量统计数据
	 * 
	 * @param context
	 */
	private void resetRecordData(Context context) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		Logs.d(TAG, "setTodayMobileDataLong=0");
		sharedData.setTodayMobileDataLong(0);
		initTime();
		int countday = sharedData.getCountDay() + 1;
		if (monthDay == countday) {
			sharedData.setMonthHasUsedStack(0);
			TrafficManager.clearUidtraffMonthly(context);
			WriteLog writelog = new WriteLog(context);
			writelog.clearmonthLog();
			// 防止月初数据错误
			TrafficManager.mobile_month_use = 1;
			PrefrenceStaticOperator.resetHasWarningMonth(context);
			// // 开始新的一月初始化数据
			// SQLHelperDataexe.initShowDataOnSplash(context);
		}
		//
		// SharedPrefrenceDataWidget sharedDatawidget = new
		// SharedPrefrenceDataWidget(
		// context);
		// boolean isNotifyOpen = sharedDatawidget.isNotifyOpen();
		// Logs.d(TAG, "isNotifyOpen=" + isNotifyOpen);
		// Logs.d(TAG, "SetText.textUpbef=" + SetText.textUp);
		SetText.resetWidgetAndNotify(context);
		PrefrenceStaticOperator.resetHasWarningDay(context);
		Logs.d(TAG, "dayRest=" + monthDay);
		// Logs.d(TAG, "SetText.textUpaf=" + SetText.textUp);
	}

	/**
	 * 初始化系统时间
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		monthDay = t.monthDay;
	}

}
