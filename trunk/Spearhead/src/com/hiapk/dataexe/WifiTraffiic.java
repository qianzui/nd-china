package com.hiapk.dataexe;

import android.content.Context;
import android.text.format.Time;

import com.hiapk.sqlhelper.SQLHelperTotal;

class WifiTraffiic {
	// ��ȡ��ϵͳʱ��
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
	 * ����ʱ��
	 */
	void setTime() {
		// ȡ��ϵͳʱ�䡣
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
	}
}
