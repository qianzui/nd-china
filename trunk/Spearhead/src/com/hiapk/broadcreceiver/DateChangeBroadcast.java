package com.hiapk.broadcreceiver;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
import com.hiapk.widget.SetText;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

public class DateChangeBroadcast extends BroadcastReceiver {
	// private static final String ACTION_TIME_CHANGED =
	// Intent.ACTION_TIME_CHANGED;
	// date
	// private int year;
	// private int month;
	private int monthDay;

	@Override
	public void onReceive(Context context, Intent intent) {
		// String action = intent.getAction();
		// if (ACTION_TIME_CHANGED.equals(action)) {
		// showLog("DataChange");
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		initTime();
		int countday = sharedData.getCountDay() + 1;
		if (monthDay == countday) {
			sharedData.setMonthHasUsedStack(0);
			SetText.resetWidgetAndNotify(context);
		}
		SetText.resetWidgetAndNotify(context);
		showLog("todya is " + monthDay);
		// }
	}

	/**
	 * 初始化系统时间
	 */
	private void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		// year = t.year;
		// month = t.month + 1;
		monthDay = t.monthDay;
	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		// Log.d("DataChangeBroad", string);
	}
}
