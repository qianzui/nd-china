package com.hiapk.dataexe;

import android.content.Context;
import android.text.format.Time;

import com.hiapk.sqlhelper.SQLHelperTotal;

class MobileTraffic {
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	// 获取的系统时间
	private int year;
	private int month;
	private int monthDay;
	private int weekDay;

	/**
	 * 取得每周的流量
	 * 
	 * @param context
	 * @return 返回一个6位数组。a[0]为移动网络总计流量a[5]为wifi网络总计流量
	 *         a[1]-a[2]移动网络的上传，下载流量，a[3]-a[4]为wifi网络的上传，下载流量
	 */
	long[] getMobileWeekTraffic(Context context) {
		setTime();
		long[] weektraffic = new long[6];
		weektraffic=TrafficManager.mobile_week_data;
//		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
//				monthDay, weekDay);
		return weektraffic;
	}

//	/**
//	 * 取得月度流量
//	 * 
//	 * @param context
//	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
//	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
//	 */
//	long[] getMobileMonthTraffic(Context context) {
//		setTime();
//		long[] monthtraffic = new long[64];
//		//specialfortext   --------交换
////		monthtraffic = TrafficManager.mobile_month_data;
//		monthtraffic = TrafficManager.wifi_month_data;
//		
////		 monthtraffic = sqlhelperTotal.SelectMobileData(context, year, month);
////		monthtraffic = sqlhelperTotal.SelectWifiData(context, year, month);
//		return monthtraffic;
//	}

	/**
	 * 设置时间
	 */
	void setTime() {
		// 取得系统时间。
		Time t = new Time();
		t.setToNow();
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;
		weekDay = t.weekDay;
	}
}
