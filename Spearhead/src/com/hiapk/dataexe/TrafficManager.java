package com.hiapk.dataexe;

import android.content.Context;

import com.hiapk.sqlhelper.SQLHelperTotal;

public class TrafficManager {
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();

	/**
	 * 取得每周的流量
	 * 
	 * @param context
	 * @param year
	 * @param month
	 * @param monthDay
	 * @param weekDay
	 * @return 返回一个6位数组。a[0]为移动网络总计流量a[5]为wifi网络总计流量
	 *         a[1]-a[2]移动网络的上传，下载流量，a[3]-a[4]为wifi网络的上传，下载流量
	 */
	public long[] getMobileWeekTraffic(Context context, int year, int month,
			int monthDay, int weekDay) {
		long[] weektraffic = new long[6];
		weektraffic = sqlhelperTotal.SelectWeekData(context, year, month,
				monthDay, weekDay);
		return weektraffic;
	}

	/**
	 * 取得月度流量
	 * 
	 * @param context
	 * @param year
	 * @param month
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	public long[] getMobileMonthTraffic(Context context, int year, int month) {
		long[] monthtraffic = new long[64];
//		monthtraffic = sqlhelperTotal.SelectMobileData(context, year, month);
		monthtraffic = sqlhelperTotal.SelectWifiData(context, year, month);
		return monthtraffic;
	}

}
