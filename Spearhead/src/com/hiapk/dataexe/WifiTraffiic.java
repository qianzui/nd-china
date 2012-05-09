package com.hiapk.dataexe;

import android.content.Context;
import android.text.format.Time;

import com.hiapk.sqlhelper.SQLHelperTotal;

class WifiTraffiic {
	// 获取的系统时间
	private int year;
	private int month;

	long[] getWifiMonthTraffic(Context context) {
		setTime();
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		long[] wifiTraffic = new long[64];
		wifiTraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		return wifiTraffic;
	}

	/**
	 * 设置时间
	 */
	void setTime() {
		// 取得系统时间。
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
	}
}
