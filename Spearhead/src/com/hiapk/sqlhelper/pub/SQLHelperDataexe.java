package com.hiapk.sqlhelper.pub;

import com.hiapk.bean.TotalTraffs;
import com.hiapk.bean.UidTraffs;
import com.hiapk.control.traff.MonthlyUseData;
import com.hiapk.control.traff.TrafficManager;
import com.hiapk.control.widget.SetText;
import com.hiapk.sqlhelper.total.SQLHelperTotal;
import com.hiapk.util.SQLStatic;
import com.hiapk.util.SharedPrefrenceDataWidget;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.TrafficStats;
import android.os.AsyncTask;
import android.text.format.Time;

/**
 * 读取系统总数据与分流量数据的具体流量
 * 
 * @author Administrator
 * 
 */
public class SQLHelperDataexe {
	private static int year;
	private static int month;
	private static String network;
	private static int monthDay;
	public static boolean isiniting = false;

	/**
	 * 初始化流量数据
	 * 
	 * @param uidnumber
	 *            依据uid获取流量数据
	 * @return
	 */
	public static UidTraffs initUidData(int uidnumber, UidTraffs uidTraff) {
		uidTraff.setUid(uidnumber);
		long uidupload = 0;
		long uiddownload = 0;
		uidupload = TrafficStats.getUidTxBytes(uidnumber);
		uiddownload = TrafficStats.getUidRxBytes(uidnumber);
		if (uidupload == -1) {
			uidupload = 0;
		}
		if (uiddownload == -1) {
			uiddownload = 0;
		}
		uidTraff.setUpload(uidupload);
		uidTraff.setDownload(uiddownload);
		return uidTraff;
	}

	/**
	 * 初始化流量数据
	 * 
	 * @param table
	 *            wifi或者mobile，若为空则无数据
	 * @return traff[0]为的上传流量，traff[1]为的下载流量
	 */
	public static TotalTraffs initTotalData() {
		TotalTraffs traff = new TotalTraffs();
		long upload = 0, download = 0;
		// if (table == "wifi") {
		upload = TrafficStats.getTotalTxBytes()
				- TrafficStats.getMobileTxBytes();
		download = TrafficStats.getTotalRxBytes()
				- TrafficStats.getMobileRxBytes();
		if (upload == 1) {
			upload = 0;
		}
		if (download == 1) {
			download = 0;
		}
		traff.setWifiDownload(download);
		traff.setWifiUpload(upload);

		// }
		// if (table == "mobile") {
		upload = TrafficStats.getMobileTxBytes();
		download = TrafficStats.getMobileRxBytes();
		if (upload == -1) {
			upload = 0;
		}
		if (download == -1) {
			download = 0;
		}
		traff.setMobileDownload(download);
		traff.setMobileUpload(upload);
		// }
		// if (table == "") {
		// upload = 0;
		// download = 0;
		// }
		return traff;
	}

	/**
	 * 用于在断网时显示流量数据包含操作isTotalAlarmRecording=false
	 * 
	 * @param context
	 */
	public static void initShowDataOnBroadCast(Context context) {
		if (isiniting == false) {
			isiniting = true;
			new AsyncTaskonOnBroadCast().execute(context);
		}
	}

	/**
	 * 用于在断网时显示流量数据splash
	 * 
	 * @param context
	 */
	public static void initShowDataOnSplash(Context context) {
		if (isiniting == false) {
			isiniting = true;
			new AsyncTaskonOnSplash().execute(context);
		}
	}

	private static class AsyncTaskonOnBroadCast extends
			AsyncTask<Context, Integer, Integer> {
		Context context;

		@Override
		protected Integer doInBackground(Context... params) {
			context = params[0];
			while (SQLStatic.setSQLTotalOnUsed(true)) {
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			initDataWithnoNetwork(params[0]);
			return 3;
		}

		@Override
		protected void onPostExecute(Integer result) {
			SQLStatic.setSQLTotalOnUsed(false);
			SQLStatic.isTotalAlarmRecording = false;
			SetText.resetWidgetAndNotify(context);
			isiniting = false;
		}
	}

	private static class AsyncTaskonOnSplash extends
			AsyncTask<Context, Integer, Integer> {
		Context context;

		@Override
		protected Integer doInBackground(Context... params) {
			context = params[0];
			while (SQLStatic.setSQLTotalOnUsed(true)) {
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			initDataWithnoNetwork(params[0]);
			return 3;
		}

		@Override
		protected void onPostExecute(Integer result) {
			SQLStatic.setSQLTotalOnUsed(false);
			SetText.resetWidgetAndNotify(context);
			isiniting = false;
		}
	}

	private static void initDataWithnoNetwork(Context context) {
		network = "nonetwork";
		long mobile_month_use_afterSet = 0;
		long[] wifi_month_data = new long[64];
		long[] mobile_month_data = new long[64];
		long[] wifi_month_data_before = new long[64];
		long[] mobile_month_data_before = new long[64];
		MonthlyUseData monthlyUseData = new MonthlyUseData();
		SQLHelperTotal sqlhelperTotal = new SQLHelperTotal();
		SQLiteDatabase sqlDataBase = SQLHelperCreateClose
				.creatSQLTotal(context);
		sqlDataBase.beginTransaction();
		try {
			// 断网后的最后一次记录
			sqlhelperTotal.RecordTotalwritestats(context, sqlDataBase, false,
					network);
			// 生成基本常用数据
			initTime();
			mobile_month_use_afterSet = monthlyUseData.getMonthUseData(context,
					sqlDataBase);
			wifi_month_data = sqlhelperTotal.SelectWifiData(sqlDataBase, year,
					month);
			mobile_month_data = sqlhelperTotal.SelectMobileData(sqlDataBase,
					year, month);
			if (month == 1) {
				mobile_month_data_before = sqlhelperTotal.SelectMobileData(
						sqlDataBase, year - 1, 12);
				wifi_month_data_before = sqlhelperTotal.SelectWifiData(
						sqlDataBase, year - 1, 12);
			} else {
				mobile_month_data_before = sqlhelperTotal.SelectMobileData(
						sqlDataBase, year, month - 1);
				wifi_month_data_before = sqlhelperTotal.SelectWifiData(
						sqlDataBase, year, month - 1);
			}
			sqlhelperTotal.autoClearData(sqlDataBase);
			sqlDataBase.setTransactionSuccessful();
			// 对数据进行赋值
			TrafficManager.wifi_month_data = wifi_month_data;
			TrafficManager.mobile_month_data = mobile_month_data;
			TrafficManager.mobile_month_data_before = mobile_month_data_before;
			TrafficManager.wifi_month_data_before = wifi_month_data_before;
			TrafficManager.mobile_month_use = mobile_month_use_afterSet;
			SharedPrefrenceDataWidget sharedData = new SharedPrefrenceDataWidget(
					context);
			sharedData.setTodayMobileDataLong(mobile_month_data[monthDay]
					+ mobile_month_data[monthDay + 31]);
			// showLog("wifitotal=" + wifi_month_data[0] + "");
		} catch (Exception e) {
		} finally {
			sqlDataBase.endTransaction();
		}
		SQLHelperCreateClose.closeSQL(sqlDataBase);
	}

	/**
	 * 初始化系统时间
	 */
	private static void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // 取得系统时间。
		// 取得系统时间。
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;

	}
}
