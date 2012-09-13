package com.hiapk.control.traff;

import com.hiapk.util.SharedPrefrenceData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 获取当前月到结算日的流量使用情况，包含设置已用流量之后
 * 
 * @author Administrator
 * 
 */
public class MonthlyUseData {
	// 月度使用流量数据
	public static long MonthlyUseTraffic = 0;
	// ------------
	// 显示何种图形
	boolean ismobileshowpie = false;
	// wifi月度流量
	long[] wifiTraffic = new long[64];
	/**
	 * 完整月份的移动数据流量
	 */
	long[] mobileTraffic = new long[64];
	/**
	 * 部分月份的移动数据流量
	 */
	long[] mobileTrafficPart = new long[64];

	/**
	 * 获取月度使用流量
	 * 
	 * @param sqlDataBase
	 * @return 返回使用流量数值
	 */
	public long getMonthUseData(Context context, SQLiteDatabase sqlDataBase) {
		SharedPrefrenceData shareData = new SharedPrefrenceData(context);
		long monthHasUseBefore = shareData.getMonthHasUsedStack();
		// 进行赋值，下次不读取
		if (monthHasUseBefore == -100) {
			return 0;
			// if (TrafficManager.mobile_month_use_afterSet != 1) {
			// monthHasUseBefore = TrafficManager.getMonthUseData(context);
			// shareData.setMonthHasUsedStack(monthHasUseBefore);
			// }
		}
		return monthHasUseBefore;
		// MonthlyUseTraffic=mobile_month_use;
	}

	// /**
	// * 显示日志
	// *
	// * @param string
	// */
	// private void showlog(String string) {
	// // Log.d("MonthlyUseData", string);
	// }

}
