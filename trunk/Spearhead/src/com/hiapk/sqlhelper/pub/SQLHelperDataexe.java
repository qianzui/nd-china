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
 * ��ȡϵͳ����������������ݵľ�������
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
	 * ��ʼ����������
	 * 
	 * @param uidnumber
	 *            ����uid��ȡ��������
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
	 * ��ʼ����������
	 * 
	 * @param table
	 *            wifi����mobile����Ϊ����������
	 * @return traff[0]Ϊ���ϴ�������traff[1]Ϊ����������
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
	 * �����ڶ���ʱ��ʾ�������ݰ�������isTotalAlarmRecording=false
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
	 * �����ڶ���ʱ��ʾ��������splash
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
			// ����������һ�μ�¼
			sqlhelperTotal.RecordTotalwritestats(context, sqlDataBase, false,
					network);
			// ���ɻ�����������
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
			// �����ݽ��и�ֵ
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
	 * ��ʼ��ϵͳʱ��
	 */
	private static void initTime() {
		// Time t = new Time("GMT+8");
		Time t = new Time();
		t.setToNow(); // ȡ��ϵͳʱ�䡣
		// ȡ��ϵͳʱ�䡣
		year = t.year;
		month = t.month + 1;
		monthDay = t.monthDay;

	}
}
