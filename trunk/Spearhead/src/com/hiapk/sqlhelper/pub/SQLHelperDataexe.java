package com.hiapk.sqlhelper.pub;

import com.hiapk.dataexe.MonthlyUseData;
import com.hiapk.dataexe.TrafficManager;
import com.hiapk.prefrencesetting.SharedPrefrenceDataWidget;
import com.hiapk.sqlhelper.total.SQLHelperTotal;
import com.hiapk.widget.SetText;

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
	 * @return uidtraff[0]Ϊuid���ϴ�������uidtraff[1]Ϊuid����������
	 */
	public static long[] initUidData(int uidnumber) {
		long uidupload, uiddownload;
		long[] uidtraff = new long[2];
		uidupload = TrafficStats.getUidTxBytes(uidnumber);
		uiddownload = TrafficStats.getUidRxBytes(uidnumber);
		if (uidupload == -1) {
			uidupload = 0;
		}
		if (uiddownload == -1) {
			uiddownload = 0;
		}
		uidtraff[0] = uidupload;
		uidtraff[1] = uiddownload;
		return uidtraff;
	}

	/**
	 * ��ʼ����������
	 * 
	 * @param table
	 *            wifi����mobile����Ϊ����������
	 * @return traff[0]Ϊuid���ϴ�������traff[1]Ϊuid����������
	 */
	public static long[] initTotalData(String table) {
		long upload = 0, download = 0;
		long[] totaltraff = new long[2];
		if (table == "wifi") {
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
		}
		if (table == "mobile") {
			upload = TrafficStats.getMobileTxBytes();
			download = TrafficStats.getMobileRxBytes();
			if (upload == -1) {
				upload = 0;
			}
			if (download == -1) {
				download = 0;
			}
		}
		if (table == "") {
			upload = 0;
			download = 0;
		}
		totaltraff[0] = upload;
		totaltraff[1] = download;
		return totaltraff;
	}

	public static void initShowData(Context context) {
		if (isiniting == false) {
			isiniting = true;
			new AsyncTaskonResume().execute(context);
		}
	}

	private static class AsyncTaskonResume extends
			AsyncTask<Context, Integer, Integer> {

		@Override
		protected Integer doInBackground(Context... params) {
			while (SQLStatic.setSQLTotalOnUsed(true)) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			initDataWithnoNetwork(params[0]);
			SQLStatic.setSQLTotalOnUsed(false);
			SetText.resetWidgetAndNotify(params[0]);
			return 3;
		}

		@Override
		protected void onPostExecute(Integer result) {
			isiniting = false;
		}
	}

	private static void initDataWithnoNetwork(Context context) {
		SQLStatic.initTablemobileAndwifi(context);
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
		SQLStatic.isTotalAlarmRecording = false;
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
