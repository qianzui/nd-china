package com.hiapk.dataexe;

import android.content.Context;

import com.hiapk.sqlhelper.SQLHelperTotal;

/**
 * 用于获取各种流量数据的总类
 * 
 * @author Administrator
 * 
 */
public class TrafficManager {
	SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
	MobileTraffic mobileTraffic = new MobileTraffic();

	/**
	 * 获取月度移动使用流量(用于预警页面)
	 * 
	 * @param context
	 * @return
	 */
	public long getMonthUseData(Context context) {
		long mobile_month_use = 0;
		MonthlyUseData monthData = new MonthlyUseData();
		mobile_month_use = monthData.getMonthUseData(context);
		return mobile_month_use;
	}

	/**
	 * 取得每周移动的流量
	 * 
	 * @param context
	 * @return 返回一个6位数组。a[0]为移动网络总计流量a[5]为wifi网络总计流量
	 *         a[1]-a[2]移动网络的上传，下载流量，a[3]-a[4]为wifi网络的上传，下载流量
	 */
	public long[] getMobileWeekTraffic(Context context) {
		long[] weektraffic = new long[6];
		weektraffic = mobileTraffic.getMobileWeekTraffic(context);
		return weektraffic;
	}

	/**
	 * 取得月度移动流量
	 * 
	 * @param context
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	public long[] getMobileMonthTraffic(Context context) {
		long[] monthtraffic = new long[64];
		monthtraffic = mobileTraffic.getMobileMonthTraffic(context);
		return monthtraffic;
	}

	/**
	 * 取得月度wifi流量
	 * 
	 * @param context
	 * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	 *         a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	 */
	public long[] getWifiMonthTraffic(Context context) {
		WifiTraffiic wifiTraffic = new WifiTraffiic();
		long[] monthtraffic = new long[64];
		monthtraffic = wifiTraffic.getWifiMonthTraffic(context);
		return monthtraffic;
	}

	/**
	 * 记录wifi，mobile流量数据
	 * @param context
	 * @param forcerecored
	 *            true则强制记录，false则不记录流量为0的数据
	 */
	public void statsTotalTraffic(Context context, boolean forcerecored) {
		sqlhelperTotal.RecordTotalwritestats(context, forcerecored);
	}
}
