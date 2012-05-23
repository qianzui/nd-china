package com.hiapk.dataexe;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hiapk.prefrencesetting.SharedPrefrenceData;
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
	public static long mobile_month_use_afterSet = 0;
	public static long[] wifi_month_data = new long[64];
	public static long[] mobile_month_data = new long[64];
	public static long[] mobile_week_data = new long[6];

	/**
	 * 获取月度移动使用流量
	 * 
	 * @param context
	 * @return
	 */
	public static long getMonthUseData(Context context) {
		SharedPrefrenceData sharedData = new SharedPrefrenceData(context);
		long mobile_month_use = 0;
		// statsTotalTraffic(context, false);
		// MonthlyUseData monthData = new MonthlyUseData();
		// mobile_month_use = monthData.getMonthUseData(context);
		mobile_month_use = mobile_month_use_afterSet;
		mobile_month_use += sharedData.getMonthMobileHasUse();
		return mobile_month_use;
	}

	// /**
	// * 设置月度移动使用流量数值
	// *
	// * @param context
	// * @return
	// */
	// public static void setMonthUseDate(Context context) {
	// MonthlyUseData monthData = new MonthlyUseData();
	// mobile_month_use_afterSet = monthData.getMonthUseData(context);
	// }

	// /**
	// * 获得月度移动使用流量数值
	// *
	// * @param context
	// * @return
	// */
	// public long countMonthUseDate(Context context) {
	// MonthlyUseData monthData = new MonthlyUseData();
	// long data = monthData.getMonthUseData(context);
	// return data;
	// }

	// /**
	// * 取得每周移动的流量
	// *
	// * @param context
	// * @return 返回一个6位数组。a[0]为移动网络总计流量a[5]为wifi网络总计流量
	// * a[1]-a[2]移动网络的上传，下载流量，a[3]-a[4]为wifi网络的上传，下载流量
	// */
	// public long[] getMobileWeekTraffic(Context context) {
	// long[] weektraffic = new long[6];
	// weektraffic = mobileTraffic.getMobileWeekTraffic(context);
	// return weektraffic;
	// }

	// /**
	// * 取得月度移动流量
	// *
	// * @param context
	// * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	// * a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	// */
	// public long[] getMobileMonthTraffic(Context context) {
	// return mobile_month_data;
	// }

	// /**
	// * 取得月度wifi流量
	// *
	// * @param context
	// * @return 返回一个64位数组。a[0]为总计上传流量a[63]为总计下载流量
	// * a[1]-a[31]为1号到31号上传流量，a[32]-a[62]为1号到31号下载流量
	// */
	// public long[] getWifiMonthTraffic(Context context) {
	// WifiTraffiic wifiTraffic = new WifiTraffiic();
	// long[] monthtraffic = new long[64];
	// monthtraffic = wifiTraffic.getWifiMonthTraffic(context);
	// return monthtraffic;
	// }

	/**
	 * 记录wifi，mobile流量数据
	 * 
	 * @param context
	 * @param forcerecored
	 *            true则强制记录，false则不记录流量为0的数据固定为false
	 */
	public void statsTotalTraffic(Context context, boolean forcerecored) {
		if (SQLHelperTotal.isSQLTotalOnUsed != true) {
			SQLHelperTotal.isSQLTotalOnUsed = true;
			sqlhelperTotal.RecordTotalwritestats(context, true);
			SQLHelperTotal.isSQLTotalOnUsed = false;
			showLog("数据记录成功");
		} else {
			showLog("特殊情况未进行记录记录");
		}

	}

	private void showLog(String string) {
		// TODO Auto-generated method stub
		Log.d("TrafficManager", string);
	}
}
